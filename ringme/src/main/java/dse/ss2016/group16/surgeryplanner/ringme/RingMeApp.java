package dse.ss2016.group16.surgeryplanner.ringme;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dse.ss2016.group16.surgeryplanner.core.entity.Notification;
import dse.ss2016.group16.surgeryplanner.core.enums.NotificationType;
import dse.ss2016.group16.surgeryplanner.ringme.service.NotificationService;

@SpringBootApplication
public class RingMeApp implements CommandLineRunner{

	@Autowired
	private NotificationService service;
	
    public static void main(String[] args){

        SpringApplication.run(RingMeApp.class, args);
    }

	@Override
	public void run(String... arg0) throws Exception {
		service.create(new Notification("asdfasd", NotificationType.DOCTOR, new Date(), "Test"));
		System.out.println("Service created");
	}
}