package dse.ss2016.group16.surgeryplanner.masterdata.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import dse.ss2016.group16.surgeryplanner.core.entity.Doctor;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 11:26:47 AM
 *
 */
public interface IDoctorRepository extends MongoRepository<Doctor, String>{

}
