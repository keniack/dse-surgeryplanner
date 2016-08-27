package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 12:58:39 AM
 *
 */
public class FindDoctorByIdHystrixCommand extends GenericHystrixCommand{

	String doctorId=null;

	public FindDoctorByIdHystrixCommand(String group, String askKey, String doctorId) {
		super(group, askKey);
		setUrl(PropertiesUtil.getValue("masterdata.url.doctor"));
		this.doctorId=doctorId;
	}

	
	@Override
    protected String getFallback() {
        return masterDataUrl+url+" unavailable. Please contact system administrator";
    }


	@Override
	protected String run() throws Exception {
		try {
			return callApiGetMethodString(masterDataUrl, url + "/" + doctorId, null);
		} catch (Throwable e) {
			throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
		}
	}
}
