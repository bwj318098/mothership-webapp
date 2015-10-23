package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum Term {
	
	TERM_6(1,"term.6"),
	TERM_12(2,"term.12"),
	TERM_18(3,"term.18"),
	TERM_24(4,"term.24");
	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "termList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, Term> lookupByValue = new HashMap<String, Term>();
		public static final Map<BigDecimal, Term> lookupByCode = new HashMap<BigDecimal, Term>();
	}
	
	Term(int code, String value) {
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
