package dse.ss2016.group16.surgeryplanner.apigateway.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.naming.InvalidNameException;
import javax.validation.ValidationException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.json.JSONException;

import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 3:13:08 PM
 *
 */
public interface IBookingApiController {

	List<Object> findAll() throws ValidationException, InterruptedException, InvalidNameException;

	String create(OPSlot entry) throws ValidationException, InterruptedException, InvalidNameException, JSONException, JsonParseException, JsonMappingException, IOException;

	String book(OPSlot entry) throws ValidationException, InterruptedException, InvalidNameException, JSONException,
			JsonParseException, JsonMappingException, IOException;

	String cancelReservation(String slotId);

	String deleteReservation(String slotId);

	List<Object> findOPSlotsByHospitalId(String hospitalId);

	List<Object> findOPSlotsByDoctorId(String doctorId);

	List<Object> findOPSlotsByPatientId(String patientId);

}
