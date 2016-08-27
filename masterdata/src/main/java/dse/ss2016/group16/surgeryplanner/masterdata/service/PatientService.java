package dse.ss2016.group16.surgeryplanner.masterdata.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dse.ss2016.group16.surgeryplanner.core.entity.Patient;

@Service
public class PatientService {

    private RestTemplate template = new RestTemplate();
    
    @Autowired
    private IPatientRepository repository;
    

    public Patient create(Patient patient) {

    	repository.save(patient);
        URI uri = getUri(patient.getId(), patient.getLon(), patient.getLat());
        template.getForObject(uri, String.class);

        return patient;
    }
 
    public Patient delete(String id) {
        Patient deleted = findPatientById(id);
        repository.delete(deleted);
        return deleted;
    }
 
    public List<Patient> findAll() {
        List<Patient> patientEntries = repository.findAll();
        return patientEntries;
    }
 
    public Patient findById(String id)  {
        return findPatientById(id);
    }
 
    public Patient update(Patient entry) {
    	repository.save(entry);
        return entry;
    }
    
    private Patient findPatientById(String id) {
        Patient result = repository.findOne(id);
        return result;
    }
	
    public void delete() {
    	repository.deleteAll();
    }

    private URI getUri(String id, double lon, double lat){


        String strLon = String.valueOf(lon);
        String strLat = String.valueOf(lat);

        String uri = String.format("http://192.168.99.100:9003/opscanner/addPatientLocation/%s/%s/%s/",id, strLon, strLat);
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
}
