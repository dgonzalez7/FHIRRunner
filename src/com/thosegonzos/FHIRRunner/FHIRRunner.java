package com.thosegonzos.FHIRRunner;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
// import javax.ws.rs.core.MediaType;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class FHIRRunner 
{
	private static HashMap<String, Patient> patientTable = new HashMap<String, Patient>();
	private static HashMap<String, Observation> observationTable = new HashMap<String, Observation>();
	// private static HashMap<Integer, PatientObservation> patientObservationTable = new HashMap<Integer, PatientObservation>();
	private static ArrayList<PatientObservation> patientObservationTable = new ArrayList<PatientObservation>();
	
	public static void main(String[] args) 
	{
		// Get all Patients
		getAllPatients();
		
		// Get all Observations
		getAllObservations();
		
		// Get all Conditions
		// getAllConditions();
		
		// Get all Medication
		//getAllMedicationDispence();
		
		lookForInterestingPatient();		
	}

	
	private static void lookForInterestingPatient() 
	{
		for (int i = 1; i <= 100; i++)
		{
			for (Map.Entry<String, Observation> entry : observationTable.entrySet()) 
			{
				String loincCode = entry.getValue().getLoincCode();

				final String URI_BASE = "http://polaris.i3l.gatech.edu:8080/gt-fhir-webapp/base";
				// String msg = "/Patient/1";

				// HashMap<String, Integer> observationMap = new HashMap<String, Integer>();

				// Create a client
				Client client = ClientBuilder.newClient();

				// Set a target
				String s = "?code=http://loinc.org%7C" + loincCode + "&patient=" + i;
				String s2 = URI_BASE + "/Observation" + s;
				WebTarget target = client.target(s2);
				// System.out.println(s2);

				// WebTarget target = client.target(URI_BASE + msg);

				// Get a response
				String result = target.request().get(String.class);

				// System.out.println(result);
				// System.out.println("\nResult length: " + result.length());

				JSONParser parser = new JSONParser();

				if (!loincCode.equals("55284-4"))
				{
					try 
					{
						JSONObject jsonResult = (JSONObject) parser.parse(result);

						JSONArray patients = (JSONArray) jsonResult.get("entry");
						// System.out.println("Size: " + patients.size());

						Iterator iEntry = patients.iterator();
						while (iEntry.hasNext()) 
						{
							JSONObject jsonEntry = (JSONObject) iEntry.next();
							// System.out.println("fullUrl "+ jsonEntry.get("fullUrl"));

							JSONObject resources = (JSONObject) jsonEntry.get("resource");
							String id = (String) resources.get("id");
							String status = (String) resources.get("status");
							String effectiveDateTime = (String) resources.get("effectiveDateTime");
							String effectiveDate = effectiveDateTime.substring(0, 10);
							String effectiveTime = effectiveDateTime.substring(11, 19);

							// System.out.println("Id: " + id + " Gender: " + gender + " Birth Date: " + birthDate);

							JSONObject valueQuantity = (JSONObject) resources.get("valueQuantity");
							double value = (double) valueQuantity.get("value");
							String unit = (String) valueQuantity.get("unit");
							// System.out.println(i + ", " + loincCode + ", " + effectiveDate + ", " + effectiveTime + ", " + value + ", " + unit);
							
							buildPatientObservationTable(i, loincCode, effectiveDate, effectiveTime, value, unit);
						}
					}
					catch (ParseException e) 
					{
						e.printStackTrace();
					}
				}
				else
				{
					try 
					{
						JSONObject jsonResult = (JSONObject) parser.parse(result);

						JSONArray patients = (JSONArray) jsonResult.get("entry");
						// System.out.println("Size: " + patients.size());

						Iterator iEntry = patients.iterator();
						while (iEntry.hasNext()) 
						{
							JSONObject jsonEntry = (JSONObject) iEntry.next();
							// System.out.println("fullUrl "+ jsonEntry.get("fullUrl"));

							JSONObject resources = (JSONObject) jsonEntry.get("resource");
							JSONArray component = (JSONArray) jsonEntry.get("component");
							
							Iterator iComponent = patients.iterator();
							while (iComponent.hasNext()) 
							{
								JSONObject jsonComponent = (JSONObject) iComponent.next();
								
							/*	
								JSONObject valueQuantity = (JSONObject) resources.get("valueQuantity");
								double value = (double) valueQuantity.get("value");
								String unit = (String) valueQuantity.get("unit");
								System.out.println(i + ", " + loincCode + ", " + effectiveDateTime + ", " + value + ", " + unit);
													
							
							String id = (String) resources.get("id");
							String status = (String) resources.get("status");
							String effectiveDateTime = (String) resources.get("effectiveDateTime");


							// System.out.println("Id: " + id + " Gender: " + gender + " Birth Date: " + birthDate);

							JSONObject valueQuantity = (JSONObject) resources.get("valueQuantity");
							double value = (double) valueQuantity.get("value");
							String unit = (String) valueQuantity.get("unit");
							System.out.println(i + ", " + loincCode + ", " + effectiveDateTime + ", " + value + ", " + unit);
							*/
							}
						}
					}
					catch (ParseException e) 
					{
						e.printStackTrace();
					}
				}
			}
		}	
		writePatientObservationFile();
	}


	private static void buildPatientObservationTable(int i, String loincCode,
			String effectiveDate, String effectiveTime, double value, String unit) 
	{
		PatientObservation po = new PatientObservation();
		po.setPatientId(i);
		po.setLoincCode(loincCode);
		po.setEffectiveDate(effectiveDate);
		po.setEffectiveTime(effectiveTime);
		po.setValue(value);
		po.setUnit(unit);
		patientObservationTable.add(po);
	}


	private static void writePatientObservationFile() 
	{
		try 
		{
			FileWriter fw = new FileWriter("PatientObservation.csv");
			BufferedWriter bw = new BufferedWriter(fw);
			
			for (int i = 0; i < patientObservationTable.size(); i++)
			// for (Map.Entry<Integer, PatientObservation> entry : patientObservationTable.entrySet()) 
			{
				int id = patientObservationTable.get(i).getPatientId();
				String loincCode = patientObservationTable.get(i).getLoincCode();
				String effectiveDate = patientObservationTable.get(i).getEffectiveDate();
				String effectiveTime = patientObservationTable.get(i).getEffectiveTime();
				double value = patientObservationTable.get(i).getValue();
				String unit = patientObservationTable.get(i).getUnit();
				
				bw.write(id + ", " + loincCode + ", " + effectiveDate + ", " + effectiveTime + ", " + value + ", " + unit + "\n");
			}
			bw.close();
			fw.close();
		}
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	
	private static void getAllMedicationDispence() 
	{
		final String URI_BASE = "http://polaris.i3l.gatech.edu:8080/gt-fhir-webapp/base";
		// String msg = "/Patient/1";
		
		HashMap<String, Integer> observationMap = new HashMap<String, Integer>();
		
		// Create a client
		Client client = ClientBuilder.newClient();
		
		// Configure client (optional)

		// Set a target
		WebTarget target = client.target(URI_BASE + "/MedicationDispense");
		// WebTarget target = client.target(URI_BASE + msg);

		// Get a response
		// String result = target.request(MediaType.TEXT_XML).get(String.class);
		String result = target.request().get(String.class);

		System.out.println(result);
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
				// String status = (String) resources.get("status");

				// System.out.println("Id: " + id + " Gender: " + gender + " Birth Date: " + birthDate);

				JSONObject medication = (JSONObject) resources.get("medicationCodeableConcept");
				
				JSONArray coding = (JSONArray) medication.get("coding");

				Iterator iCoding = coding.iterator();

				while (iCoding.hasNext()) 
				{
					JSONObject innerObj = (JSONObject) iCoding.next();
					String system = (String) innerObj.get("system");
					String code2 = (String) innerObj.get("code");

					System.out.println("Id: " + id + " System: "+ system + " Code: " + code2);

					if (!system.equals("http://www.nlm.nih.gov/research/umls/rxnorm"))
					{
						System.out.println("Unknow Observation System: " + system);
					}
					else
					{
						buildConditionTable(observationMap, code2);
					}
				}
			}
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("\nSize of Medication Table: " + observationMap.size());
		printObservationTable(observationMap);
	}


	private static void getAllConditions() 
	{
		final String URI_BASE = "http://polaris.i3l.gatech.edu:8080/gt-fhir-webapp/base";
		// String msg = "/Patient/1";
		
		HashMap<String, Integer> observationMap = new HashMap<String, Integer>();
		
		// Create a client
		Client client = ClientBuilder.newClient();
		
		// Configure client (optional)

		// Set a target
		WebTarget target = client.target(URI_BASE + "/Condition");
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
				// String status = (String) resources.get("status");

				// System.out.println("Id: " + id + " Gender: " + gender + " Birth Date: " + birthDate);

				JSONObject code = (JSONObject) resources.get("code");
				
				JSONArray coding = (JSONArray) code.get("coding");
				
				// Handle an odd exception (Condition 194)
				if (coding != null)
				{
					Iterator iCoding = coding.iterator();

					while (iCoding.hasNext()) 
					{
						JSONObject innerObj = (JSONObject) iCoding.next();
						String system = (String) innerObj.get("system");
						String code2 = (String) innerObj.get("code");

						System.out.println("Id: " + id + " System: "+ system + " Code: " + code2);

						if (!system.equals("http://snomed.info/sct"))
						{
							System.out.println("Unknow Observation System: " + system);
						}
						else
						{
							buildConditionTable(observationMap, code2);
						}
					}
				}
			}
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("\nSize of Condition Table: " + observationMap.size());
		printObservationTable(observationMap);
	}

	private static void buildConditionTable(
			HashMap<String, Integer> observationMap, String code) 
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


	private static void getAllObservations() 
	{
		final String URI_BASE = "http://polaris.i3l.gatech.edu:8080/gt-fhir-webapp/base";
		// String msg = "/Patient/1";
		
		// HashMap<String, Integer> observationMap = new HashMap<String, Integer>();
		
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
					String display = (String) innerObj.get("display");

					System.out.println("Id: " + id + " Status: " + status + " System: "+ system + " Code: " + code2);
					
					if (!system.equals("http://loinc.org"))
					{
						System.out.println("Unknow Observation System: " + system);
					}
					else
					{
						buildObservationTable(code2, display);
					}
				}
			}
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("\nSize of Observation Table: " + observationTable.size());
		printObservationTable_NEW();
	}


	private static void buildObservationTable(String code, String display) 
	{
		if (observationTable.containsKey(code))
		{
			Observation observation = observationTable.get(code);
			observation.setLoincCode(code);
			observation.setDisplay(display);
			observation.setLoincCodeCount(observation.getLoincCodeCount()  + 1);
			observationTable.put(code, observation);
		}
		else
		{
			Observation observation = new Observation();
			observation.setLoincCode(code);
			observation.setDisplay(display);
			observation.setLoincCodeCount(1);
			observationTable.put(code, observation);
		}
	}
	
	
	private static void printObservationTable_NEW() 
	{
		for (Map.Entry<String, Observation> entry : observationTable.entrySet()) 
		{
		    int loincCodeCount = entry.getValue().getLoincCodeCount();
			System.out.println("Observation code = " + entry.getKey() + " , Display: " + entry.getValue().getDisplay() + " , Count = " + loincCodeCount);
		    // System.out.println(entry.getValue());
		}
	}
	
	
	private static void printObservationTable(HashMap<String, Integer> map) 
	{
		for (Map.Entry<String, Integer> entry : map.entrySet()) 
		{
		    System.out.println("Observation code = " + entry.getKey() + ", Count = " + entry.getValue());
		    // System.out.println(entry.getValue());
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
				
					buildPatientTable(id, gender, birthDate, state, zip);
				}
			}
		} 
		catch (ParseException e) 
		{
			e.printStackTrace();
		}
		
		System.out.println("\nSize of Patient Table: " + patientTable.size());
		printPatientTable();
	}
	
	private static void buildPatientTable(String id, String gender, String birthDate, String state, String zip) 
	{
		if (patientTable.containsKey(id))
		{
			Patient patient = patientTable.get(id);
			patient.setId(id);
			patient.setGender(gender);
			patient.setBirthDate(birthDate);
			patient.setState(state);
			patient.setPostalCode(zip);
			patientTable.put(id, patient);
		}
		else
		{
			Patient patient = new Patient();
			patient.setId(id);
			patient.setGender(gender);
			patient.setBirthDate(birthDate);
			patient.setState(state);
			patient.setPostalCode(zip);
			patientTable.put(id, patient);
		}
	}
	
	private static void printPatientTable() 
	{
		for (Map.Entry<String, Patient> entry : patientTable.entrySet()) 
		{
			String id = entry.getValue().getId();
			String gender = entry.getValue().getGender();
			String birthDate = entry.getValue().getBirthDate();
			String state = entry.getValue().getState();
			String postalCode = entry.getValue().getPostalCode();
			
			System.out.println(id + ", " + gender + ", " + birthDate + ", "  + state + ", " + postalCode);
		}
	}
}
