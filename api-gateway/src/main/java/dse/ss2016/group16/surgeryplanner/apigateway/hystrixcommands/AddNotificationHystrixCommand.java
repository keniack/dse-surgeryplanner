package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import org.json.JSONObject;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;
import dse.ss2016.group16.surgeryplanner.core.entity.Notification;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 12:47:54 PM
 *
 */
public class AddNotificationHystrixCommand extends GenericHystrixCommand {
	
	JSONObject not;
	
	public AddNotificationHystrixCommand(String group, String askKey, JSONObject not) {
		super(group, askKey);
		setUrl(PropertiesUtil.getValue("ringme.url.create"));
		this.not = not;
	}

	@Override
	protected String run() throws Exception {
		try {
			return callApiPostMethodString(ringmeUrl, url, not);
		} catch (Throwable e) {
			throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
		}
	}
	
	@Override
    protected String getFallback() {
        return bookingUrl+url+" unavailable. Please contact system administrator";
    }

}
