package com.thosegonzos.FHIRRunner;

public class Observation 
{
	private String loincCode = null;
	private String display = null;
	private int loincCodeCount = 0;
	
	/**
	 * @return the loincCode
	 */
	public String getLoincCode() 
	{
		return loincCode;
	}
	/**
	 * @param loincCode the loincCode to set
	 */
	public void setLoincCode(String loincCode) 
	{
		this.loincCode = loincCode;
	}
	/**
	 * @return the loincCodeCount
	 */
	public int getLoincCodeCount() 
	{
		return loincCodeCount;
	}
	/**
	 * @param loincCodeCount the loincCodeCount to set
	 */
	public void setLoincCodeCount(int loincCodeCount) {
		this.loincCodeCount = loincCodeCount;
	}
	public String getDisplay() {
		return display;
	}
	public void setDisplay(String dsiplay) {
		this.display = dsiplay;
	}
}
