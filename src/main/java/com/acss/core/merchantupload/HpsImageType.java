package com.acss.core.merchantupload;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
/**
 * Image type for additional image type.
 * @author gvargas
 *
 */
public enum HpsImageType {
	
	//NONE(0,""),
	ID_PROOF(1,"imgtype.id"),
	PROOF_OF_BILLING(2,"imgtype.pobilling"),
	PROOF_OF_INCOME(3,"imgtype.poincome"),
	HOUSE_SKETCH(4,"imgtype.housesketch"),
	CUSTOMER_IMAGE(5,"imgtype.custimage"),
	SUPPORTING_DOCUMENTS(6,"imgtype.support");
	
	private int code;
	private String value;
	
	public final static class BootstrapSingleton {
		public static final Map<String, HpsImageType> lookupByValue = new HashMap<String, HpsImageType>();
		public static final Map<BigDecimal, HpsImageType> lookupByCode = new HashMap<BigDecimal, HpsImageType>();
	}
	
	HpsImageType(int code, String value) {
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
