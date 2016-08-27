package dse.ss2016.group16.surgeryplanner.masterdata.service;

import org.springframework.data.mongodb.repository.MongoRepository;

import dse.ss2016.group16.surgeryplanner.core.entity.Hospital;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 11:42:48 AM
 *
 */
public interface IHospitalRepository extends MongoRepository<Hospital, String> {

}
