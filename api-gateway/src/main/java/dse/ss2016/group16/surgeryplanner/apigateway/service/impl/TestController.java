package dse.ss2016.group16.surgeryplanner.apigateway.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;

import javax.validation.Valid;

import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.*;
import dse.ss2016.group16.surgeryplanner.core.dto.OPScannerRequestWrapper;
import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;
import dse.ss2016.group16.surgeryplanner.core.entity.Patient;
import dse.ss2016.group16.surgeryplanner.core.enums.OPType;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import dse.ss2016.group16.surgeryplanner.apigateway.service.ITestController;
import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(value="/test")
public class TestController extends GenericApiController implements ITestController {

	private static final String GROUP = "test";
	
    @Override
    @RequestMapping(value="/hello", method = RequestMethod.GET)
    public String helloWorld() {
        return "Hello World! I'm the API-Gateway.";
    }
	
	@Override
	@RequestMapping(value="/addTestData", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String addTestData() throws JSONException {
		
		// MasterData
		String masterDataUrl = PropertiesUtil.getValue("masterdata.url");
    	String masterDataPath = PropertiesUtil.getValue("masterdata.url.addTestData");
    	String status = callApiPostMethod(masterDataUrl, masterDataPath, new JSONObject());
    	
    	// Booking


    	// RingMe
		String ringmeDataUrl = PropertiesUtil.getValue("ringme.url");
    	String ringmeDataPath = PropertiesUtil.getValue("ringme.url.addTestData");
    	status += callApiPostMethod(ringmeDataUrl, ringmeDataPath, new JSONObject());
    	
    	return status;
	}

	@Override
	@RequestMapping(value="/deleteAll", method = RequestMethod.DELETE)
	public String deleteAll() {
		
		// MasterData
		String masterDataUrl = PropertiesUtil.getValue("masterdata.url");
    	String masterDataPath = PropertiesUtil.getValue("masterdata.url.deleteAll");
		String status = callApiDeleteMethod(masterDataUrl, masterDataPath);
    	
    	// Booking
    	String bookingDataUrl = PropertiesUtil.getValue("booking.url");
		String bookingDataPath = PropertiesUtil.getValue("booking.url.slot.deleteAll");
		status += callApiDeleteMethod(bookingDataUrl,bookingDataPath);

    	// OPScanner
    	String opscannerDataUrl = PropertiesUtil.getValue("opscanner.url");
		String opScannerDataPath= PropertiesUtil.getValue("opscanner.url.deleteAll");
		status += callApiDeleteMethod(opscannerDataUrl, opScannerDataPath);

		// RingMe
		String ringmeDataUrl = PropertiesUtil.getValue("ringme.url");
    	String ringmeDataPath = PropertiesUtil.getValue("ringme.url.deleteAll");
    	status += callApiDeleteMethod(ringmeDataUrl, ringmeDataPath);
    	
    	return status;
	}
	
	@Override
	@RequestMapping(value="/addNotification", method = RequestMethod.POST, produces="application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public String addNotification(@RequestBody @Valid String entry) throws JSONException {
		
		org.json.JSONObject json = new org.json.JSONObject(entry);
		
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
		AddNotificationHystrixCommand slotFindAllHystrix = new AddNotificationHystrixCommand(GROUP, "addNotification", json );
        callables.add(new BackendServiceCallable("addNotification", slotFindAllHystrix));
        return getSingleJsonFromSingleCall(callables);

	}

	@RequestMapping(value="testOPScanner/{patientID}", method = RequestMethod.GET)
	public String testOPScanner(@PathVariable("patientID") String patientID) {

		Date begin = new Date(1465516800000L);
		Date end = new Date(1466121600000L);

		int duration = 1000*60*120;
		OPScannerRequestWrapper request = new OPScannerRequestWrapper(null, patientID, null, begin, end, duration, OPType.AUGEN.toString());
		//call the booking service to find all slots
		List<Callable<AsyncResponse>> callables = new ArrayList<>();
		SlotFindAllHystrixCommand slotFindAllSlotsHystrix = new SlotFindAllHystrixCommand("opslot", "findAll");
		callables.add(new BackendServiceCallable("findAll", slotFindAllSlotsHystrix));
		List<Object> objectList = doCallAndGetObjectListFromResult(callables,OPSlot.class);


		request.setFreeSlots((List<OPSlot>)(Object)objectList);

		//call the opscanner service to find the best free slot
		callables = new ArrayList<>();
		OPScannerHystrixCommand opScannerHystrixCommand = new OPScannerHystrixCommand("opscanner", "find",request);
		callables.add(new BackendServiceCallable("find", opScannerHystrixCommand));
		String result = getSingleJsonFromSingleCall(callables);
		OPSlot best = null;

		try {
			best = new ObjectMapper().readValue(result, OPSlot.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		best.setDoctorId("57609044d3c2aaf69c531317");
		callables = new ArrayList<>();
		ReserveSlotHystrixCommand reserveSlotHystrixCommand = new ReserveSlotHystrixCommand("booking", "reserve",best);
		callables.add(new BackendServiceCallable("reserve", reserveSlotHystrixCommand));
		return getSingleJsonFromSingleCall(callables);


	}


}
	

