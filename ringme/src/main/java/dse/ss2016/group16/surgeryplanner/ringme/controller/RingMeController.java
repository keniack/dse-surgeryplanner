package dse.ss2016.group16.surgeryplanner.ringme.controller;

import dse.ss2016.group16.surgeryplanner.core.entity.Notification;
import dse.ss2016.group16.surgeryplanner.core.enums.NotificationType;
import dse.ss2016.group16.surgeryplanner.core.exception.NotificationNotFoundException;
import dse.ss2016.group16.surgeryplanner.ringme.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 17.03.2016.
 */
@RestController
@RequestMapping(value="/ringme")
public class RingMeController{

    private final NotificationService service;

    @Autowired
    RingMeController(NotificationService service) {
        this.service = service;
    }

    /**
     * Saves a notification to the database
     * @param entry The notification to save
     * @return The saved notification
     */
    @RequestMapping(value="create", method = RequestMethod.POST,produces=MediaType.APPLICATION_JSON_VALUE,
			consumes=MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    Notification create(@RequestBody @Valid Notification entry) {
    	System.out.println("Create NOtification: "+entry);
        return service.create(entry);
    }

    /**
     * Deletes a Notification
     * @param id The id of the notification to delete
     * @return The deleted notifcation
     */
    @RequestMapping(value = "delete/{id}", method = RequestMethod.DELETE)
    Notification delete(@PathVariable("id") String id) {
        return service.delete(id);
    }

    /**
     * Returns a Notification from the database
     * @param id
     * @return
     */
    @RequestMapping(value = "findById/{id}", method = RequestMethod.GET)
    Notification findById(@PathVariable("id") String id) throws NotificationNotFoundException{
        Notification response = service.findById(id);

        if(response == null) {
            throw new NotificationNotFoundException();
        }

        return response;

    }

    @RequestMapping(value = "doctor/{ref}", method = RequestMethod.GET)
    List<Notification> findForDoctor(@PathVariable("ref") String ref) throws NotificationNotFoundException{

        List<Notification> response = service.findByEntityId(ref, NotificationType.DOCTOR);

        if(response == null) {
            throw new NotificationNotFoundException();
        }

        return response;
    }

    @RequestMapping(value = "patient/{ref}", method = RequestMethod.GET)
    List<Notification> findForPatient(@PathVariable("ref") String ref) throws NotificationNotFoundException{

        List<Notification> response = service.findByEntityId(ref, NotificationType.PATIENT);

        if(response == null) {
            throw new NotificationNotFoundException();
        }

        return response;
    }

    @RequestMapping(value = "hospital/{ref}", method = RequestMethod.GET)
    List<Notification> findForHospital(@PathVariable("ref") String ref) throws NotificationNotFoundException{
        List<Notification> response = service.findByEntityId(ref, NotificationType.HOSPITAL);

        if(response == null) {
            throw new NotificationNotFoundException();
        }

        return response;
    }


}
