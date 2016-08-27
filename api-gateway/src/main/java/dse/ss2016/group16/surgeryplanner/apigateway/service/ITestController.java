package dse.ss2016.group16.surgeryplanner.apigateway.service;

import org.json.JSONException;
import org.json.JSONObject;

import dse.ss2016.group16.surgeryplanner.core.entity.Notification;

public interface ITestController {

    /**
     * Simple method to test if the service is responsive
     * @return a hello World Test message
     */
    String helloWorld();

	/**
	 * Create TestData in all Modules
	 * @return
	 */
	String addTestData() throws JSONException;
	
	/**
	 * Delete all Data from Modules
	 * @return
	 */
	String deleteAll();

	/**
	 * Add a Notification
	 * @param entry Notification details
	 * @return added Notification
	 * @throws JSONException 
	 */
	String addNotification(String entry) throws JSONException;
	
}
