package dse.ss2016.group16.surgeryplanner.masterdata.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import dse.ss2016.group16.surgeryplanner.core.entity.Patient;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 11:36:45 AM
 *
 */
public interface IPatientRepository extends MongoRepository<Patient,String>{

}
