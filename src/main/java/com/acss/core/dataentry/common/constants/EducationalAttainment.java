package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum EducationalAttainment {
	
	HIGHSCHOOL(2,"education.highschool"),
	VOCATIONAL(4,"education.vocational"),
	COLLEGE(5,"education.college"),
	POSTCOLLEGE(3,"education.postcollege");
	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "educationAttainmentList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, EducationalAttainment> lookupByValue = new HashMap<String, EducationalAttainment>();
		public static final Map<BigDecimal, EducationalAttainment> lookupByCode = new HashMap<BigDecimal, EducationalAttainment>();
	}
	
	EducationalAttainment(int code, String value) {
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
