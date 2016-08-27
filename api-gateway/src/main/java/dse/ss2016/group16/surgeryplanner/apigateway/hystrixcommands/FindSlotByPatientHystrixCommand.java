package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import org.json.JSONObject;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 7:14:46 PM
 *
 */
public class FindSlotByPatientHystrixCommand extends SlotHystrixCommand{
	
	String patientId=null;

	public FindSlotByPatientHystrixCommand(String group, String askKey, String patientId) {
		super(group, askKey,null,PropertiesUtil.getValue("booking.url.slot.findByPatientId"));
		this.patientId=patientId;
	}

	@Override
	protected String run() throws Exception {
		try {
			return callApiGetMethodString(bookingUrl, url + "/" + patientId, new JSONObject());
		} catch (Throwable e) {
			throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
		}
	}
	
	@Override
    protected String getFallback() {
        return bookingUrl+url+" unavailable. Please contact system administrator";
    }
}
