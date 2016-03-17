package com.thosegonzos.FHIRRunner;

import java.util.Iterator;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FHIRRunner3 
{

	public static void main(String[] args) 
	{
		final String URI_BASE = "http://polaris.i3l.gatech.edu:8080/gt-fhir-webapp/base";
		String msg = "/Patient/1";
		
		// Create a client
		Client client = ClientBuilder.newClient();
		
		// Configure client (optional)
		
		// Set a target
		WebTarget target = client.target(URI_BASE + "/Patient");
		// WebTarget target = client.target(URI_BASE + msg);
		
		// Get a response
		// String result = target.request(MediaType.TEXT_XML).get(String.class);
		String result = target.request().get(String.class);
		
		// System.out.println(result);
		// System.out.println("\nResult length: " + result.length());
		
		JSONParser parser = new JSONParser();
		try 
		{
			JSONObject jsonResult = (JSONObject) parser.parse(result);
			
			JSONArray patients = (JSONArray) jsonResult.get("entry");
			System.out.println("Size: " + patients.size());
			
			// String id = (String) jsonResult.get("id");
            // String gender = (String) jsonResult.get("gender");
            // String birthDate = (String) jsonResult.get("birthDate");

			Iterator i = patients.iterator();
            while (i.hasNext()) 
            {
                JSONObject jsonEntry = (JSONObject) i.next();
                // System.out.println("fullUrl "+ jsonEntry.get("fullUrl"));
                
    			JSONObject resources = (JSONObject) jsonEntry.get("resource");
    			String id = (String) resources.get("id");
                String gender = (String) resources.get("gender");
                String birthDate = (String) resources.get("birthDate");
				System.out.println("Id: " + id + " Gender: " + gender + " Birth Date: " + birthDate);
    			
            }
			
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}	
	}
}
