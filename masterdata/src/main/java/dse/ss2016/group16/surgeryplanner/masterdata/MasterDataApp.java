package dse.ss2016.group16.surgeryplanner.masterdata;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.data.mongodb.core.MongoOperations;

import dse.ss2016.group16.surgeryplanner.core.entity.Doctor;
import dse.ss2016.group16.surgeryplanner.masterdata.service.DoctorService;

@SpringBootApplication
public class MasterDataApp implements CommandLineRunner {

	@Autowired
	private DoctorService doctorService;
	
    public static void main(String[] args) {
        SpringApplication.run(MasterDataApp.class, args);
        
    } 
    
    
    @Override
	public void run(String... args) throws Exception {
    	createDoctors();
	}
    
    public void createDoctors(){
    	Doctor doc =new Doctor("Dr. Test", "Musterman");
    	doctorService.create(doc);
    	
    	System.out.println("----------------------------");
    	System.out.println("Doctor saved:" + doc);
    }
}
