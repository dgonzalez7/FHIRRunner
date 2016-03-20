package com.thosegonzos.FHIRRunner;

public class PatientCondition 
{
	private int patientId = 0;
	private String snomedCode = null;
	
	private String status = null;
	private String onsetDateTime = null;
	private String onsetDate = null;
	private String onsetTime = null;
	/**
	 * @return the patientId
	 */
	public int getPatientId() {
		return patientId;
	}
	/**
	 * @param patientId the patientId to set
	 */
	public void setPatientId(int patientId) {
		this.patientId = patientId;
	}
	/**
	 * @return the snomedCode
	 */
	public String getSnomedCode() {
		return snomedCode;
	}
	/**
	 * @param snomedCode the snomedCode to set
	 */
	public void setSnomedCode(String snomedCode) {
		this.snomedCode = snomedCode;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the onsetDateTime
	 */
	public String getOnsetDateTime() {
		return onsetDateTime;
	}
	/**
	 * @param onsetDateTime the onsetDateTime to set
	 */
	public void setOnsetDateTime(String onsetDateTime) {
		this.onsetDateTime = onsetDateTime;
	}
	/**
	 * @return the onsetDate
	 */
	public String getOnsetDate() {
		return onsetDate;
	}
	/**
	 * @param onsetDate the onsetDate to set
	 */
	public void setOnsetDate(String onsetDate) {
		this.onsetDate = onsetDate;
	}
	/**
	 * @return the onsetTime
	 */
	public String getOnsetTime() {
		return onsetTime;
	}
	/**
	 * @param onsetTime the onsetTime to set
	 */
	public void setOnsetTime(String onsetTime) {
		this.onsetTime = onsetTime;
	}	
}
