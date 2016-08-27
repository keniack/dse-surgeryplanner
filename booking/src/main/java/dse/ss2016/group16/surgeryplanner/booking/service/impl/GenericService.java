package dse.ss2016.group16.surgeryplanner.booking.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
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

/**
 *Created by Cynthia Marcelino (cynthia.marcelino@student.tuwien.ac.at) on 3:07:17 PM
 *
 */
public class GenericService {
	
	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	protected JSONObject callApiGetMethodFindById(String url, String method,String id) throws Throwable {
		SimpleClientHttpRequestFactory hrf = new SimpleClientHttpRequestFactory();
		hrf.setReadTimeout(60*60*1000); // wait one hour for response
		try {
			JSONObject obj = new JSONObject();
			obj.put("id", id);
			String reqstr = obj.toString();
			ClientHttpRequest req = hrf.createRequest(new URI(url+method+"/"), HttpMethod.GET);
			System.out.println("Calling "+url+method + " body:" + reqstr);
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
		} catch (Throwable t) {
			t.printStackTrace();
			throw t;
		}
	}
	
	protected JSONObject callApiGetMethodFindByIdURL(String url, String method,String id) throws Throwable {
		SimpleClientHttpRequestFactory hrf = new SimpleClientHttpRequestFactory();
		hrf.setReadTimeout(60*60*1000); // wait one hour for response
		try {

			ClientHttpRequest req = hrf.createRequest(new URI(url+method+"/"+id), HttpMethod.GET);
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
	protected JSONObject callPostGetMethodFindByIdURL(String url, String method,JSONObject input) throws Throwable {
		SimpleClientHttpRequestFactory hrf = new SimpleClientHttpRequestFactory();
		hrf.setReadTimeout(60*60*1000); // wait one hour for response
		try {
			
			ClientHttpRequest req = hrf.createRequest(new URI(url+method), HttpMethod.POST);
			req.getHeaders().setContentType(MediaType.APPLICATION_JSON);
			System.out.println("Calling "+url+method);
			if(input!=null){
				String reqstr = input.toString();
				req.getBody().write(reqstr.getBytes("UTF-8"));
			}
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
	
	 public Object convertJsonToObject(JSONObject obj, Class classT) {
			
			try {
				return  new ObjectMapper().readValue(obj.toString(), classT);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

}
