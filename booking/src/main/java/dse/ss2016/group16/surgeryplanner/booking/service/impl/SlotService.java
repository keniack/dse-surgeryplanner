package dse.ss2016.group16.surgeryplanner.booking.service.impl;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;
import dse.ss2016.group16.surgeryplanner.booking.repository.ISlotRepository;
import dse.ss2016.group16.surgeryplanner.booking.service.ISlotService;
import dse.ss2016.group16.surgeryplanner.core.entity.Doctor;
import dse.ss2016.group16.surgeryplanner.core.entity.Hospital;
import dse.ss2016.group16.surgeryplanner.core.entity.Notification;
import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;
import dse.ss2016.group16.surgeryplanner.core.entity.Patient;
import dse.ss2016.group16.surgeryplanner.core.enums.NotificationType;
import dse.ss2016.group16.surgeryplanner.core.enums.OPDuration;
import dse.ss2016.group16.surgeryplanner.core.enums.SlotStatus;
import dse.ss2016.group16.surgeryplanner.core.exception.OPSlotNotFoundException;

@Service
public class SlotService extends GenericService implements ISlotService{
	
	String masterdataUrl= PropertiesUtil.getValue("masterdata.url");
	String ringmeUrl= PropertiesUtil.getValue("ringme.url");
	String findDoctorById= PropertiesUtil.getValue("masterdata.doctor.findbyid");
	String findHospitalById= PropertiesUtil.getValue("masterdata.hospital.findbyid");
	String findPatientById= PropertiesUtil.getValue("masterdata.patient.findbyid");
	String createNotificaiton= PropertiesUtil.getValue("ringme.notification.create");

	private ISlotRepository repository;

	@Autowired
	SlotService(ISlotRepository repository) throws UnknownHostException {
        this.repository = repository;
    }
	
	@Override
	public OPSlot create(OPSlot entry) throws OPSlotNotFoundException {
		
		if(entry == null || entry.getStartTime()==null || entry.getEndTime()==null || entry.getOPType()==null)
			throw new IllegalArgumentException("Missing required field "+ entry);
		
		/* Check if there is another slot created at same time
		List<OPSlot> dailySlots = findDailySlots(entry.getStartTime());
		for (OPSlot slot: dailySlots){
			if(slot.hasConflict(entry.getStartTime(), entry.getEndTime(),slot.getOPType()))
				throw new OPSlotNotFoundException("Conflict found with Slot: "+ slot);
		}
		*/
	    long diffMinutes = entry.getDuration() / (60 * 1000);
	    
	    if(diffMinutes < OPDuration._60_MIN.getValue() || diffMinutes > OPDuration._240_MIN.getValue())
	    	throw new IllegalArgumentException("Slot duration invalid. ");
		
		return repository.insert(entry);
	}

	@Override
	public List<OPSlot> findDailySlots(Date date) {
		
		if(date == null )
			return new ArrayList<>();
			
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		
		int year = cal.get(Calendar.YEAR);
	    int month = cal.get(Calendar.MONTH);
	    int day = cal.get(Calendar.DAY_OF_MONTH);
		
		Date startTime = new Date(year - 1900, month, day, 0, 0, 0);
		
		Date endTime = new Date(year - 1900, month, day, 23, 59, 59);
		
		System.out.println("Start date:" + startTime);
		System.out.println("end date:" + endTime);
		return repository.findByStartTime(startTime, endTime);
	}

	@Override
	public void deleteAll() {
		repository.deleteAll();
		
	}

	@Override
	public List<OPSlot> findAll() {
		return repository.findAll();
	}

	@Override
	public OPSlot book(OPSlot entry) throws OPSlotNotFoundException {
		System.out.println("Reserve op: "+entry);
		if(entry == null || entry.getStartTime()==null || entry.getEndTime()==null || entry.getOPType()==null
				|| entry.getDoctorId()==null || entry.getHospitalId()==null || entry.getPatientId()==null)
			throw new IllegalArgumentException("Missing required field "+ entry);

		try {
			Hospital hosp = (Hospital) convertJsonToObject(callApiGetMethodFindByIdURL(masterdataUrl, findHospitalById,entry.getHospitalId()), Hospital.class);
			Doctor doc = (Doctor) convertJsonToObject(callApiGetMethodFindByIdURL(masterdataUrl, findDoctorById,entry.getDoctorId()), Doctor.class);
			Patient patient = (Patient) convertJsonToObject(callApiGetMethodFindByIdURL(masterdataUrl, findPatientById,entry.getPatientId()), Patient.class);
			System.out.println("Hospital: "+hosp);
			System.out.println("Doctor: "+doc);
			System.out.println("Patient: "+patient);
			if( hosp== null || doc ==null || patient==null)
				throw new OPSlotNotFoundException("Slot reference not found. Hospital:" + hosp + " Patient: "+ patient + " Doctor: " + doc );
			
			OPSlot slot = repository.findOne(entry.getId());
			
			slot.setDoctorId(doc.getId());
			slot.setPatientId(patient.getId());
			slot.setHospitalId(hosp.getId());
			slot.setStatus(SlotStatus.RESERVED);
			slot=repository.save(slot);
			
			createNotificationForOPSlot(entry.getDoctorId(), NotificationType.DOCTOR, entry.getOPType().toString() + " OP  was reserved sucessfully at the Hospital:" + hosp.getName() + " with Patient:" + patient.getName());
			createNotificationForOPSlot(entry.getHospitalId(), NotificationType.HOSPITAL, entry.getOPType().toString() + " OP  was reserved sucessfully for Doctor:" + doc.getDescription() + " with Patient:" + patient.getName());
			createNotificationForOPSlot(entry.getPatientId(), NotificationType.PATIENT, entry.getOPType().toString() + " OP  was reserved sucessfully at the Hospital:" + hosp.getName() + " with Doctor:" + doc.getDescription());
			
			return entry;
		} catch (Throwable e) {
			throw new OPSlotNotFoundException("Error reserving Slot! Please contact system administrator");
		}
		
	}

	protected JSONObject createNotificationForOPSlot(String referenceId, NotificationType type, String text) throws Throwable {
		Notification notification = new Notification();
		notification.setType(type);
		notification.setEntityId(referenceId);
		notification.setText(text);
		notification.setDate(new Date());
		System.out.println("Creating notifications for : " + type.toString() +  " ID: " + referenceId);
		return callPostGetMethodFindByIdURL(ringmeUrl, createNotificaiton, convertObjectToJsonObject(notification));
	}
	
	protected JSONObject convertObjectToJsonObject(Notification obj) {

		try {

			JSONObject jsonObject = new JSONObject();
			jsonObject.put("type", obj.getType().toString());
			jsonObject.put("entityId", obj.getEntityId());
			String date = format.format(obj.getDate());
			jsonObject.put("date", date);
			jsonObject.put("text", obj.getText());
			return jsonObject;

		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public List<OPSlot> findByPatientId(String patientId) {
		return repository.findByPatientId(patientId);
	}

	@Override
	public List<OPSlot> findByHospitalId(String hospitalId) {
		return repository.findByHospitalId(hospitalId);
	}

	@Override
	public List<OPSlot> findByDoctorId(String doctorId) {
		return repository.findByDoctorId(doctorId);
	}

	@Override
	public OPSlot cancelReservation(String slotId) throws OPSlotNotFoundException {
		try {
			OPSlot slot = repository.findOne(slotId);
			if(slot == null || slotId ==null)
				throw new OPSlotNotFoundException("OPSlot not found. Id:" + slotId);
			
			Hospital hosp = (Hospital) convertJsonToObject(callApiGetMethodFindByIdURL(masterdataUrl, findHospitalById,slot.getHospitalId()), Hospital.class);
			Doctor doc;
				doc = (Doctor) convertJsonToObject(callApiGetMethodFindByIdURL(masterdataUrl, findDoctorById,slot.getDoctorId()), Doctor.class);
			Patient patient = (Patient) convertJsonToObject(callApiGetMethodFindByIdURL(masterdataUrl, findPatientById,slot.getPatientId()), Patient.class);
			
			
			slot.setDoctorId(null);
			slot.setPatientId(null);
			slot.setStatus(SlotStatus.AVAILABLE);
			
			slot = repository.save(slot);
			createNotificationForOPSlot(slot.getDoctorId(), NotificationType.DOCTOR, slot.getOPType().toString() + " OP Reservation  was canceled at the Hospital:" + hosp.getName() + " with Patient:" + patient.getName());
			createNotificationForOPSlot(slot.getHospitalId(), NotificationType.HOSPITAL, slot.getOPType().toString() + " OP  was canceled for Doctor:" + doc.getDescription() + " with Patient:" + patient.getName());
			createNotificationForOPSlot(slot.getDoctorId(), NotificationType.PATIENT, slot.getOPType().toString() + " OP  was canceled at the Hospital:" + hosp.getName() + " with Doctor:" + doc.getDescription());
			return slot;
		} catch (Throwable e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String deleteOPSlot(String slotId) throws OPSlotNotFoundException {
		OPSlot slot = repository.findOne(slotId);

		if(slot == null || slotId ==null)
			throw new OPSlotNotFoundException("OPSlot not found. Id:" + slotId);
		
		repository.delete(slot);
		return "OPSlot deleted with Sucess! ";
	}
	
	

}
