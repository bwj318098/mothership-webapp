package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum Gender {
	
	MALE(1,"gender.male"),
	FEMALE(2,"gender.female");
	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "genderList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, Gender> lookupByValue = new HashMap<String, Gender>();
		public static final Map<BigDecimal, Gender> lookupByCode = new HashMap<BigDecimal, Gender>();
	}
	
	Gender(int code, String value) {
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
