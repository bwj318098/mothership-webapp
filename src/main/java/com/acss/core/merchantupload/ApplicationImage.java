package com.acss.core.merchantupload;

public class ApplicationImage {
	
	public ApplicationImage(String fileName,String imageType){
		this.fileName = fileName;
		this.imageType = imageType;
	}
	
	private String fileName;
	private String imageType;
	
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getImageType() {
		return imageType;
	}
	public void setImageType(String imageType) {
		this.imageType = imageType;
	}
	
	
}
