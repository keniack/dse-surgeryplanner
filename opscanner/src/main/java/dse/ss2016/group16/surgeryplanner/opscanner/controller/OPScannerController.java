package dse.ss2016.group16.surgeryplanner.opscanner.controller;

import dse.ss2016.group16.surgeryplanner.core.dto.OPScannerRequestWrapper;
import dse.ss2016.group16.surgeryplanner.core.entity.HospitalLocation;
import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;
import dse.ss2016.group16.surgeryplanner.core.entity.Patient;
import dse.ss2016.group16.surgeryplanner.core.entity.PatientLocation;
import dse.ss2016.group16.surgeryplanner.core.exception.OPSlotNotFoundException;
import dse.ss2016.group16.surgeryplanner.opscanner.service.OPScannerService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 17.03.2016.
 */

@RestController
@RequestMapping(value="/opscanner")
public class OPScannerController{

    private final OPScannerService service;

    @Autowired
    OPScannerController(OPScannerService service) {
        this.service = service;
    }

    @RequestMapping(value ="/hello", method = RequestMethod.GET)
    public String helloWorld() {
        return "Hello World! I'm the OPScanner Service.";
    }
    
    @RequestMapping(value ="/status", method = RequestMethod.GET)
    public String status() {
        return "OK";
    }

    @RequestMapping(value="/find", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public OPSlot find(@RequestBody OPScannerRequestWrapper request) throws OPSlotNotFoundException {

        OPSlot slot =  service.findOpSlot(request);

        if(slot != null) return slot;

        throw new OPSlotNotFoundException();
    }

    @RequestMapping(value="/addHospitalLocation/{hospitalId}/{lon}/{lat}", method = RequestMethod.GET)
    public String addHopitalLocation(@PathVariable String hospitalId, @PathVariable  String lon,
                                       @PathVariable String lat ){


        service.createHospitalLocation(new HospitalLocation(hospitalId, new double[]{Double.parseDouble(lon),Double.parseDouble(lat)}));

        return "Hospital Location created";
    }

    @RequestMapping(value="/addPatientLocation/{patientId}/{lon}/{lat}", method = RequestMethod.GET)
    public String addPatientLocation(@PathVariable String patientId, @PathVariable  String lon,
                                       @PathVariable String lat ){

        service.createPatientLocation(new PatientLocation(patientId, new double[]{Double.parseDouble(lon),Double.parseDouble(lat)}));

        return "Hospital Location created";
    }

    @RequestMapping(value="deleteAll", method = RequestMethod.DELETE)
    public String deleteAll(){
        service.clear();

        return "Testdata cleared";
    }



}
