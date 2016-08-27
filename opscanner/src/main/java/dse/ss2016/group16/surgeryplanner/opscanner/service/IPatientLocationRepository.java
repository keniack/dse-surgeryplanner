package dse.ss2016.group16.surgeryplanner.opscanner.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import dse.ss2016.group16.surgeryplanner.core.entity.PatientLocation;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 2:03:19 PM
 *
 */
public interface IPatientLocationRepository extends MongoRepository<PatientLocation, String>{

	PatientLocation findByPatientId(String patientId);

}
