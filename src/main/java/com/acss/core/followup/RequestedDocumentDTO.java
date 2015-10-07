package com.acss.core.followup;

import java.math.BigDecimal;

import com.acss.core.merchantupload.HpsImageType;

public class RequestedDocumentDTO {
	private BigDecimal seqId;
	private BigDecimal imageType;
	private String requestedBy;
	private BigDecimal requestedDate;
	private BigDecimal uploadStatus;
	private String remarks;
	
	public BigDecimal getSeqNo() {
		return seqId;
	}
	
	public String getUploadStatus() {
		//reason behind this invocation. this to bootstrap all the content of enum into its static map.
		FuUploadStatus.values();
		return FuUploadStatus.BootstrapSingleton.lookupByCode.get(uploadStatus).getValue();
	}
	public void setUploadStatus(BigDecimal uploadStatus) {
		this.uploadStatus = uploadStatus;
	}
	
	public void setSeqNo(BigDecimal seqNo) {
		this.seqId = seqNo;
	}
	public String getImageType() {
		return HpsImageType.BootstrapSingleton.lookupByCode.get(imageType).getValue();
	}
	public void setImageType(BigDecimal imageType) {
		this.imageType = imageType;
	}
	public String getRequestedBy() {
		return requestedBy;
	}
	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}
	public BigDecimal getRequestedDate() {
		return requestedDate;
	}
	public void setRequestedDate(BigDecimal requestedDate) {
		this.requestedDate = requestedDate;
	}
	
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
}
