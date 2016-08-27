package dse.ss2016.group16.surgeryplanner.masterdata.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dse.ss2016.group16.surgeryplanner.core.entity.Doctor;

@Service
public class DoctorService {
	
	@Autowired
	public IDoctorRepository doctorRepository;
 
    public Doctor create(Doctor doctor) {
        // Vorsicht das kein vorhandes Object überschreiben wird --> Kopie erstellen
    	doctorRepository.save(doctor);
        return doctor;
    }
 
    public Doctor delete(String id) {
        Doctor deleted = findDoctorById(id);
        doctorRepository.delete(deleted);
        return deleted;
    }
 
    public List<Doctor> findAll() {
        List<Doctor> DoctorEntries = doctorRepository.findAll();
        return DoctorEntries;
    }
 
    public Doctor findById(String id)  {
        return findDoctorById(id);
    }
 
    public Doctor update(Doctor entry) {
        // Hier muss nicht vorher der Datensatz aus der DB abgefragt werden?
    	doctorRepository.save(entry);
        return entry;
    }
    
    private Doctor findDoctorById(String id) {
        Doctor result = doctorRepository.findOne(id);
        return result;
    }
    
    public void delete() {
    	doctorRepository.deleteAll();
    }
	
}
