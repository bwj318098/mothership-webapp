package com.acss.core.search;


/**
 * DTO for application search criteria.
 * 
 * @author gvargas
 *
 */
public class ApplicationSearchCriteriaDTO {
	
	private String applicationNo;
	private String seqNo;
	private String customerName;
	private String appDateFrom;
	private String appDateTo;
	private String appStatus;
	
	public ApplicationSearchCriteriaDTO() {}
	
	/**
	 * Append all present parameters as query string in the GET request
	 * @param uri
	 * @return uri with parameters.
	 */
	public String appendParameters(String uri){
		uri=applicationNo!=null?uri+"applicationNo="+applicationNo+"&":uri;
		uri=customerName!=null?uri+"customerName="+customerName+"&":uri;
		uri=seqNo!=null?uri+"seqNo="+seqNo+"&":uri;
		uri=appDateFrom!=null?uri+"appDateFrom="+getAppDateFromAsYYYYMMDD()+"&":uri;
		uri=appDateTo!=null?uri+"appDateTo="+getAppDateToAsYYYYMMDD()+"&":uri;
		uri=appStatus!=null?uri+"appStatus="+appStatus+"&":uri;
		return uri;
	}
	
	public ApplicationSearchCriteriaDTO(String applicationNo, String seqNo,
			String customerName, String appDateFrom, String appDateTo,
			String appStatus) {
		this.applicationNo = applicationNo;
		this.seqNo = seqNo;
		this.customerName = customerName;
		this.appDateFrom = appDateFrom;
		this.appDateTo = appDateTo;
		this.appStatus = appStatus;
	}

	public String getApplicationNo() {
		return applicationNo;
	}
	public void setApplicationNo(String applicationNo) {
		this.applicationNo = applicationNo;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getAppDateFrom() {
		return appDateFrom;
	}
	public void setAppDateFrom(String appDateFrom) {
		this.appDateFrom = appDateFrom;
	}
	public String getAppDateTo() {
		return appDateTo;
	}
	public void setAppDateTo(String appDateTo) {
		this.appDateTo = appDateTo;
	}
	public String getAppStatus() {
		return appStatus;
	}
	public void setAppStatus(String appStatus) {
		this.appStatus = appStatus;
	}
	
	/**
	 * Gets Application Date from as YYYYMMDD
	 * @return date String as YYYYMMDD
	 */
	public String getAppDateToAsYYYYMMDD(){
		return appDateTo.replaceAll("[^A-Za-z0-9]", "");
	}
	
	/**
	 * Gets Application Date to as YYYYMMDD
	 * @return date String as YYYYMMDD
	 */
	public String getAppDateFromAsYYYYMMDD(){
		return appDateFrom.replaceAll("[^A-Za-z0-9]", "");
	}
	
}
