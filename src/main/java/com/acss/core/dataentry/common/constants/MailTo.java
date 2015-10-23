package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum MailTo {
	
	HOME(1,"mailto.home"),
	OFFICE(2,"mailto.office");
	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "mailToList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, MailTo> lookupByValue = new HashMap<String, MailTo>();
		public static final Map<BigDecimal, MailTo> lookupByCode = new HashMap<BigDecimal, MailTo>();
	}
	
	MailTo(int code, String value) {
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
