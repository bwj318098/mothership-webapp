package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum BankAccountType {
	CURRENT(1,"bank.current"),
	SAVINGS(2,"bank.savings"),
	TIME_DEPOSIT(3,"bank.deposit");
	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "bankAccountTypeList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, BankAccountType> lookupByValue = new HashMap<String, BankAccountType>();
		public static final Map<BigDecimal, BankAccountType> lookupByCode = new HashMap<BigDecimal, BankAccountType>();
	}
	
	BankAccountType(int code, String value) {
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
