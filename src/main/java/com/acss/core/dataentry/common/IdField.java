package com.acss.core.dataentry.common;

import com.acss.core.dataentry.common.constants.TypeOfId;

public class IdField {
	
	private TypeOfId idType;
	private String idCardNo;
	
	public TypeOfId getIdType() {
		return idType;
	}
	public void setIdType(TypeOfId idType) {
		this.idType = idType;
	}
	public String getIdCardNo() {
		return idCardNo;
	}
	public void setIdCardNo(String idCardNo) {
		this.idCardNo = idCardNo;
	}
	
}
