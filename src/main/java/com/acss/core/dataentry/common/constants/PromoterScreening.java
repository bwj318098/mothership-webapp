package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum PromoterScreening {
	RATING_A(1,"rating.a"),
	RATING_B(2,"rating.b"),
	RATING_C(3,"rating.c");
	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "promoterScreeningList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, PromoterScreening> lookupByValue = new HashMap<String, PromoterScreening>();
		public static final Map<BigDecimal, PromoterScreening> lookupByCode = new HashMap<BigDecimal, PromoterScreening>();
	}
	
	PromoterScreening(int code, String value) {
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
