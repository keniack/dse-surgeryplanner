package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import org.json.JSONObject;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 12:47:54 PM
 *
 */
public class SlotFindAllHystrixCommand extends SlotHystrixCommand{
	

	public SlotFindAllHystrixCommand(String group, String askKey) {
		super(group, askKey,null,PropertiesUtil.getValue("booking.url.slot.findall"));
		
	}

	@Override
	protected String run() throws Exception {
		try {
			return callApiGetMethodString(bookingUrl, url, new JSONObject());
		} catch (Throwable e) {
			throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
		}
	}
	
	@Override
    protected String getFallback() {
        return bookingUrl+url+" unavailable. Please contact system administrator";
    }

}
