package com.acss.core.dataentry.common;

import java.math.BigDecimal;

public class PhoneField {
	
	private Integer region;
	private BigDecimal phoneNo;
	//local or extension
	private Integer local;
	
	public Integer getRegion() {
		return region;
	}
	public void setRegion(Integer region) {
		this.region = region;
	}
	
	public BigDecimal getPhoneNo() {
		return phoneNo;
	}
	public void setPhoneNo(BigDecimal phoneNo) {
		this.phoneNo = phoneNo;
	}
	public Integer getLocal() {
		return local;
	}
	public void setLocal(Integer local) {
		this.local = local;
	}
	
	
}
