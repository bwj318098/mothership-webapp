package com.acss.core.dataentry.common;

import com.acss.core.dataentry.common.constants.RefRelationship;

public class ReferenceData {
	
	private NameField refName;
	private AddressField refAddress;
	private RefRelationship relationship;
	private PhoneField refPhone;
	private PhoneField refMobile;
	private PhoneField refCorpPhone;
	private String companyName;
	
	public NameField getRefName() {
		return refName;
	}
	public void setRefName(NameField refName) {
		this.refName = refName;
	}
	public AddressField getRefAddress() {
		return refAddress;
	}
	public void setRefAddress(AddressField refAddress) {
		this.refAddress = refAddress;
	}
	public RefRelationship getRelationship() {
		return relationship;
	}
	public void setRelationship(RefRelationship relationship) {
		this.relationship = relationship;
	}
	public PhoneField getRefPhone() {
		return refPhone;
	}
	public void setRefPhone(PhoneField refPhone) {
		this.refPhone = refPhone;
	}
	public PhoneField getRefMobile() {
		return refMobile;
	}
	public void setRefMobile(PhoneField refMobile) {
		this.refMobile = refMobile;
	}
	public PhoneField getRefCorpPhone() {
		return refCorpPhone;
	}
	public void setRefCorpPhone(PhoneField refCorpPhone) {
		this.refCorpPhone = refCorpPhone;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	
}
