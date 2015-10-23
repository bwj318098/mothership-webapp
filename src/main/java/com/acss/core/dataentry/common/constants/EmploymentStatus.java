package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum EmploymentStatus {
	
	CASUAL(1,"employmentstatus.casual"),
	COMMISSION(2,"employmentstatus.commission"),
	CONTRACTUAL(3,"employmentstatus.contractual"),
	CO_TERMINOUS(4,"employmentstatus.coterminous"),
	PROBATIONARY(5,"employmentstatus.probationary"),
	REGULAR(6,"employmentstatus.regular"),
	REMITTANCE_PENSION(7,"employmentstatus.remittance"),
	SELF_EMPLOYED(8,"employmentstatus.selfemployed"),
	UNEMPLOYED(9,"employmentstatus.unemployed"),
	UNIDENTIFIED(10,"employmentstatus.unidentified");

	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "employmentStatusList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, EmploymentStatus> lookupByValue = new HashMap<String, EmploymentStatus>();
		public static final Map<BigDecimal, EmploymentStatus> lookupByCode = new HashMap<BigDecimal, EmploymentStatus>();
	}
	
	EmploymentStatus(int code, String value) {
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
