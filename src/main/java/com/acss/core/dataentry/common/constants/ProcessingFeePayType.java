package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum ProcessingFeePayType {
	
	NORMAL(1,"procfeetype.normal"),
	ADDTOFP(2,"procfeetype.addtofp"),
	ADDTOINSTALLMENT(3,"procfeetype.addtoinstallment");
	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "processingFeePayTypeList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, ProcessingFeePayType> lookupByValue = new HashMap<String, ProcessingFeePayType>();
		public static final Map<BigDecimal, ProcessingFeePayType> lookupByCode = new HashMap<BigDecimal, ProcessingFeePayType>();
	}
	
	ProcessingFeePayType(int code, String value) {
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
