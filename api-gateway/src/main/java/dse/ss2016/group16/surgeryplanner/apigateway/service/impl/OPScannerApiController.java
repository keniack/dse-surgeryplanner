package dse.ss2016.group16.surgeryplanner.apigateway.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.OPScannerHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.ReserveSlotHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.SlotFindAllHystrixCommand;
import dse.ss2016.group16.surgeryplanner.apigateway.service.IOPScannerApiController;
import dse.ss2016.group16.surgeryplanner.core.dto.OPScannerRequestWrapper;
import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 13.06.2016.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/opscanner")
public class OPScannerApiController extends GenericApiController implements IOPScannerApiController{

    private static final String GROUP = "opscanner";


    /**
     * Finds the earliest available OPSlot in the geographically closest possible hospital and automatically
     * books it
     * @param request Wraps needed parameters to find the op-slot
     * @return the booked opslot
     */
    @Override
    @RequestMapping(value="findAndBook", method = RequestMethod.POST)
    public String findAndBookFreeSlot(@RequestBody  OPScannerRequestWrapper request) {


        //call the booking service to find all slots
        List<Callable<AsyncResponse>> callables = new ArrayList<>();
        SlotFindAllHystrixCommand slotFindAllSlotsHystrix = new SlotFindAllHystrixCommand("booking", "findAll");
        callables.add(new BackendServiceCallable("findAll", slotFindAllSlotsHystrix));
        List<Object> objectList = doCallAndGetObjectListFromResult(callables,OPSlot.class);

        request.setFreeSlots((List<OPSlot>)(Object)objectList);

        //call the opscanner service to find the best free slot
        callables = new ArrayList<>();
        OPScannerHystrixCommand opScannerHystrixCommand = new OPScannerHystrixCommand(GROUP, "find",request);
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

        //book the slot
        callables = new ArrayList<>();
        ReserveSlotHystrixCommand reserveSlotHystrixCommand = new ReserveSlotHystrixCommand("booking", "reserve",best);
        callables.add(new BackendServiceCallable("reserve", reserveSlotHystrixCommand));
        return getSingleJsonFromSingleCall(callables);
    }


}
