package com.acss.core.merchantupload;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import com.acss.core.merchantupload.validator.NotInValidImageFile;
import com.acss.core.merchantupload.validator.NotInValidSeqNo;
import com.acss.core.merchantupload.validator.NotInvalidAppNo;
import com.acss.core.merchantupload.validator.PendingUploadInformationData;
import com.acss.core.merchantupload.validator.UploadInformationData;

/**
 * Contains the upload files information, the application number and the sequence number.
 * DTO for merchant upload module.
 * @author gvargas
 */
public class UploadInformationDTO {
	
	private static final String NOT_BLANK_MESSAGE = "{notBlankPending.message}";
	
	@NotInvalidAppNo(groups = {UploadInformationData.class,PendingUploadInformationData.class})
	private String appNo;
	
	@NotInValidSeqNo(groups = {UploadInformationData.class,PendingUploadInformationData.class})
	private String seqNo;
	
	@NotInValidImageFile(groups = {UploadInformationData.class,PendingUploadInformationData.class})
	private MultipartFile appForm;
	
	@NotInValidImageFile(groups = UploadInformationData.class)
	private MultipartFile idProof;
	
	@NotInValidImageFile(groups = UploadInformationData.class)
	private MultipartFile addressProof;
	
	@NotInValidImageFile(groups = UploadInformationData.class)
	private MultipartFile incomeProof;
	
	@NotBlank(message = UploadInformationDTO.NOT_BLANK_MESSAGE,groups = {PendingUploadInformationData.class})
	private String pendingRemarks;
	
	@Valid
	@NotNull(groups = UploadInformationData.class)
	@Size(min = 0,groups = {UploadInformationData.class})
	private List<HpsUploadFileDTO> additionalImages;
	
	public UploadInformationDTO(){additionalImages=new ArrayList<HpsUploadFileDTO>();}
	//initial is false.
	private boolean isForPendingSubmission = false;
	
	public boolean isForPendingSubmission() {
		return isForPendingSubmission;
	}

	public void setForPendingSubmission(boolean isForPendingSubmission) {
		this.isForPendingSubmission = isForPendingSubmission;
	}
	
	public List<HpsUploadFileDTO> getUploadFiles(){
		List<HpsUploadFileDTO> files = new ArrayList<>();
		if(this.appForm!=null)files.add(new HpsUploadFileDTO("0", this.appForm));
		if(this.idProof!=null)files.add(new HpsUploadFileDTO("1", this.idProof));
		if(this.addressProof!=null)files.add(new HpsUploadFileDTO("2", this.addressProof));
		if(this.incomeProof!=null)files.add(new HpsUploadFileDTO("3", this.incomeProof));
		files.addAll(this.additionalImages);
		return files;
	}
	
	public void addMoreImages(HpsUploadFileDTO image){
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

	public List<HpsUploadFileDTO> getAdditionalImages() {
		return additionalImages;
	}

	public void setAdditionalImages(List<HpsUploadFileDTO> additionalImages) {
		this.additionalImages = additionalImages;
	}

	public String getPendingRemarks() {
		return pendingRemarks;
	}

	public void setPendingRemarks(String pendingRemarks) {
		this.pendingRemarks = pendingRemarks;
	}
	
}
