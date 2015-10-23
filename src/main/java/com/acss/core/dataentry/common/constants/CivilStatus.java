package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum CivilStatus {
	SINGLE(2,"civil.single"),
	MARRIED(1,"civil.married"),
	SINGLEPARENT(3,"civil.singleparent"),
	SEPERATED(4,"civil.separated"),
	WIDOWED(5,"civil.widowed");
	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "civilStatusList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, CivilStatus> lookupByValue = new HashMap<String, CivilStatus>();
		public static final Map<BigDecimal, CivilStatus> lookupByCode = new HashMap<BigDecimal, CivilStatus>();
	}
	
	CivilStatus(int code, String value) {
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
