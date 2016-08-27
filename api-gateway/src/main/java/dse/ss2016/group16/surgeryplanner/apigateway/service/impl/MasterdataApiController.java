package dse.ss2016.group16.surgeryplanner.apigateway.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.GetAllDoctorsHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.GetAllHospitalsHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.GetAllPatientsHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.service.__IDoctorApiController;
import dse.ss2016.group16.surgeryplanner.apigateway.service.impl.GenericApiController.AsyncResponse;
import dse.ss2016.group16.surgeryplanner.apigateway.service.impl.GenericApiController.BackendServiceCallable;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/masterdata")
public class MasterdataApiController extends GenericApiController {
	
	private static final String GROUP = "masterdata";

	@RequestMapping(value = "/doctor", method = RequestMethod.GET, produces="application/json")
	public String getAllDoctors() {
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
		GetAllDoctorsHystrixCommand doctorHystrix = new GetAllDoctorsHystrixCommand(GROUP, "doctor");
		callables.add(new BackendServiceCallable("doctor", doctorHystrix));
		return getSingleJsonFromSingleCall(callables);
	}

	@RequestMapping(value = "/hospital", method = RequestMethod.GET, produces="application/json")
	public String getAllPatitents() {
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
		GetAllHospitalsHystrixCommand hospitalHystrix = new GetAllHospitalsHystrixCommand(GROUP, "patient");
		callables.add(new BackendServiceCallable("patient", hospitalHystrix));
		return getSingleJsonFromSingleCall(callables);
	}

	@RequestMapping(value = "/patient", method = RequestMethod.GET, produces="application/json")
	public String getAllHospitals() {
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
		GetAllPatientsHystrixCommand patientHystrix = new GetAllPatientsHystrixCommand(GROUP, "hospital");
		callables.add(new BackendServiceCallable("hospital", patientHystrix));
		return getSingleJsonFromSingleCall(callables);
	}
	
	
	

}
