package com.acss.core.merchantupload;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import com.acss.core.merchantupload.validator.NotEmptyUpload;

/**
 * Contains the upload files information, the application number and the sequence number.
 * @author gvargas
 */
public class UploadInformation {
	
	/**
	 * This will be resolved at client side level via thymeleaf.
	 * example: {notBlank.message} will resolve into #{notBlank.message} message.properties
	 */
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private static final String FILE_NOT_BLANK_MESSAGE = "{fileNotBlank.message}";
	
	@NotBlank(message = UploadInformation.NOT_BLANK_MESSAGE)
	private String appNo;
	
	@NotBlank(message = UploadInformation.NOT_BLANK_MESSAGE)
	private String seqNo;
	
	@NotEmptyUpload(message = UploadInformation.FILE_NOT_BLANK_MESSAGE)
	private MultipartFile appForm;
	@NotEmptyUpload(message = UploadInformation.FILE_NOT_BLANK_MESSAGE)
	private MultipartFile idProof;
	@NotEmptyUpload(message = UploadInformation.FILE_NOT_BLANK_MESSAGE)
	private MultipartFile addressProof;
	@NotEmptyUpload(message = UploadInformation.FILE_NOT_BLANK_MESSAGE)
	private MultipartFile incomeProof;
	
	@Valid
	@NotNull
	@Size(min = 1)
	private List<AdditionalImage> additionalImages;
	
	public UploadInformation(){additionalImages=new ArrayList<AdditionalImage>();}
	
	public List<MultipartFile> getUploadFiles(){
		List<MultipartFile> files = new ArrayList<>();
		if(!this.appForm.isEmpty())files.add(this.appForm);
		if(!this.idProof.isEmpty())files.add(this.idProof);
		if(!this.addressProof.isEmpty())files.add(this.addressProof);
		if(!this.incomeProof.isEmpty())files.add(this.incomeProof);
		//add more images
		for(AdditionalImage additionalImage:this.additionalImages){
			MultipartFile moreImage = additionalImage.getImageFile();
			files.add(moreImage);
		}
		return files;
	}
	
	public void addMoreImages(AdditionalImage image){
		this.additionalImages.add(image);
	}
	
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public MultipartFile getAppForm() {
		return appForm;
	}
	public void setAppForm(MultipartFile appForm) {
		this.appForm = appForm;
	}
	public MultipartFile getIdProof() {
		return idProof;
	}
	public void setIdProof(MultipartFile idProof) {
		this.idProof = idProof;
	}
	public MultipartFile getAddressProof() {
		return addressProof;
	}
	public void setAddressProof(MultipartFile addressProof) {
		this.addressProof = addressProof;
	}
	public MultipartFile getIncomeProof() {
		return incomeProof;
	}
	public void setIncomeProof(MultipartFile incomeProof) {
		this.incomeProof = incomeProof;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public List<AdditionalImage> getAdditionalImages() {
		return additionalImages;
	}

	public void setAdditionalImages(List<AdditionalImage> additionalImages) {
		this.additionalImages = additionalImages;
	}
	
	
}
