package dse.ss2016.group16.surgeryplanner.booking.service;

import java.util.Date;
import java.util.List;

import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;
import dse.ss2016.group16.surgeryplanner.core.exception.OPSlotNotFoundException;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 9:45:29 PM
 *
 */
public interface ISlotService {

	OPSlot create(OPSlot entry) throws OPSlotNotFoundException;

	List<OPSlot> findDailySlots(Date date);

	void deleteAll();

	List<OPSlot> findAll();

	OPSlot book(OPSlot entry) throws OPSlotNotFoundException;

	List<OPSlot> findByPatientId(String patientId);

	List<OPSlot> findByHospitalId(String hospitalId);

	List<OPSlot> findByDoctorId(String doctorId);

	OPSlot cancelReservation(String slotId) throws OPSlotNotFoundException;

	String deleteOPSlot(String slotId) throws OPSlotNotFoundException;

}
