package dse.ss2016.group16.surgeryplanner.booking.controller.impl;

import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dse.ss2016.group16.surgeryplanner.booking.service.ISlotService;
import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;
import dse.ss2016.group16.surgeryplanner.core.exception.OPSlotNotFoundException;

@RestController
@RequestMapping(value="/booking/slot")
public class SlotController {
	
	private final ISlotService service;
	    
    @Autowired
    SlotController(ISlotService service) {
        this.service = service;
    }
	
	@RequestMapping(value="/create", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
	OPSlot create(@RequestBody @Valid OPSlot entry) throws OPSlotNotFoundException {
        return service.create(entry);
    }
	
	@RequestMapping(value="/book", method = RequestMethod.PUT,produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	OPSlot book(@RequestBody @Valid OPSlot entry) throws OPSlotNotFoundException {
		return service.book(entry);
	}
	
    @RequestMapping(value ="/findAll",method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody List<OPSlot> findAll() {
        return service.findAll();
    }

    
    @RequestMapping(value ="/findDailySlots",method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody List<OPSlot> findDailySlots(Date date) {
    	return service.findDailySlots(date);
    }
    
    
    @RequestMapping(value ="/findByPatientId/{patientId}",method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody List<OPSlot> findByPatientId(@PathVariable String patientId) {
    	return service.findByPatientId(patientId);
    }
    
    
    @RequestMapping(value ="/findByHospitalId/{hospitalId}",method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody List<OPSlot> findByHospitalId(@PathVariable String hospitalId) {
    	return service.findByHospitalId(hospitalId);
    }
    
    
    @RequestMapping(value ="/findByDoctorId/{doctorId}",method = RequestMethod.GET,produces=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody List<OPSlot> findByDoctorId(@PathVariable String doctorId) {
    	return service.findByDoctorId(doctorId);
    }
    
	@RequestMapping(value="/cancelReservation/{slotId}", method = RequestMethod.PUT,produces=MediaType.APPLICATION_JSON_VALUE)
	OPSlot cancelReservation(@PathVariable String slotId) throws OPSlotNotFoundException {
		return service.cancelReservation(slotId);
	}
	
	@RequestMapping(value="/deleteOPSlot/{slotId}", method = RequestMethod.DELETE)
	String deleteOPSlot(@PathVariable String slotId) throws OPSlotNotFoundException {
		return service.deleteOPSlot(slotId);
	}

    @RequestMapping(value="/deleteAll", method = RequestMethod.DELETE)
    String  deleteAll(){
        service.deleteAll();
        return "ok";
    }
   
   
}
