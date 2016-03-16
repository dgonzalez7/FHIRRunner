package com.thosegonzos.FHIRRunner;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

public class FHIRRunner 
{

	public static void main(String[] args) 
	{
		// Create a client
		Client client = ClientBuilder.newClient();
		
		// Configure client (optional)
		
		// Set a target
		WebTarget target = client.target("http://polaris.i3l.gatech.edu:8080/gt-fhir-webapp/base/Patient/1");
		
		// Get a response
		String result = target.request(MediaType.TEXT_XML).get(String.class);
		
		System.out.println(result);
	}

}
