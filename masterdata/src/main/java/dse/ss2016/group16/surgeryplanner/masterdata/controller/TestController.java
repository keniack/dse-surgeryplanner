package dse.ss2016.group16.surgeryplanner.masterdata.controller;

import dse.ss2016.group16.surgeryplanner.core.entity.Doctor;
import dse.ss2016.group16.surgeryplanner.core.entity.Hospital;
import dse.ss2016.group16.surgeryplanner.core.entity.Patient;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import dse.ss2016.group16.surgeryplanner.masterdata.service.DoctorService;
import dse.ss2016.group16.surgeryplanner.masterdata.service.HospitalService;
import dse.ss2016.group16.surgeryplanner.masterdata.service.PatientService;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/test")
public class TestController {

	private final DoctorService doctor;
	private final HospitalService hospital;
	private final PatientService patient;

    @Autowired
    TestController(DoctorService doctor, HospitalService hospital, PatientService patient) {
        this.doctor = doctor;
        this.hospital = hospital;
        this.patient = patient;
    }
	
    @RequestMapping(value ="/hello", method = RequestMethod.GET)
    public String helloWorld() {
        return "Hello World! I'm the MasterData Service.";
    }
    
    @RequestMapping(value ="/status", method = RequestMethod.GET)
    public String status() {
        return "OK";
    }
	
    @RequestMapping(value="/addTestData", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public String addTestdata(){

        doctor.create(new Doctor().withName("Dr. Albert Aufbesser").withDescription("Test Arzt"));
        doctor.create(new Doctor().withName("Dr. Emily Eder").withDescription("Test Arzt"));
        doctor.create(new Doctor().withName("Dr. Adam Augapfel").withDescription("Test Arzt"));
        doctor.create(new Doctor().withName("Dr. Maria Meier").withDescription("Test Arzt"));
        
        patient.create(new Patient().withName("Fr. Adelheid Amann").withDescription("Test Patient, wohnt im LKH Mistelbach").withLon(16.57667).withLat(48.57));
        patient.create(new Patient().withName("Hr. Alfons Berger").withDescription("Test Patient, wohnt im LKH Tulln").withLon(16.05858).withLat(48.32829));
        patient.create(new Patient().withName("Fr. Beatrix Binder").withDescription("Test Patient, wohnt im KH Rudolfstiftung").withLon(16.39053404).withLat(48.19550184));
        patient.create(new Patient().withName("Hr. Franz Fuchs").withDescription("Test Patient, wohnt im SMZ West").withLon(16.27862692).withLat(48.57));
        patient.create(new Patient().withName("Fr. Gloria Greiner").withDescription("Test Patient, wohnt in Salzburg").withLon(13.034627).withLat(47.814087));
        patient.create(new Patient().withName("Hr. Manfred Mann").withDescription("Test Patient, wohnt in der Hofburg").withLon(16.366011).withLat(48.207594));
        
        hospital.create(new Hospital().withName("LKH Tulln").withDescription("Test Krankenhaus").withLon(16.05858).withLat(48.32829));
        hospital.create(new Hospital().withName("LKH Mistelbach").withDescription("Test Krankenhaus").withLon(16.57667).withLat(48.57));
        hospital.create(new Hospital().withName("KH Rudolfstiftung").withDescription("Test Krankenhaus").withLon(16.39053404).withLat(48.19550184));
        hospital.create(new Hospital().withName("SMZ West").withDescription("Test Krankenhaus").withLon(16.27862692).withLat(48.20814456));
        
        return "ok";
        
    }
    
    @RequestMapping(value="/deleteAll", method = RequestMethod.DELETE)
    public String deleteAll(){
    	
    	doctor.delete();
    	patient.delete();
    	hospital.delete();
    	
    	return "ok";
    	
    }
    
}
