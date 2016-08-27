package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 12:57:32 AM
 *
 */
public class FindHospitalByIdHystrixCommand extends GenericHystrixCommand{

	String hospitalId=null;

	public FindHospitalByIdHystrixCommand(String group, String askKey, String hospitalId) {
		super(group, askKey);
		setUrl(PropertiesUtil.getValue("masterdata.url.hospital"));
		this.hospitalId=hospitalId;
	}

	
	@Override
    protected String getFallback() {
        return masterDataUrl+url+" unavailable. Please contact system administrator";
    }


	@Override
	protected String run() throws Exception {
		try {
			return callApiGetMethodString(masterDataUrl, url + "/" + hospitalId, null);
		} catch (Throwable e) {
			throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
		}
	}
	
}
