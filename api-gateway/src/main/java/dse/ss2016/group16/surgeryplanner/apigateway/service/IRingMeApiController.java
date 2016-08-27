package dse.ss2016.group16.surgeryplanner.apigateway.service;

import dse.ss2016.group16.surgeryplanner.core.entity.Notification;

import java.util.List;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 14.06.2016.
 */
public interface IRingMeApiController {

    String findNotificationByHospital(String hospitalId);

    String findNotificationByDoctor(String patientId);

    String findNotificationByPatient(String doctorId);

    String createNotification(Notification notification);
}
