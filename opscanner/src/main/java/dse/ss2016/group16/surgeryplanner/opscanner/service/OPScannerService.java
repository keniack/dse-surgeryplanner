package dse.ss2016.group16.surgeryplanner.opscanner.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.mongodb.BasicDBObject;

import dse.ss2016.group16.surgeryplanner.core.dto.OPScannerRequestWrapper;
import dse.ss2016.group16.surgeryplanner.core.entity.HospitalLocation;
import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;
import dse.ss2016.group16.surgeryplanner.core.entity.PatientLocation;
import dse.ss2016.group16.surgeryplanner.core.enums.SlotStatus;

@Service
public class OPScannerService {
	
	@Autowired
	private IHospitalRepository repository;
	
	@Autowired
	private IHospitalLocationRepository hospLocationRepository;
	
	@Autowired
	private IPatientLocationRepository patientLocationRepository;

    /**
     * Creates a HospitalLocation used for GeoSearch which references a Hospital
     * @param hospitalLocation
     * @return
     */
    public HospitalLocation createHospitalLocation(HospitalLocation hospitalLocation) {
    	hospLocationRepository.save(hospitalLocation);
        return hospitalLocation;
    }

    public PatientLocation createPatientLocation(PatientLocation patientLocation) {
    	patientLocationRepository.save(patientLocation);
        return patientLocation;
    }

    public void clear(){
    	hospLocationRepository.deleteAll();
    	patientLocationRepository.deleteAll();
    }

    public OPSlot findOpSlot(OPScannerRequestWrapper request){

        OPSlot result = null;

        //Retrieve the PatientLocation from the database
        Query patientQuery = new Query();
        patientQuery.addCriteria(Criteria.where("patientId").is(request.getPatientId()));

        PatientLocation patientLocation=patientLocationRepository.findByPatientId(request.getPatientId());

        if(patientLocation == null)
            return null;

        //filter the List of OP-Slots for valid ones
        List<OPSlot> validSlots = request.getFreeSlots().stream().filter(
                x->((x.getStatus().equals(SlotStatus.AVAILABLE))
                    && (x.getOPType().equals(request.getOPType()))
                    && (x.getStartTime().after(request.getBegin()))
                    && (x.getEndTime().before(request.getEnd()))
                    && (x.getDuration() >= request.getDuration()))
                    ).collect(Collectors.toList());

        //order the list of Hospitals with ascending distance to the patient
        final BasicDBObject filter = new BasicDBObject("$near", patientLocation.getLoc());
        final BasicDBObject query = new BasicDBObject("loc", filter);
        //OPScannerApp.mongoOperation .getCollection("hospitalLocation").createIndex(new BasicDBObject("loc", "2d"), "geospacialIdx");
//        List<DBObject> nearestHospitals = OPScannerApp.mongoOperation.getCollection("hospitalLocation").find(query).toArray();
        List<HospitalLocation> nearestHospitals = hospLocationRepository.findAll();

        for(HospitalLocation hospitalLocation : nearestHospitals){

//            String hospitalReference =(String) hospitalLocation.get("hospitalId");
            String hospitalReference = hospitalLocation.getHospitalId();

            //find possible slots at nearest Hopital
            List<OPSlot> possibleSlotsAtLocation = validSlots.stream().filter(
                    x->(x.getHospitalId().equals(hospitalReference))
            ).collect(Collectors.toList());

            //find earliest available slot
            if(possibleSlotsAtLocation.size()>0){

                Date earliest = null;

                for(OPSlot slot:possibleSlotsAtLocation){
                    if(earliest == null || (slot.getStartTime().before(earliest))){
                        earliest = slot.getStartTime();
                        result = slot;
                    }
                }

                result.setPatientId(request.getPatientId());
                result.setDoctorId(request.getDoctorId());

                return result;
            }

        }

        return null;
    }

}
