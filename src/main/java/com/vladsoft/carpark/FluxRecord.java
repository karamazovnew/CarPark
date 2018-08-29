package com.vladsoft.carpark;

public class FluxRecord {
	private String tokenId;
	private String inDate;
	private String inTime;
	private String payDate;
	private String payTime;
	private String section;
	private String payed;

	
	public String getTokenId() {
		return tokenId;
	}
	public void setTokenId(String tokenId) {
		this.tokenId = (tokenId==null? "" : tokenId);		
	}
	public String getInDate() {
		return inDate;
	}
	public void setInDate(String inDate) {
		this.inDate = (inDate==null? "" : inDate);		
	}
	public String getInTime() {
		return inTime;
	}
	public void setInTime(String inTime) {
		this.inTime = (inTime==null? "" : inTime);
	}
	public String getPayDate() {
		return payDate;
	}
	public void setPayDate(String payDate) {
		this.payDate = (payDate==null? "" : payDate);
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = (payTime==null? "" : payTime);
	}
	public String getSection() {
		return section;
	}
	public void setSection(String section) {
		this.section = (section==null? "" : section);
	}
	public String getPayed() {
		return payed;
	}
	public void setPayed(String payed) {
		this.payed = (payed==null? "" : payed);
	}
	
}
