package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import org.json.JSONObject;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;
import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 6:40:18 PM
 *
 */
public class DeleteSlotHystrixCommand extends SlotHystrixCommand{
	
	public DeleteSlotHystrixCommand(String group, String askKey, OPSlot slot) {
		super(group, askKey,slot,PropertiesUtil.getValue("booking.url.slot.delete"));
	}

	@Override
	protected String run() throws Exception {
		try{
			return callApiDeleteMethodString(bookingUrl, url + "/" + slot.getId(), new JSONObject());
		} catch (Throwable e) {
			throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
		}
	}
	
	@Override
    protected String getFallback() {
        return bookingUrl+url+" unavailable. Please contact system administrator";
    }


}
