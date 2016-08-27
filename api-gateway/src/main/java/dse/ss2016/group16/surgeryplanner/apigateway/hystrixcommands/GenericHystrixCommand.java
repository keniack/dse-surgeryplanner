package dse.ss2016.group16.surgeryplanner.apigateway.hystrixcommands;

import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

import org.json.JSONObject;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;

import dse.ss2016.group16.surgeryplanner.apigateway.util.PropertiesUtil;

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 4:14:23 PM
 *
 */
public abstract class GenericHystrixCommand extends HystrixCommand<String>{
	
	static final int TIMEOUT = 60000;
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	TimeZone UTC_TIME_ZONE = TimeZone.getTimeZone("UTC");
	String masterDataUrl = PropertiesUtil.getValue("masterdata.url");
	String bookingUrl = PropertiesUtil.getValue("booking.url");
	String opscannerUrl = PropertiesUtil.getValue("opscanner.url");
	String ringmeUrl = PropertiesUtil.getValue("ringme.url");

	String url=null;

	public void setUrl(String url) {
		this.url=url;
		
	}

	public GenericHystrixCommand(String group, String askKey){
		super(HystrixCommand.Setter
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(group))
                .andCommandKey(HystrixCommandKey.Factory.asKey(askKey))
                .andCommandPropertiesDefaults(
                        HystrixCommandProperties.Setter()
                                .withExecutionIsolationThreadTimeoutInMilliseconds(TIMEOUT)));
	}
	

	
	protected String callApiGetMethod(String url, String method) throws Throwable {
		JSONObject obj = callApiGetMethod(url, method, null);
		if(obj!=null)
			return obj.toString();
		return null;
	}
	
	protected JSONObject callApiGetMethod(String url, String method, JSONObject input) throws Throwable {
		SimpleClientHttpRequestFactory hrf = new SimpleClientHttpRequestFactory();
		hrf.setReadTimeout(60*60*1000); // wait one hour for response
		try {
			ClientHttpRequest req = hrf.createRequest(new URI(url+method+"/"), HttpMethod.GET);
			if(input!=null){
				String reqstr = input.toString();
				req.getBody().write(reqstr.getBytes("UTF-8"));
			}
			System.out.println("Calling "+url+method);
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
			return new JSONObject(body);
		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}
	
	protected JSONObject callApiPostMethod(String url, String method, JSONObject input) {
		return callApiMethod(url,  method,  input, HttpMethod.POST);
	}
	protected JSONObject callApiPutMethod(String url, String method, JSONObject input) {
		return callApiMethod(url,  method,  input, HttpMethod.PUT);
	}
	protected JSONObject callApiDeleteMethod(String url, String method, JSONObject input) {
		return callApiMethod(url,  method,  input, HttpMethod.DELETE);
	}
	protected JSONObject callApiMethod(String url, String method, JSONObject input,HttpMethod httpMethod) {
		SimpleClientHttpRequestFactory hrf = new SimpleClientHttpRequestFactory();
		hrf.setReadTimeout(60*60*1000); // wait one hour for response
		try {
			ClientHttpRequest req = hrf.createRequest(new URI(url+method+"/"), httpMethod);
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
				return new JSONObject(body);
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	
	
	
	
	protected String callApiGetMethodString(String url, String method, JSONObject input) throws Throwable {
		SimpleClientHttpRequestFactory hrf = new SimpleClientHttpRequestFactory();
		hrf.setReadTimeout(60*60*1000); // wait one hour for response
		try {
			ClientHttpRequest req = hrf.createRequest(new URI(url+method+"/"), HttpMethod.GET);
			if(input!=null){
				String reqstr = input.toString();
				req.getBody().write(reqstr.getBytes("UTF-8"));
			}
			System.out.println("Calling "+url+method);
			ClientHttpResponse httpres = req.execute();
			HttpStatus httpstatus = httpres.getStatusCode();
			if (httpstatus.series() != HttpStatus.Series.SUCCESSFUL) {
				throw new HttpClientErrorException(httpstatus, httpres.getStatusText() + " " + httpres.getBody() );
			}
			Charset cs = httpres.getHeaders().getContentType().getCharSet();
			if (cs == null) cs = Charset.forName("UTF-8");
			InputStreamReader bodyreader = new InputStreamReader(httpres.getBody(), cs);
			String body = FileCopyUtils.copyToString(bodyreader);
			System.out.println("Response from API."+method+": "+body);
			return body;
		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}
	
	protected String callApiPostMethodString(String url, String method, JSONObject input) {
		return callApiMethodString(url,  method,  input, HttpMethod.POST);
	}
	protected String callApiPutMethodString(String url, String method, JSONObject input) {
		return callApiMethodString(url,  method,  input, HttpMethod.PUT);
	}
	protected String callApiDeleteMethodString(String url, String method, JSONObject input) {
		return callApiMethodString(url,  method,  input, HttpMethod.DELETE);
	}
	protected String callApiMethodString(String url, String method, JSONObject input,HttpMethod httpMethod) {
		SimpleClientHttpRequestFactory hrf = new SimpleClientHttpRequestFactory();
		hrf.setReadTimeout(60*60*1000); // wait one hour for response
		try {
			ClientHttpRequest req = hrf.createRequest(new URI(url+method+"/"), httpMethod);
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
				return body;
			}
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	
}
