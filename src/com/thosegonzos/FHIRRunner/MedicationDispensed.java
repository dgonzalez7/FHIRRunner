package com.thosegonzos.FHIRRunner;

public class MedicationDispensed 
{
	// private String id = null;
	private String system = null;
	private String code = null;
	private int medCodeCount = 0;

	/**
	 * @return the system
	 */
	public String getSystem() {
		return system;
	}
	/**
	 * @param system the system to set
	 */
	public void setSystem(String system) {
		this.system = system;
	}
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}
	/**
	 * @return the medCodeCount
	 */
	public int getMedCodeCount() {
		return medCodeCount;
	}
	/**
	 * @param medCodeCount the medCodeCount to set
	 */
	public void setMedCodeCount(int medCodeCount) {
		this.medCodeCount = medCodeCount;
	}
	
	
}
