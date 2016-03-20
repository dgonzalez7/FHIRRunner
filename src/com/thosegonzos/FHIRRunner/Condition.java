package com.thosegonzos.FHIRRunner;

public class Condition 
{
	private String snomedCode = null;
	private String display = null;
	private int snomedCodeCount = 0;
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
	 * @return the display
	 */
	public String getDisplay() {
		return display;
	}
	/**
	 * @param display the display to set
	 */
	public void setDisplay(String display) {
		this.display = display;
	}
	/**
	 * @return the snomedCodeCount
	 */
	public int getSnomedCodeCount() {
		return snomedCodeCount;
	}
	/**
	 * @param snomedCodeCount the snomedCodeCount to set
	 */
	public void setSnomedCodeCount(int snomedCodeCount) {
		this.snomedCodeCount = snomedCodeCount;
	}
}
