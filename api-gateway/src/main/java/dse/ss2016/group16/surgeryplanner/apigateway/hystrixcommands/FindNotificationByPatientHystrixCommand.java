package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;
import org.json.JSONObject;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 14.06.2016.
 */
public class FindNotificationByPatientHystrixCommand extends GenericHystrixCommand{

    private String patientId;

    public FindNotificationByPatientHystrixCommand(String group, String askKey, String patientId) {
        super(group, askKey);
        this.patientId=patientId;
        setUrl(PropertiesUtil.getValue("ringme.url.findForPatient"));
    }

    @Override
    protected String run() throws Exception {
        try {
        	return callApiGetMethodString(ringmeUrl, url + "/" + patientId, new JSONObject());
        } catch (Throwable e) {
            throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
        }
    }

    @Override
    protected String getFallback() {
        return ringmeUrl+url+" unavailable. Please contact system administrator";
    }
}
