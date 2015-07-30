package com.acss.core.merchantupload;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.web.multipart.MultipartFile;

import com.acss.core.merchantupload.validator.NotInValidImageFile;

public class HpsUploadFile {
	
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	public HpsUploadFile() {}
	
	public HpsUploadFile(String imageType, MultipartFile imageFile) {
		this.imageType = imageType;
		this.imageFile = imageFile;
	}

	@NotBlank(message = HpsUploadFile.NOT_BLANK_MESSAGE)
	private String imageType;
	
	@NotInValidImageFile()
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
