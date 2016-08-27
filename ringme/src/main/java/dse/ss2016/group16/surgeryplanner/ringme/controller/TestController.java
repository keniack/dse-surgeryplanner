package dse.ss2016.group16.surgeryplanner.ringme.controller;

import dse.ss2016.group16.surgeryplanner.core.entity.Notification;
import dse.ss2016.group16.surgeryplanner.core.enums.NotificationType;
import dse.ss2016.group16.surgeryplanner.ringme.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 12.06.2016.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/ringme/test")
public class TestController {

    private NotificationService service;

    @Autowired
    public TestController(NotificationService service){
        this.service = service;
    }

    @RequestMapping(value ="/hello", method = RequestMethod.GET)
    public String helloWorld() {
        return "Hello World! I'm the RingMe Service.";
    }
    
    @RequestMapping(value ="/status", method = RequestMethod.GET)
    public String status() {
        return "OK";
    }

    @RequestMapping(value ="/addTestData", method = RequestMethod.GET)
    public String addTestData() {

        Notification test = new Notification("SomeHospitalId", NotificationType.HOSPITAL, new Date(), "This is test");
        service.create(test);
        return "ok";
    }

    @RequestMapping(value ="/deleteAll", method = RequestMethod.DELETE)
    public String deleteAll() {

        service.deleteAll();
        return "ok";
    }
    
}
