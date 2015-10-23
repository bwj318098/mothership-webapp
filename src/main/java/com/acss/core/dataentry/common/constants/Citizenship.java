package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum Citizenship {
	
	FILIPINO(1,"citizen.filipino"),
	OTHERS(2,"citizen.others");
	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "citizenshipList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, Citizenship> lookupByValue = new HashMap<String, Citizenship>();
		public static final Map<BigDecimal, Citizenship> lookupByCode = new HashMap<BigDecimal, Citizenship>();
	}
	
	Citizenship(int code, String value) {
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
