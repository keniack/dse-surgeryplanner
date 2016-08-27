package dse.ss2016.group16.surgeryplanner.apigateway.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.json.JSONObject;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.StatusBookingHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.StatusMasterdataHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.StatusOpScannerHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.StatusRingmeHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.service.IAPIGatewayController;
import dse.ss2016.group16.surgeryplanner.core.entity.Status;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 17.03.2016.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/api")
public class APIGatewayController extends GenericApiController implements IAPIGatewayController {

	private static final String GROUP = "api-gateway";

	@Override
	@RequestMapping(value="/status", method = RequestMethod.GET, produces="application/json")
	public String getServiceStatus() {

		Status status = new Status();
		
		status.apigateway = "OK";
		
		List<Callable<AsyncResponse>> masterdataCallables = new ArrayList<>();
		StatusMasterdataHystrixCommand masterdataHystrix = new StatusMasterdataHystrixCommand(GROUP, "getMasterdataStatus");
		masterdataCallables.add(new BackendServiceCallable("masterdata", masterdataHystrix));
		status.masterdata = getSingleJsonFromSingleCall(masterdataCallables);
		
		List<Callable<AsyncResponse>> ringmeCallables = new ArrayList<>();
		StatusRingmeHystrixCommand ringmeHystrix = new StatusRingmeHystrixCommand(GROUP, "getNotificationStatus");
		ringmeCallables.add(new BackendServiceCallable("ringme", ringmeHystrix));
		status.ringme = getSingleJsonFromSingleCall(ringmeCallables);
		
		List<Callable<AsyncResponse>> opscannerCallables = new ArrayList<>();
		StatusOpScannerHystrixCommand opscannerHystrix = new StatusOpScannerHystrixCommand(GROUP, "getScannerStatus");
		opscannerCallables.add(new BackendServiceCallable("opscanner", opscannerHystrix));
		status.opscanner = getSingleJsonFromSingleCall(opscannerCallables);
		
		List<Callable<AsyncResponse>> bookingCallables = new ArrayList<>();
		StatusBookingHystrixCommand bookingHystrix = new StatusBookingHystrixCommand(GROUP, "getBookingStatus");
		bookingCallables.add(new BackendServiceCallable("booking", bookingHystrix));
		status.booking = getSingleJsonFromSingleCall(bookingCallables);
		
		return new JSONObject(status).toString();
		
	}



    
}
