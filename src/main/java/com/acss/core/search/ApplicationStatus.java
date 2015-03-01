package com.acss.core.search;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Application Status for search criteria.
 * @author gvargas
 *
 */
public enum ApplicationStatus {
	NONE(0,"appstatus.blank"),
	DOCUMENTS_SUBMITTED(1, "appstatus.docs"),
	DE1_FINISHED(2, "appstatus.definished"),
	BAP_CHECK(3, "appstatus.bap"),
	ACCEPTED(4, "appstatus.accepted"),
	SURVEY_REQUESTED(5, "appstatus.svyrequest"),
	FIRST_ASSESSMENT(6,"appstatus.frstassess"),
	FINAL_ASSESSMENT(7,"appstatus.fnalassess"),
	VERIFICATION_PENDING(8,"appstatus.verpending"),
	APPROVED(9,"appstatus.approved"),
	REJECTED(10,"appstatus.rejected"),
	CANCELLED(11,"appstatus.cancelled"),
	WITH_PENDING_DOCUMENTS(12,"appstatus.pending");
	
	private int code;
	private String value;
	
	private final static class BootstrapSingleton {
		public static final Map<String, ApplicationStatus> lookupByValue = new HashMap<String, ApplicationStatus>();
		public static final Map<BigDecimal, ApplicationStatus> lookupByCode = new HashMap<BigDecimal, ApplicationStatus>();
	}
	
	ApplicationStatus(int code, String value) {
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
