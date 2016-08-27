package dse.ss2016.group16.surgeryplanner.booking.controller.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import dse.ss2016.group16.surgeryplanner.booking.controller.IBookingController;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 17.03.2016.
 */
@RestController
@RequestMapping(value="/booking")
public class BookingController implements IBookingController{


    @Override
    @RequestMapping(value ="/hello", method = RequestMethod.GET)
    public String helloWorld() {
        return "Hello World! I'm the Booking Service.";
    }
    
    @Override
    @RequestMapping(value ="/status", method = RequestMethod.GET)
    public String status() {
        return "OK";
    }
    
}
