package com.acss.core.dataentry.common.constants;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum DaysOfMonth {
	
	D1(1,"d.1"),
	D2(2,"d.2"),
	D3(3,"d.3"),
	D4(4,"d.4"),
	D5(5,"d.5"),
	D6(6,"d.6"),
	D7(7,"d.7"),
	D8(8,"d.8"),
	D9(9,"d.9"),
	D10(10,"d.0"),
	D11(11,"d.11"),
	D12(12,"d.12"),
	D13(13,"d.13"),
	D14(14,"d.14"),
	D15(15,"d.15"),
	D16(16,"d.16"),
	D17(17,"d.17"),
	D18(18,"d.18"),
	D19(19,"d.19"),
	D20(20,"d.20"),
	D21(21,"d.21"),
	D22(22,"d.22"),
	D23(23,"d.23"),
	D24(24,"d.24"),
	D25(25,"d.25"),
	D26(26,"d.26"),
	D27(27,"d.27"),
	D28(28,"d.28"),
	D29(29,"d.29"),
	D30(30,"d.30"),
	D31(31,"d.31");
	
	private int code;
	private String value;
	
	public final static String MODEL_ATTRIB_KEY = "daysOfMonthList";
	
	public final static class BootstrapSingleton {
		public static final Map<String, DaysOfMonth> lookupByValue = new HashMap<String, DaysOfMonth>();
		public static final Map<BigDecimal, DaysOfMonth> lookupByCode = new HashMap<BigDecimal, DaysOfMonth>();
	}
	
	DaysOfMonth(int code, String value) {
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
