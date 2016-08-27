package dse.ss2016.group16.surgeryplanner.apigateway.service.impl;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONException;
import org.json.simple.JSONObject;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpRequest;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.HttpClientErrorException;

import com.netflix.hystrix.HystrixCommand;



public abstract class GenericApiController {
	
	static final int TIMEOUT = 60000;
	
	protected String callApiGetMethod(String url, String method)  {
		SimpleClientHttpRequestFactory hrf = new SimpleClientHttpRequestFactory();
		hrf.setReadTimeout(60*60*1000); // wait one hour for response
		try {
			ClientHttpRequest req = hrf.createRequest(new URI(url+method+"/"), HttpMethod.GET);
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
			return body;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	protected static Map<String, String> doBackendAsyncServiceCall(List<Callable<AsyncResponse>> callables) {
	        ExecutorService executorService = Executors.newFixedThreadPool(1);
	        try {
	            List<Future<AsyncResponse>> futures = executorService.invokeAll(callables);
	            executorService.shutdown();
	            executorService.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
	            Map<String, String> result = new HashMap<>();
	            for (Future<AsyncResponse> future : futures) {
	                AsyncResponse response = future.get();
	                result.put(response.serviceKey, response.response);
	            }
	            return result;
	        } catch (InterruptedException|ExecutionException e) {
	            throw new RuntimeException(e);
	        }
	    }
	
	
	protected static List<String> getJsonObjectsFromBackendAsyncServiceCall(List<Callable<AsyncResponse>> callables) {
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		try {
			List<Future<AsyncResponse>> futures = executorService.invokeAll(callables);
			executorService.shutdown();
			executorService.awaitTermination(TIMEOUT, TimeUnit.MILLISECONDS);
			List<String> result = new ArrayList<>();
			for (Future<AsyncResponse> future : futures) {
				AsyncResponse response = future.get();
				 result.add(response.response);
			}
			return result;
		} catch (InterruptedException|ExecutionException e) {
			throw new RuntimeException(e);
		}
	}
	    
    protected static class AsyncResponse {
        private final String serviceKey;
        private final String response;
        
        AsyncResponse(String serviceKey, String response) {
            this.serviceKey = serviceKey;
            this.response = response;
        }
    }
    protected static class BackendServiceCallable implements Callable<AsyncResponse> {
        private final String serviceKey;
        private final HystrixCommand<String> hystrixCommand;
        
        public BackendServiceCallable(String serviceKey, HystrixCommand<String> hystrixCommand) {
            this.serviceKey = serviceKey;
            this.hystrixCommand = hystrixCommand;
        }
        @Override
        public AsyncResponse call() throws Exception {
            return new AsyncResponse(serviceKey, hystrixCommand.execute());
        }
    }
	    
    public Object convertJsonToObject(JSONObject obj, Class classT) {
			
		try {
			return new ObjectMapper().readValue(obj.toString(), classT);
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



	protected String callApiDeleteMethod(String url, String method) {
		SimpleClientHttpRequestFactory hrf = new SimpleClientHttpRequestFactory();
		hrf.setReadTimeout(60*60*1000); // wait one hour for response
		try {
			ClientHttpRequest req = hrf.createRequest(new URI(url+method+"/"), HttpMethod.DELETE);
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
			System.out.println("Response from API. "+method+": "+body);
			return body;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
	
	protected String callApiPostMethod(String url, String method, JSONObject input) {
		SimpleClientHttpRequestFactory hrf = new SimpleClientHttpRequestFactory();
		hrf.setReadTimeout(60*60*1000); // wait one hour for response
		try {
			String reqstr = input.toString();
			ClientHttpRequest req = hrf.createRequest(new URI(url+method+"/"), HttpMethod.POST);
			System.out.println("Calling API."+method+": "+reqstr);
			if (reqstr != null) {
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
			System.out.println("Response from API. "+method+": "+body);
			return body;
		} catch (Throwable t) {
			t.printStackTrace();
		}
		return null;
	}
    
    public List<Object> doCallAndGetObjectListFromResult(List<Callable<AsyncResponse>> callables, Class classT) {
    	List<String> result=getJsonObjectsFromBackendAsyncServiceCall(callables);  
    	List<Object> objectList= new ArrayList<>();
    	if(result ==null || result.isEmpty())
    		return objectList;

		Object object = null;
		JSONArray arrayObj=null;
		JSONParser jsonParser=new JSONParser();
		try {
			object=jsonParser.parse(result.get(0));
		} catch (ParseException e) {
			e.printStackTrace();
			return objectList;
		}
		arrayObj=(JSONArray) object;


		for(int i = 0; i<arrayObj.size(); i++){

			objectList.add( convertJsonToObject((JSONObject)arrayObj.get(i), classT));

		}

    	return objectList;
    }




    public String getSingleJsonFromSingleCall(List<Callable<AsyncResponse>> callables) {
    	List<String> result=getJsonObjectsFromBackendAsyncServiceCall(callables);  
    	if(result == null || result.isEmpty())
    		return "";
    	
    	return result.get(0);
    	
    }
    public Object getSingleObjectFromSingleCall(List<Callable<AsyncResponse>> callables, Class classT) {
    	
    	List<String> result = getJsonObjectsFromBackendAsyncServiceCall(callables);  
    	if(result == null || result.isEmpty() )
    		return null;

		Object object = null;
		JSONParser jsonParser=new JSONParser();
		try {
			object=jsonParser.parse(result.get(0));
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
		return convertJsonToObject((JSONObject) object, classT);

    	
    }

}
