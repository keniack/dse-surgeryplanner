package dse.ss2016.group16.surgeryplanner.booking.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 9:52:02 PM
 *
 */
public interface ISlotRepository extends MongoRepository<OPSlot, String>{

	

    List<OPSlot> findAll();
 
    @Query("{ 'startTime' :  { $gt: ?0, $lt: ?1 } }")
    List<OPSlot> findByStartTime(Date startTime,Date endTime);

	List<OPSlot> findByPatientId(String patientId);

	List<OPSlot> findByHospitalId(String hospitalId);

	List<OPSlot> findByDoctorId(String doctorId);
 
    
}
