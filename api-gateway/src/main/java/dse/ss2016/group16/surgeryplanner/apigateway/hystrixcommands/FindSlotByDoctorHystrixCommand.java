package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 7:16:23 PM
 *
 */
public class FindSlotByDoctorHystrixCommand extends SlotHystrixCommand{
	
	String doctorId=null;
	
	public FindSlotByDoctorHystrixCommand(String group, String askKey, String doctorId) {
		super(group, askKey,null,PropertiesUtil.getValue("booking.url.slot.findByDoctor"));
		this.doctorId=doctorId;
	}

	@Override
	protected String run() throws Exception {
		try {
			return callApiGetMethodString(bookingUrl, url + "/" + doctorId, null);
		} catch (Throwable e) {
			throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
		}
	}
	
	@Override
    protected String getFallback() {
        return bookingUrl+url+" unavailable. Please contact system administrator";
    }

}
