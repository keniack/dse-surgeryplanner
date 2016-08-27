package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import org.json.JSONArray;
import org.json.JSONObject;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;

public class GetAllHospitalsHystrixCommand extends GenericHystrixCommand{

	
	public GetAllHospitalsHystrixCommand(String group, String askKey) {
		super(group, askKey);
		setUrl(PropertiesUtil.getValue("masterdata.url.hospital"));
	}
	
	@Override
    protected String run() throws Exception {
    	
		try {
			return callApiGetMethodString(masterDataUrl, url, new JSONObject());
		} catch (Throwable e) {
			throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
		}
    }
	
    @Override
    protected String getFallback() {
        return "Service unavailable. Please contact system administrator";
    }
    
    @Override
	public void setUrl(String url) {
		super.url = url;
	}
	
	
}
