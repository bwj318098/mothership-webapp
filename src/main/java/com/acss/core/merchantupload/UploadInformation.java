package com.acss.core.merchantupload;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import com.acss.core.merchantupload.validator.NotInValidImageFile;

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
	
	@NotBlank(message = UploadInformation.NOT_BLANK_MESSAGE)
	private String appNo;
	
	@NotBlank(message = UploadInformation.NOT_BLANK_MESSAGE)
	private String seqNo;
	

	@NotInValidImageFile
	private MultipartFile appForm;
	
	@NotInValidImageFile
	private MultipartFile idProof;
	
	@NotInValidImageFile
	private MultipartFile addressProof;
	
	@NotInValidImageFile
	private MultipartFile incomeProof;
	
	@Valid
	@NotNull
	@Size(min = 0)
	private List<HpsUploadFile> additionalImages;
	
	public UploadInformation(){additionalImages=new ArrayList<HpsUploadFile>();}
	
	public List<HpsUploadFile> getUploadFiles(){
		List<HpsUploadFile> files = new ArrayList<>();
		if(!this.appForm.isEmpty())files.add(new HpsUploadFile("0", this.appForm));
		if(!this.idProof.isEmpty())files.add(new HpsUploadFile("1", this.idProof));
		if(!this.addressProof.isEmpty())files.add(new HpsUploadFile("2", this.addressProof));
		if(!this.incomeProof.isEmpty())files.add(new HpsUploadFile("3", this.incomeProof));
		files.addAll(this.additionalImages);
		return files;
	}
	
	public void addMoreImages(HpsUploadFile image){
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

	public List<HpsUploadFile> getAdditionalImages() {
		return additionalImages;
	}

	public void setAdditionalImages(List<HpsUploadFile> additionalImages) {
		this.additionalImages = additionalImages;
	}
	
	
}
