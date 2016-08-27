package dse.ss2016.group16.surgeryplanner.masterdata.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import dse.ss2016.group16.surgeryplanner.core.entity.Hospital;

@Service
public class HospitalService {

    private RestTemplate template = new RestTemplate();
    
    private IHospitalRepository  repository;

    public Hospital create(Hospital hospital) {
    	repository.save(hospital);
        //create corresponding entry in the opscanner service
        URI uri = getUri(hospital.getId(),hospital.getLon(), hospital.getLat());
        template.getForObject(uri, String.class);
        return hospital;
    }
 
    public Hospital delete(String id) {
        Hospital deleted = findHospitalById(id);
        repository.delete(deleted);
        return deleted;
    }
 
    public List<Hospital> findAll() {
        List<Hospital> hospitalEntries = repository.findAll();
        return hospitalEntries;
    }
 
    public Hospital findById(String id)  {
        return findHospitalById(id);
    }
 
    public Hospital update(Hospital entry) {
    	repository.save(entry);
        return entry;
    }
    
    private Hospital findHospitalById(String id) {
        Hospital result = repository.findOne(id) ;
        return result;
    }
	
    public void delete() {
    	repository.deleteAll();
    }

    private URI getUri(String id, double lon, double lat){


        String strLon = String.valueOf(lon);
        String strLat = String.valueOf(lat);

        String uri = String.format("http://192.168.99.100:9003/opscanner/addHospitalLocation/%s/%s/%s/",id, strLon, strLat);
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        return null;
    }
}
