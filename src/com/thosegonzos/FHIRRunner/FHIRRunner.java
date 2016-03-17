package com.thosegonzos.FHIRRunner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class FHIRRunner 
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
		
		System.out.println(result);
		System.out.println("\nResult length: " + result.length());
	}

}
