package dse.ss2016.group16.surgeryplanner.opscanner.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import dse.ss2016.group16.surgeryplanner.core.entity.HospitalLocation;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 1:57:11 PM
 *
 */
public interface IHospitalLocationRepository extends MongoRepository<HospitalLocation, String>{

}
