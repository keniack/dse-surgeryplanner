package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import org.json.JSONObject;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;
import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;

public class ReserveSlotHystrixCommand extends SlotHystrixCommand{
	
	public ReserveSlotHystrixCommand(String group, String askKey, OPSlot slot) {
		super(group, askKey,slot,PropertiesUtil.getValue("booking.url.slot.book"));
	}

	@Override
	protected String run() throws Exception {
		try{
			JSONObject obj = callApiPutMethod(bookingUrl, url,convertObjectToJsonObject(slot));
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
