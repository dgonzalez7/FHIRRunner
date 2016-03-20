package com.thosegonzos.FHIRRunner;

public class PatientObservation 
{
	private int patientId = 0;
	private String loincCode = null;
	
	private String status = null;
	private String effectiveDateTime = null;
	private String effectiveDate = null;
	private String effectiveTime = null;
	private double value = 0.0;
	private String unit = null;
	
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
	 * @return the loincCode
	 */
	public String getLoincCode() {
		return loincCode;
	}
	/**
	 * @param loincCode the loincCode to set
	 */
	public void setLoincCode(String loincCode) {
		this.loincCode = loincCode;
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
	 * @return the effectiveDateTime
	 */
	public String getEffectiveDateTime() {
		return effectiveDateTime;
	}
	/**
	 * @param effectiveDateTime the effectiveDateTime to set
	 */
	public void setEffectiveDateTime(String effectiveDateTime) {
		this.effectiveDateTime = effectiveDateTime;
	}
	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
	/**
	 * @return the unit
	 */
	public String getUnit() {
		return unit;
	}
	/**
	 * @param unit the unit to set
	 */
	public void setUnit(String unit) {
		this.unit = unit;
	}
	/**
	 * @return the effectiveDate
	 */
	public String getEffectiveDate() {
		return effectiveDate;
	}
	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	/**
	 * @return the effectiveTime
	 */
	public String getEffectiveTime() {
		return effectiveTime;
	}
	/**
	 * @param effectiveTime the effectiveTime to set
	 */
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
}
