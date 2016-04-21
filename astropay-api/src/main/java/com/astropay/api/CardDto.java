package com.astropay.api;

public class CardDto {

	private String xTransKey;
	private String xVersion;
	private String xCountry;
	private String xCpf;
	private String xName;
	private String xEmail;
	private String ccNumber;
	private String ccExpMonth;
	private String ccExpYear;
	private String ccCvv;
	private String control;

	public String getxTransKey() {
		return this.xTransKey;
	}

	public void setxTransKey(String xTransKey) {
		this.xTransKey = xTransKey;
	}

	public String getxVersion() {
		return this.xVersion;
	}

	public void setxVersion(String xVersion) {
		this.xVersion = xVersion;
	}

	public String getxCountry() {
		return this.xCountry;
	}

	public void setxCountry(String xCountry) {
		this.xCountry = xCountry;
	}

	public String getxCpf() {
		return this.xCpf;
	}

	public void setxCpf(String xCpf) {
		this.xCpf = xCpf;
	}

	public String getxName() {
		return this.xName;
	}

	public void setxName(String xName) {
		this.xName = xName;
	}

	public String getxEmail() {
		return this.xEmail;
	}

	public void setxEmail(String xEmail) {
		this.xEmail = xEmail;
	}

	public String getCcNumber() {
		return this.ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public String getCcExpMonth() {
		return this.ccExpMonth;
	}

	public void setCcExpMonth(String ccExpMonth) {
		this.ccExpMonth = ccExpMonth;
	}

	public String getCcExpYear() {
		return this.ccExpYear;
	}

	public void setCcExpYear(String ccExpYear) {
		this.ccExpYear = ccExpYear;
	}

	public String getCcCvv() {
		return this.ccCvv;
	}

	public void setCcCvv(String ccCvv) {
		this.ccCvv = ccCvv;
	}

	public String getControl() {
		return this.control;
	}

	public void setControl(String control) {
		this.control = control;
	}
}
