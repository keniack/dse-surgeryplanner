package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import org.json.JSONObject;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;
import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 6:24:27 PM
 *
 */
public class CancelSlotReservationHystrixCommand extends SlotHystrixCommand{

	
	public CancelSlotReservationHystrixCommand(String group, String askKey, OPSlot slot) {
		super(group, askKey,slot,PropertiesUtil.getValue("booking.url.slot.cancel.reservation"));
	}
	

	@Override
	protected String run() throws Exception {
		try{
			return callApiPutMethodString(bookingUrl, url + "/" + slot.getId(), new JSONObject());
		} catch (Throwable e) {
			throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
		}
	}
	
	
	@Override
    protected String getFallback() {
        return url+" unavailable. Please contact system administrator";
    }

}
