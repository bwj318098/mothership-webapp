package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum TypeOfEmployment {
	
	PRIVATE_SECTOR(1,"employment.private"),
	SELF_EMPLOYED(2,"employment.self"),
	GOVERNMENT_SECTOR(3,"employment.govsector"),
	RETIRED_UNEMPLOYED(4,"employment.retired");
	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "employmentTypeList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, TypeOfEmployment> lookupByValue = new HashMap<String, TypeOfEmployment>();
		public static final Map<BigDecimal, TypeOfEmployment> lookupByCode = new HashMap<BigDecimal, TypeOfEmployment>();
	}
	
	TypeOfEmployment(int code, String value) {
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
