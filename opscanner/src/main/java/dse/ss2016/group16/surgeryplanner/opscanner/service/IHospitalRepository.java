package dse.ss2016.group16.surgeryplanner.opscanner.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import dse.ss2016.group16.surgeryplanner.core.entity.Hospital;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 1:53:17 PM
 *
 */
public interface IHospitalRepository extends MongoRepository<Hospital, String>{

}
