package dse.ss2016.group16.surgeryplanner.apigateway.service;

import java.util.Map;

import javax.naming.InvalidNameException;
import javax.validation.ValidationException;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 1:39:20 PM
 *
 */
public interface __IDoctorApiController {

	Map<String, String> findAll() throws ValidationException, InterruptedException, InvalidNameException;

}
