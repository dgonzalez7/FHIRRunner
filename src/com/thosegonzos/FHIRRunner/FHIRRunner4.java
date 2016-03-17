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

public class FHIRRunner4 
{

	public static void main(String[] args) 
	{
		final int PATIENT_MAX = 10; 
		final String URI_BASE = "http://polaris.i3l.gatech.edu:8080/gt-fhir-webapp/base";
		
		// Create a client
		Client client = ClientBuilder.newClient();
		
		// Configure client (optional)
		
		for (int i = 1; i <= PATIENT_MAX; i++)
		{
			// Set a target
			// WebTarget target = client.target(URI_BASE + "/Patient");
			String msg = "/Patient/" + i;
			WebTarget target = client.target(URI_BASE + msg);

			// Get a response
			// String result = target.request(MediaType.TEXT_XML).get(String.class);
			String result = target.request().get(String.class);

			// System.out.println(result);
			// System.out.println("\nResult length: " + result.length());

			JSONParser parser = new JSONParser();
			try 
			{
				JSONObject jsonPatient = (JSONObject) parser.parse(result);

				String id = (String) jsonPatient.get("id");
				String gender = (String) jsonPatient.get("gender");
				String birthDate = (String) jsonPatient.get("birthDate");

				System.out.println("Id: " + id + " Gender: " + gender + " Birth Date: " + birthDate);
				
	            JSONArray address = (JSONArray) jsonPatient.get("address");
	            Iterator iter = address.iterator();
	            
	            while (iter.hasNext()) 
	            {
	                JSONObject innerObj = (JSONObject) iter.next();
					String state = (String) innerObj.get("state");
					String zip = (String) innerObj.get("postalCode");

	                System.out.println("State: "+ state + " Zip: " + zip);
	            }
			} 
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
		}
	}
}
