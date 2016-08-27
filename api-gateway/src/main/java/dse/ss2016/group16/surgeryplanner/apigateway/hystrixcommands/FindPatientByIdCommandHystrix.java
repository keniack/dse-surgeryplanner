package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import com.netflix.hystrix.exception.HystrixRuntimeException;

import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 12:53:33 AM
 *
 */
public class FindPatientByIdCommandHystrix extends GenericHystrixCommand {
	
	String patientId=null;

	public FindPatientByIdCommandHystrix(String group, String askKey, String patientId) {
		super(group, askKey);
		setUrl(PropertiesUtil.getValue("masterdata.url.patient"));
		this.patientId=patientId;
	}

	
	@Override
    protected String getFallback() {
        return masterDataUrl+url+" unavailable. Please contact system administrator";
    }


	@Override
	protected String run() throws Exception {
		try {
			return callApiGetMethodString(masterDataUrl, url + "/" + patientId, null);
		} catch (Throwable e) {
			throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
		}
	}

}
