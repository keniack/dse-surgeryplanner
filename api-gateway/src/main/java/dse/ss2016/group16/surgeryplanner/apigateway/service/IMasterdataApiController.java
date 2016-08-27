package dse.ss2016.group16.surgeryplanner.apigateway.service;

import java.util.Map;

import javax.naming.InvalidNameException;
import javax.validation.ValidationException;


public interface IMasterdataApiController {

	/**
	 * Abfragen aller Doctor Einträge aus Masterdata
	 * 
	 * @return JSON Daten von Masterdata als String
	 * @throws ValidationException 
	 * @throws InterruptedException
	 * @throws InvalidNameException
	 */
	String getAllDoctors() throws ValidationException, InterruptedException, InvalidNameException;
	
	/**
	 * Abfragen aller Patient Einträge aus Masterdata
	 * 
	 * @return JSON Daten von Masterdata als String
	 * @throws ValidationException 
	 * @throws InterruptedException
	 * @throws InvalidNameException
	 */
	String getAllPatitents() throws ValidationException, InterruptedException, InvalidNameException;
	
	/**
	 * Abfragen aller Hospital Einträge aus Masterdata
	 * 
	 * @return JSON Daten von Masterdata als String
	 * @throws ValidationException 
	 * @throws InterruptedException
	 * @throws InvalidNameException
	 */
	String getAllHospitals() throws ValidationException, InterruptedException, InvalidNameException;

}
