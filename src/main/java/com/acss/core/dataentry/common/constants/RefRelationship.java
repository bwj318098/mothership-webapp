package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum RefRelationship {
	
	MOTHER(1,"refrelationship.mother"),
	FATHER(2,"refrelationship.father"),
	BROTHER(3,"refrelationship.brother"),
	SISTER(4,"refrelationship.sister"),
	AUNTIE(6,"refrelationship.auntie"),
	UNCLE(7,"refrelationship.uncle"),
	COUSIN(8,"refrelationship.cousin"),
	REFERRAL(9,"refrelationship.referral"),
	OTHERREF(5,"refrelationship.other");
	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "refRelationshipList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, RefRelationship> lookupByValue = new HashMap<String, RefRelationship>();
		public static final Map<BigDecimal, RefRelationship> lookupByCode = new HashMap<BigDecimal, RefRelationship>();
	}
	
	RefRelationship(int code, String value) {
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
