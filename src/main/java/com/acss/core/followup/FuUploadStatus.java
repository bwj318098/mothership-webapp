package com.acss.core.followup;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public enum FuUploadStatus {
	UPLOADED(0, "fuuploadstatus.no"), NOTUPLOADED(1, "fuuploadstatus.yes");

	private String value;
	private int code;

	FuUploadStatus(int code, String value) {
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
	
	public Map<String, FuUploadStatus> lookUpByValue(){
		return BootstrapSingleton.lookupByValue;
	}
	
	public final static class BootstrapSingleton {
		public static final Map<String, FuUploadStatus> lookupByValue = new HashMap<String, FuUploadStatus>();
		public static final Map<BigDecimal, FuUploadStatus> lookupByCode = new HashMap<BigDecimal, FuUploadStatus>();
	}

}
