package dse.ss2016.group16.surgeryplanner.masterdata.controller;

import java.util.List;

import javax.validation.Valid;

import dse.ss2016.group16.surgeryplanner.core.entity.Doctor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dse.ss2016.group16.surgeryplanner.masterdata.exception.EntryNotFoundException;
import dse.ss2016.group16.surgeryplanner.masterdata.service.DoctorService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/doctor")
public class DoctorController {

	    private final DoctorService service;
	    
	    @Autowired
	    DoctorController(DoctorService service) {
	        this.service = service;
	    }
	 
	    @RequestMapping(method = RequestMethod.POST)
	    @ResponseStatus(HttpStatus.CREATED)
		Doctor create(@RequestBody @Valid Doctor entry) {
	        return service.create(entry);
	    }
	 
	    @RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	    Doctor delete(@PathVariable("id") String id) throws EntryNotFoundException {
	        return service.delete(id);
	    }
	 
	    @RequestMapping(method = RequestMethod.GET)
	    List<Doctor> findAll() {
	        return service.findAll();
	    }
	 
	    @RequestMapping(value = "{id}", method = RequestMethod.GET)
	    Doctor findById(@PathVariable("id") String id) throws EntryNotFoundException {
	        return service.findById(id);
	    }
	 
	    @RequestMapping(value = "{id}", method = RequestMethod.PUT)
	    Doctor update(@RequestBody @Valid Doctor entry) throws EntryNotFoundException {
	        return service.update(entry);
	    }
	 
	    @ExceptionHandler
	    @ResponseStatus(HttpStatus.NOT_FOUND)
	    public void handleTodoNotFound(EntryNotFoundException ex) {
	    }	
	
}
