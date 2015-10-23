package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum TypeOfId {
	
	SSS_GSIS(1,"idtype.sss"),
	TIN(2,"idtype.tin"),
	PRC(6,"idtype.prc"),
	PASSPORT(5,"idtype.passport"),
	DRIVERSLICENCE(7,"idtype.licence"),
	COMPANYID(8,"idtype.company"),
	OWWACARD(9,"idtype.owwa");
	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "idTypeList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, TypeOfId> lookupByValue = new HashMap<String, TypeOfId>();
		public static final Map<BigDecimal, TypeOfId> lookupByCode = new HashMap<BigDecimal, TypeOfId>();
	}
	
	TypeOfId(int code, String value) {
		this.code = code;
		this.value = value;
		BootstrapSingleton.lookupByValue.put(value, this);
		BootstrapSingleton.lookupByCode.put(new BigDecimal(code), this);
	}
	
	public int getCode() {
		return code;
	}

	public String getValue() {
		return value;
	}
	
}
