package dse.ss2016.group16.surgeryplanner.apigateway.service.impl;

import dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands.*;
import dse.ss2016.group16.surgeryplanner.apigateway.service.IRingMeApiController;
import dse.ss2016.group16.surgeryplanner.core.entity.Notification;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 14.06.2016.
 */
@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api/ringme")
public class RingMeApiController extends GenericApiController implements IRingMeApiController{

    private static final String GROUP = "ringme";

    @Override
    @RequestMapping(value="/findNotification/hospital/{hospitalId}", method = RequestMethod.GET, produces="application/json")
    public String findNotificationByHospital(@PathVariable String hospitalId){

        System.out.println("call findNotificationByHospitalId:" + hospitalId);

        List<Callable<AsyncResponse>> callables = new ArrayList<>();
        FindNotificationByHospitalHystrixCommand findNotificationByHospital =
                new FindNotificationByHospitalHystrixCommand(GROUP, "findNotificationByHospitalId", hospitalId);
        callables.add(new BackendServiceCallable("findNotificationByHospitalId", findNotificationByHospital));

        return getSingleJsonFromSingleCall(callables);
    }

    @Override
    @RequestMapping(value="/findNotification/doctor/{doctorId}", method = RequestMethod.GET, produces="application/json")
    public String findNotificationByDoctor(@PathVariable String doctorId) {

        System.out.println("call findNotificationByDoctorId:" + doctorId);

        List<Callable<AsyncResponse>> callables = new ArrayList<>();
        FindNotificationByDoctorHystrixCommand findNotificationByDoctor =
                new FindNotificationByDoctorHystrixCommand(GROUP, "findNotificationByHospitalId", doctorId);
        callables.add(new BackendServiceCallable("findNotificationBydoctorId", findNotificationByDoctor));

        return getSingleJsonFromSingleCall(callables);
    }

    @Override
    @RequestMapping(value="/findNotification/patient/{patientId}", method = RequestMethod.GET, produces="application/json")
    public String findNotificationByPatient(@PathVariable String patientId) {

        System.out.println("call findNotificationBypatientId:" + patientId);

        List<Callable<AsyncResponse>> callables = new ArrayList<>();
        FindNotificationByPatientHystrixCommand findNotificationByPatient =
                new FindNotificationByPatientHystrixCommand(GROUP, "findNotificationByHospitalId", patientId);
        callables.add(new BackendServiceCallable("findNotificationByPatientId", findNotificationByPatient));

        return getSingleJsonFromSingleCall(callables);
    }

    @Override
    @RequestMapping(value="/create", method = RequestMethod.POST)
    public String createNotification(Notification notification) {

        System.out.println("call create notification:" + notification);

        List<Callable<AsyncResponse>> callables = new ArrayList<>();
        CreateNotificationHystrixCommand createNotification =
                new CreateNotificationHystrixCommand(GROUP, "create", notification);
        callables.add(new BackendServiceCallable("create", createNotification));
        return getSingleJsonFromSingleCall(callables);
    }
}
