package com.acss.core.search;

/**
 * Inner class for existing application images which will be picked up from database.
 * Also contains the url for image streaming purposes.
 * @author gvargas
 *
 */
public class ExistingImageDTO {
	private String imageType;
	private String imageFilename;
	//For Streaming purposes.
	private String imageCode;
	public ExistingImageDTO(){};
	
	public String getImageType() {
		return imageType;
	}
	
	/**
	 * Resolve the status from database into string
	 * @param imageType
	 */
	public void setImageType(String imageType) {
		switch (imageType) {
		case "0":
			this.imageType = "Application Form";
			break;
		case "1":
			this.imageType = "ID Proof";
			break;
		case "2":
			this.imageType = "Proof of Billing";
			break;
		case "3":
			this.imageType = "Proof of Income";
			break;
		default:
			this.imageType = imageType;
			break;
		}
	}
	public String getImageFilename() {
		return imageFilename;
	}
	public void setImageFilename(String imageFilename) {
		this.imageFilename = imageFilename;
	}
	public String getImageCode() {
		return imageCode;
	}
	public void setImageCode(String imageUrl) {
		this.imageCode = imageUrl;
	}
}