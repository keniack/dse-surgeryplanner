package dse.ss2016.group16.surgeryplanner.apigateway.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.GetAllDoctorsHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.service.__IDoctorApiController;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 1:34:21 PM
 *
 */
@RestController
@RequestMapping("/api/doctor")
public class __DoctorApiController extends GenericApiController implements __IDoctorApiController{
	
	private static final String GROUP = "doctor";

	@RequestMapping(value="/findAll", method = RequestMethod.GET)
	public Map<String, String> findAll() {
	        List<Callable<AsyncResponse>> callables = new ArrayList<>();
	        GetAllDoctorsHystrixCommand doctorHystrix = new GetAllDoctorsHystrixCommand(GROUP, "findAll");
	        callables.add(new BackendServiceCallable("findAll", doctorHystrix));
	        return doBackendAsyncServiceCall(callables);
	  }
	
	
	

}
