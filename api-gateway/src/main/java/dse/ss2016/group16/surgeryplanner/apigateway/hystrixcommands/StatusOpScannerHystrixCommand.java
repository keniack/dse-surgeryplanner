package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;
import org.json.JSONObject;

public class StatusOpScannerHystrixCommand extends GenericHystrixCommand {

    public StatusOpScannerHystrixCommand(String group, String askKey) {
        super(group, askKey);
        setUrl(PropertiesUtil.getValue("opscanner.url.status"));
    }

    @Override
    protected String run() throws Exception {
        try {
            return callApiGetMethodString(opscannerUrl, url, new JSONObject());
        } catch (Throwable e) {
            throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
        }
    }

    @Override
    protected String getFallback() {
        return "FEHLER";
    }
}

