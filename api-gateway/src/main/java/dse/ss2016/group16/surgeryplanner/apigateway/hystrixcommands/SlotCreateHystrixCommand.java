package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import org.json.JSONObject;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;
import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 2:16:06 PM
 *
 */
public class SlotCreateHystrixCommand extends SlotHystrixCommand{
	
	public SlotCreateHystrixCommand(String group, String askKey, OPSlot slot) {
		super(group, askKey,slot,PropertiesUtil.getValue("booking.url.slot.create"));
		format.setTimeZone(UTC_TIME_ZONE);
		
	}

	@Override
	protected String run() throws Exception {
		try {
			JSONObject obj = callApiPostMethod(bookingUrl, url,convertObjectToJsonObject(slot));
			return obj.toString();
		} catch (Throwable e) {
			throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
		}
	}
	
	
	@Override
    protected String getFallback() {
        return bookingUrl+url+" unavailable. Please contact system administrator";
    }
	

}
