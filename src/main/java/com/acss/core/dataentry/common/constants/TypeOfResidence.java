package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum TypeOfResidence {
	
	OWNED(1,"residence.owned"),
	PARENTOWNED(2,"residence.parent"),
	MORTGAGED(3,"residence.mortgaged"),
	RENTED(4,"residence.rent"),
	OTHERS(5,"residence.others");
	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "typeOfResidenceList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, TypeOfResidence> lookupByValue = new HashMap<String, TypeOfResidence>();
		public static final Map<BigDecimal, TypeOfResidence> lookupByCode = new HashMap<BigDecimal, TypeOfResidence>();
	}
	
	TypeOfResidence(int code, String value) {
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
