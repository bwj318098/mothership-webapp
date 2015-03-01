package com.acss.core.merchantupload;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import com.acss.core.merchantupload.validator.NotEmptyUpload;

public class AdditionalImage {
	
	private static final String FILE_NOT_BLANK_MESSAGE = "{fileNotBlank.message}";
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	@NotBlank(message = AdditionalImage.NOT_BLANK_MESSAGE)
	private String imageType;
	
	@NotEmptyUpload(message = AdditionalImage.FILE_NOT_BLANK_MESSAGE)
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
