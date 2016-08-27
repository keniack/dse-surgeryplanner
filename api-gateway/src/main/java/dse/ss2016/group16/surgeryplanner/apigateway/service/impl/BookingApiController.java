package dse.ss2016.group16.surgeryplanner.apigateway.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.CancelSlotReservationHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.DeleteSlotHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.FindDoctorByIdHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.FindHospitalByIdHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.FindPatientByIdCommandHystrix;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.FindSlotByDoctorHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.FindSlotByHospitalHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.FindSlotByPatientHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.ReserveSlotHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.SlotCreateHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.SlotFindAllHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.service.IBookingApiController;
import dse.ss2016.group16.surgeryplanner.core.entity.Doctor;
import dse.ss2016.group16.surgeryplanner.core.entity.Hospital;
import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;
import dse.ss2016.group16.surgeryplanner.core.entity.Patient;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 12:45:34 PM
 *
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/booking")
public class BookingApiController extends GenericApiController implements IBookingApiController{
	
	private static final String GROUP = "booking";

	@Override
	@RequestMapping(value="/findAll", method = RequestMethod.GET, produces="application/json")
	public List<Object> findAll() {
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
        SlotFindAllHystrixCommand slotFindAllHystrix = new SlotFindAllHystrixCommand(GROUP, "findAll");
        callables.add(new BackendServiceCallable("findAll", slotFindAllHystrix));
        return getJsonSlotsWithEntititesName(callables);
	}
	
	@Override
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public String create(@RequestBody OPSlot entry)  {
		System.out.println("call create slot:" + entry);
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
		SlotCreateHystrixCommand slotCreateHystrix = new SlotCreateHystrixCommand(GROUP, "create", entry);
		callables.add(new BackendServiceCallable("findAll", slotCreateHystrix));
		return getSingleJsonFromSingleCall(callables);
	}
	
	@Override
	@RequestMapping(value="/book", method = RequestMethod.PUT)
	public String book(@RequestBody OPSlot entry) {
		System.out.println("call update slot:" + entry);
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
		ReserveSlotHystrixCommand bookSlotHystrix = new ReserveSlotHystrixCommand(GROUP, "book", entry);
		callables.add(new BackendServiceCallable("book", bookSlotHystrix));
		return getSingleJsonFromSingleCall(callables);
	}

	@Override
	@RequestMapping(value="/cancelReservation/{slotId}", method = RequestMethod.PUT)
	public String cancelReservation(@PathVariable String slotId) {
		System.out.println("call cancel reservation slot:" + slotId);
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
		CancelSlotReservationHystrixCommand cancelSlotReservationHystrix = new CancelSlotReservationHystrixCommand(GROUP, "cancelReservation", new OPSlot( slotId));
		callables.add(new BackendServiceCallable("cancelReservation", cancelSlotReservationHystrix));
		return getSingleJsonFromSingleCall(callables);
	}
	
	@Override
	@RequestMapping(value="/deleteReservation/{slotId}", method = RequestMethod.DELETE)
	public String deleteReservation(@PathVariable String slotId) {
		System.out.println("call delete reservation slot:" + slotId);
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
		DeleteSlotHystrixCommand cancelSlotReservationHystrix = new DeleteSlotHystrixCommand(GROUP, "deleteReservation", new OPSlot( slotId));
		callables.add(new BackendServiceCallable("deleteReservation", cancelSlotReservationHystrix));
		return getSingleJsonFromSingleCall(callables);
	}
	
	@Override
	@RequestMapping(value="/findOPSlots/hospital/{hospitalId}", method = RequestMethod.GET, produces="application/json")
	public List<Object> findOPSlotsByHospitalId(@PathVariable String hospitalId) {
		System.out.println("call findOPSlotsByHospitalId HOspital ID:" + hospitalId);
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
		FindSlotByHospitalHystrixCommand findOPSlotsByHospitalHystrix = new FindSlotByHospitalHystrixCommand(GROUP, "findOPSlotsByHospitalId",  hospitalId);
		callables.add(new BackendServiceCallable("findOPSlotsByHospitalId", findOPSlotsByHospitalHystrix));
		return getJsonSlotsWithEntititesName(callables);
	}
	
	@Override
	@RequestMapping(value="/findOPSlots/doctor/{doctorId}", method = RequestMethod.GET, produces="application/json")
	public List<Object> findOPSlotsByDoctorId(@PathVariable String doctorId) {
		System.out.println("call findOPSlotsByDoctorId doc ID:" + doctorId);
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
		FindSlotByDoctorHystrixCommand hystrixCommand = new FindSlotByDoctorHystrixCommand(GROUP, "findOPSlotsByDoctorId",  doctorId);
		callables.add(new BackendServiceCallable("findOPSlotsByDoctorId", hystrixCommand));
		return getJsonSlotsWithEntititesName(callables);
	}
	@Override
	@RequestMapping(value="/findOPSlots/patient/{patientId}", method = RequestMethod.GET, produces="application/json")
	public List<Object> findOPSlotsByPatientId(@PathVariable String patientId) {
		System.out.println("call findOPSlotsByPatientId patient ID:" + patientId);
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
		FindSlotByPatientHystrixCommand hystrixCommand = new FindSlotByPatientHystrixCommand(GROUP, "findOPSlotsByPatientId",  patientId);
		callables.add(new BackendServiceCallable("findOPSlotsByPatientId", hystrixCommand));
		return getJsonSlotsWithEntititesName(callables);
	}
	
	public Patient findPatientById (String patientId ){
		System.out.println("call findPatientById patient ID:" + patientId);
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
		FindPatientByIdCommandHystrix hystrixCommand = new FindPatientByIdCommandHystrix(GROUP, "findPatientById",  patientId);
		callables.add(new BackendServiceCallable("findPatientById", hystrixCommand));
		return (Patient) getSingleObjectFromSingleCall(callables, Patient.class);
	}
	public Hospital findHospitalById (String hospitalId ){
		System.out.println("call findHospitalById hospitalId:" + hospitalId);
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
		FindHospitalByIdHystrixCommand hystrixCommand = new FindHospitalByIdHystrixCommand(GROUP, "findHospitalById",  hospitalId);
		callables.add(new BackendServiceCallable("findHospitalById", hystrixCommand));
		return (Hospital) getSingleObjectFromSingleCall(callables, Hospital.class);
	}
	public Doctor findDoctorById (String doctorId ){
		System.out.println("call findDoctorById patient ID:" + doctorId);
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
		FindDoctorByIdHystrixCommand hystrixCommand = new FindDoctorByIdHystrixCommand(GROUP, "findDoctorById",  doctorId);
		callables.add(new BackendServiceCallable("findDoctorById", hystrixCommand));
		return (Doctor) getSingleObjectFromSingleCall(callables, Doctor.class);
	}
	
	
	public List<Object> getJsonSlotsWithEntititesName(List<Callable<AsyncResponse>> callables){
		List<Object> list = doCallAndGetObjectListFromResult(callables,OPSlot.class);
		
		for (Object obj:list){
			
			if(((OPSlot) obj).getPatientId() !=null){
				Patient patient = findPatientById(((OPSlot) obj).getPatientId() ) ;
				if (patient!=null)
					((OPSlot) obj).setPatientName(patient.getName());
			}
			
			if(((OPSlot) obj).getHospitalId() !=null){
				Hospital hospital = findHospitalById(((OPSlot) obj).getHospitalId() ) ;
				if (hospital!=null)
					((OPSlot) obj).setHospitalName(hospital.getName());
			}
			
			if(((OPSlot) obj).getDoctorId() !=null){
				Doctor doctor = findDoctorById(((OPSlot) obj).getDoctorId()) ;
				if (doctor!=null)
					((OPSlot) obj).setDoctorName(doctor.getName());
			}
			
		}
		
		return list;
		
	}

}
