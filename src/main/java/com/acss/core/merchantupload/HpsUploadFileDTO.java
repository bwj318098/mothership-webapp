package com.acss.core.merchantupload;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import com.acss.core.merchantupload.validator.NotInValidImageFile;
import com.acss.core.merchantupload.validator.PendingUploadInformaionData;
import com.acss.core.merchantupload.validator.UploadInformationData;
/**
 * The upload file object used by the UploadInformationDTO
 * to describe the uploaded file in OSA.
 * DTO for the upload file.
 * @author gvargas
 *
 */
public class HpsUploadFileDTO {
	
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	public HpsUploadFileDTO() {}
	
	public HpsUploadFileDTO(String imageType, MultipartFile imageFile) {
		this.imageType = imageType;
		this.imageFile = imageFile;
	}
	
	@NotBlank(message = HpsUploadFileDTO.NOT_BLANK_MESSAGE,groups = {UploadInformationData.class,PendingUploadInformaionData.class})
	private String imageType;
	
	@NotInValidImageFile(groups = {UploadInformationData.class,PendingUploadInformaionData.class})
	private MultipartFile imageFile;
	
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	public MultipartFile getImageFile() {
		return imageFile;
	}
	public void setImageFile(MultipartFile imageFile) {
		this.imageFile = imageFile;
	}
	
	
}
