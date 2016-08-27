package dse.ss2016.group16.surgeryplanner.booking;

import java.util.Calendar;
import java.util.GregorianCalendar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import dse.ss2016.group16.surgeryplanner.booking.service.ISlotService;
import dse.ss2016.group16.surgeryplanner.core.entity.Doctor;
import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;
import dse.ss2016.group16.surgeryplanner.core.enums.OPType;
import dse.ss2016.group16.surgeryplanner.core.enums.SlotStatus;
import dse.ss2016.group16.surgeryplanner.core.exception.OPSlotNotFoundException;

@SpringBootApplication
public class BookingApp implements CommandLineRunner {

	@Autowired
	private ISlotService service;

	public static void main(String args[]) {
		SpringApplication.run(BookingApp.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createSlots();
		showAll();
	}

	public void createSlots() {
		service.deleteAll();
		Doctor doc = new Doctor("Dr. Albert Aufbesser", "Booking TestEintrag");
		createSlot(new GregorianCalendar(2016, 6, 20, 8, 0, 0), new GregorianCalendar(2016, 6, 20, 10, 0, 0),
				SlotStatus.AVAILABLE, OPType.AUGEN, null);
		createSlot(new GregorianCalendar(2016, 6, 21, 11, 0, 0), new GregorianCalendar(2016, 6, 21, 12, 0, 0),
				SlotStatus.AVAILABLE, OPType.HNO, null);
		createSlot(new GregorianCalendar(2016, 6, 21, 13, 0, 0), new GregorianCalendar(2016, 6, 21, 14, 0, 0),
				SlotStatus.AVAILABLE, OPType.ORTHO, doc);
		createSlot(new GregorianCalendar(2016, 6, 20, 10, 0, 1), new GregorianCalendar(2016, 6, 20, 12, 0, 0),
				SlotStatus.AVAILABLE, OPType.KARDIO, doc);
	}

	public void showAll() {
		System.out.println("Slots found with ");
		System.out.println("--------------------------------");
		OPSlot helper = null;
		for (OPSlot slot : service.findAll()) {
			System.out.println(slot);
			helper = slot;
		}

		System.out.println("Slots found with by Date Range for day: " + helper.getStartTime());
		System.out.println("--------------------------------");

		for (OPSlot slot : service.findDailySlots(helper.getStartTime())) {
			System.out.println(slot);
		}
	}

	public void createSlot(Calendar startTime, Calendar endTime, SlotStatus status, OPType type, Doctor doc) {
		try {
			OPSlot slot = new OPSlot();
			slot.setStartTime(startTime.getTime());
			slot.setEndTime(endTime.getTime());
			// if(doc!=null)
			// slot.setDoctor(doc);
			slot.setStatus(status);
			slot.setOPType(type);
			service.create(slot);
		} catch (OPSlotNotFoundException e) {
			e.printStackTrace();
		}
	}

}
