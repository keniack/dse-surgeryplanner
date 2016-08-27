package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import com.netflix.hystrix.exception.HystrixRuntimeException;
import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;
import dse.ss2016.group16.surgeryplanner.core.dto.OPScannerRequestWrapper;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Manuel Roblek (mroblek@tuwien.ac.at) on 13.06.2016.
 */
public class OPScannerHystrixCommand extends GenericHystrixCommand {

	private OPScannerRequestWrapper request;

	public OPScannerHystrixCommand(String group, String askKey, OPScannerRequestWrapper request) {
		super(group, askKey);
		this.request = request;
		setUrl(PropertiesUtil.getValue("opscanner.url.find"));
	}

	@Override
	protected String run() throws Exception {
		try {
			JSONObject obj = callApiPostMethod(opscannerUrl, PropertiesUtil.getValue("opscanner.url.find"),
					request.convertToJsonObject());
			return obj.toString();
		} catch (Throwable e) {
			throw new HystrixRuntimeException(null, null, e.getMessage(), null, e);
		}
	}

	@Override
	protected String getFallback() {
		return opscannerUrl + PropertiesUtil.getValue("opscanner.url.find")
				+ " unavailable. Please contact system administrator";
	}



}
