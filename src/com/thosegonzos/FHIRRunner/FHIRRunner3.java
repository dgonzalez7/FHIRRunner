package com.thosegonzos.FHIRRunner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

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
		// Get all Patients
		// getAllPatients();
		
		// Get all Observations
		getAllObservations();
	}

	private static void getAllObservations() 
	{
		final String URI_BASE = "http://polaris.i3l.gatech.edu:8080/gt-fhir-webapp/base";
		// String msg = "/Patient/1";
		
		HashMap<String, Integer> observationMap = new HashMap<String, Integer>();
		
		// Create a client
		Client client = ClientBuilder.newClient();
		
		// Configure client (optional)

		// Set a target
		WebTarget target = client.target(URI_BASE + "/Observation");
		// WebTarget target = client.target(URI_BASE + msg);

		// Get a response
		// String result = target.request(MediaType.TEXT_XML).get(String.class);
		String result = target.request().get(String.class);

		// System.out.println(result);
		System.out.println("\nResult length: " + result.length());

		JSONParser parser = new JSONParser();
		
		try 
		{
			JSONObject jsonResult = (JSONObject) parser.parse(result);

			JSONArray patients = (JSONArray) jsonResult.get("entry");
			System.out.println("Size: " + patients.size());

			Iterator iEntry = patients.iterator();
			while (iEntry.hasNext()) 
			{
				JSONObject jsonEntry = (JSONObject) iEntry.next();
				// System.out.println("fullUrl "+ jsonEntry.get("fullUrl"));

				JSONObject resources = (JSONObject) jsonEntry.get("resource");
				String id = (String) resources.get("id");
				String status = (String) resources.get("status");

				// System.out.println("Id: " + id + " Gender: " + gender + " Birth Date: " + birthDate);

				JSONObject code = (JSONObject) resources.get("code");
				JSONArray coding = (JSONArray) code.get("coding");
				
				Iterator iCoding = coding.iterator();

				while (iCoding.hasNext()) 
				{
					JSONObject innerObj = (JSONObject) iCoding.next();
					String system = (String) innerObj.get("system");
					String code2 = (String) innerObj.get("code");

					System.out.println("Id: " + id + " Status: " + status + " System: "+ system + " Code: " + code2);
					
					if (system.equals(""))
					{
						System.out.println("Unknow Observation System: " + system);
					}
					else
					{
						buildObservationTable(observationMap, code2);
					}
				}
			}
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("\nSize of Observation Table: " + observationMap.size());
		printObservationTable(observationMap);
	}


	private static void buildObservationTable(HashMap<String, Integer> observationMap, 
			String code) 
	{
		if (observationMap.containsKey(code))
		{
			int val = observationMap.get(code);
			observationMap.put(code, val + 1);
		}
		else
		{
			observationMap.put(code, 1);
		}
	}
	
	
	private static void printObservationTable(HashMap<String, Integer> map) 
	{
		for (Map.Entry<String, Integer> entry : map.entrySet()) 
		{
		    System.out.println("Observation code = " + entry.getKey() + ", Count = " + entry.getValue());
		}
	}
	

	/**
	 * Get all Patient Record
	 */
	private static void getAllPatients() 
	{
		final String URI_BASE = "http://polaris.i3l.gatech.edu:8080/gt-fhir-webapp/base";
		// String msg = "/Patient/1";

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

			Iterator iEntry = patients.iterator();
			while (iEntry.hasNext()) 
			{
				JSONObject jsonEntry = (JSONObject) iEntry.next();
				// System.out.println("fullUrl "+ jsonEntry.get("fullUrl"));

				JSONObject resources = (JSONObject) jsonEntry.get("resource");
				String id = (String) resources.get("id");
				String gender = (String) resources.get("gender");
				String birthDate = (String) resources.get("birthDate");
				// System.out.println("Id: " + id + " Gender: " + gender + " Birth Date: " + birthDate);

				JSONArray address = (JSONArray) resources.get("address");
				Iterator iAddress = address.iterator();

				while (iAddress.hasNext()) 
				{
					JSONObject innerObj = (JSONObject) iAddress.next();
					String state = (String) innerObj.get("state");
					String zip = (String) innerObj.get("postalCode");

					System.out.println("Id: " + id + " Gender: " + gender + " Birth Date: " + birthDate + "State: "+ state + " Zip: " + zip);
				}
			}
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
	}
}
