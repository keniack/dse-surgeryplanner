import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.GregorianCalendar;

import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;

import dse.ss2016.group16.surgeryplanner.core.entity.OPSlot;
import dse.ss2016.group16.surgeryplanner.core.enums.OPType;
import dse.ss2016.group16.surgeryplanner.core.enums.SlotStatus;

public class ClientTest {

	public static void main (String args[]){

		OPSlot slot = new OPSlot();
		slot.setStartTime(new GregorianCalendar(2016,6,22,8,0,0).getTime());
    	slot.setEndTime(new GregorianCalendar(2016,6,22,10,0,0).getTime());
    	slot.setStatus(SlotStatus.AVAILABLE);
    	slot.setOPType(OPType.AUGEN);
    	
    	
		
	    try {
	    	JSONObject input = new JSONObject();
	    	input.put("type", slot.getType());
	    	input.put("status", slot.getStatus());
			input.put("startTime", "2016-08-26T08:00:00.000+01:00");
			input.put("endTime", "2016-08-26T10:00:00.000+01:00");
			String url = "http://localhost:9000/api";
			String method="/booking/create";
			SimpleClientHttpRequestFactory hrf = new SimpleClientHttpRequestFactory();
			hrf.setReadTimeout(60*60*1000); // wait one hour for response
			ClientHttpRequest req = hrf.createRequest(new URI(url+method+"/"), HttpMethod.POST);
			req.getHeaders().setContentType(MediaType.APPLICATION_JSON);
			String reqstr = input.toString();
			if (reqstr != null) {
				System.out.println("Calling API."+method+": "+reqstr);
				req.getBody().write(reqstr.getBytes("UTF-8"));
				ClientHttpResponse httpres = req.execute();
				HttpStatus httpstatus = httpres.getStatusCode();
				if (httpstatus.series() != HttpStatus.Series.SUCCESSFUL) {
					throw new HttpClientErrorException(httpstatus, httpres.getStatusText());
				}
				Charset cs = httpres.getHeaders().getContentType().getCharSet();
				if (cs == null) cs = Charset.forName("UTF-8");
				InputStreamReader bodyreader = new InputStreamReader(httpres.getBody(), cs);
				String body = FileCopyUtils.copyToString(bodyreader);
				System.out.println("Response from API."+method+": "+body);
				OPSlot response = (OPSlot) new ObjectMapper().readValue(body, OPSlot.class);
				System.out.println("Slot created:"+ response);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		
	}
	
	
}
