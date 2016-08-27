package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;
import org.json.JSONObject;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 14.06.2016.
 */
public class FindNotificationByDoctorHystrixCommand extends GenericHystrixCommand {

    private String doctorId;

    public FindNotificationByDoctorHystrixCommand(String group, String askKey, String doctorId) {
        super(group, askKey);
        this.doctorId=doctorId;
        setUrl(PropertiesUtil.getValue("ringme.url.findForDoctor"));
    }

    @Override
    protected String run() throws Exception {
        try {
            return callApiGetMethodString(ringmeUrl, url + "/" + doctorId, new JSONObject());
        } catch (Throwable e) {
            throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
        }
    }

    @Override
    protected String getFallback() {
        return ringmeUrl+url+" unavailable. Please contact system administrator";
    }
}

