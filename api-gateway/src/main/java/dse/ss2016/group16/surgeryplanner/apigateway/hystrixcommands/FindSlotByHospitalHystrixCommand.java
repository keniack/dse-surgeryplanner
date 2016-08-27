package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import org.json.JSONObject;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 6:50:31 PM
 *
 */
public class FindSlotByHospitalHystrixCommand extends SlotHystrixCommand{
	
	String hospitalId=null; 
	public FindSlotByHospitalHystrixCommand(String group, String askKey, String hospitalId) {
		super(group, askKey,null,PropertiesUtil.getValue("booking.url.slot.findbyhospital"));
		this.hospitalId=hospitalId;
	}

	@Override
	protected String run() throws Exception {
		try {
			return callApiGetMethodString(bookingUrl, url + "/" + hospitalId, new JSONObject());
		} catch (Throwable e) { 
			throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
		}
	}
	
	@Override
    protected String getFallback() {
        return bookingUrl+url+" unavailable. Please contact system administrator";
    }

}
