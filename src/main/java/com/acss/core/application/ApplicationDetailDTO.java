package com.acss.core.application;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.acss.core.merchantupload.HpsUploadFileDTO;
import com.acss.core.merchantupload.validator.NotInvalidAppNo;
import com.acss.core.merchantupload.validator.UploadInformationData;

public class ApplicationDetailDTO {
	
	private static final String NOT_EMPTY_UPLOAD = "{uploadNotEmpty.message}";
	
	@NotInvalidAppNo
	private String appNo;
	private String customerName;
	private String applicationDate;
	private String applicationStatus;
	private String seqNo;
	private String remarks;
	private String pendingRemarks;
	
	private List<ExistingImageDTO> existingImages;
	
	@Valid
	@NotNull(groups = UploadInformationData.class)
	@Size(min = 1,message = ApplicationDetailDTO.NOT_EMPTY_UPLOAD,groups = UploadInformationData.class)
	private List<HpsUploadFileDTO> additionalImages;
	
	public ApplicationDetailDTO() {
		existingImages = new ArrayList<ExistingImageDTO>();
		additionalImages=new ArrayList<HpsUploadFileDTO>();
	}
	
	public List<HpsUploadFileDTO> getUploadFiles(){
		return additionalImages;
	}
	
	public String getAppNo() {
		return appNo;
	}
	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}
	public String getCustomerName() {
		return customerName;
	}
	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}
	public String getApplicationDate() {
		return applicationDate;
	}
	public void setApplicationDate(String applicationDate) {
		this.applicationDate = applicationDate;
	}
	public String getApplicationStatus() {
		return applicationStatus;
	}
	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getRemarks() {
		return remarks;
	}
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	public List<ExistingImageDTO> getExistingImages() {
		return existingImages;
	}
	public void setExistingImages(List<ExistingImageDTO> existingImages) {
		this.existingImages = existingImages;
	}
	
	public ExistingImageDTO createNewApplicationImage(){
		return new ExistingImageDTO();
	}

	public List<HpsUploadFileDTO> getAdditionalImages() {
		return additionalImages;
	}

	public void setAdditionalImages(List<HpsUploadFileDTO> additionalImages) {
		this.additionalImages = additionalImages;
	}
	
	public void addMoreImages(HpsUploadFileDTO image){
		this.additionalImages.add(image);
	}
	
	public void addMoreImages(List<HpsUploadFileDTO> images){
		this.additionalImages.addAll(images);
	}

	public String getPendingRemarks() {
		return pendingRemarks;
	}

	public void setPendingRemarks(String pendingRemarks) {
		this.pendingRemarks = pendingRemarks;
	}
	
	
}
