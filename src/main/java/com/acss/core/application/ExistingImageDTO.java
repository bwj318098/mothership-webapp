package com.acss.core.application;

import java.math.BigDecimal;

import com.acss.core.merchantupload.HpsImageType;

/**
 * Inner class for existing application images which will be picked up from database.
 * Also contains the url for image streaming purposes.
 * @author gvargas
 *
 */
public class ExistingImageDTO {
	private String imageType;
	private String imageTypeDesc;
	private String imageFilename;
	private String imageCode;
	//special handling form application form since we don't include this as image type for upload.
	private final static String APPLICATION_FORM_MSG_KEY="imgtype.appForm";
	
	public ExistingImageDTO(){};
	
	public String getImageType() {
		return imageType;
	}
	
	public String getImageTypeDesc() {
		return imageTypeDesc;
	}

	public void setImageTypeDesc(String imageTypeDesc) {
		this.imageTypeDesc = imageTypeDesc;				
	}
	/**
	 * Resolve the status from database into string and set it as imageType desc.
	 * @param imageType
	 */
	public void setImageType(String imageType) {
		String sImageType = "";
		if(imageType.equals("0")){
			sImageType = APPLICATION_FORM_MSG_KEY;
		}else
			sImageType = HpsImageType.BootstrapSingleton.lookupByCode.get(
					new BigDecimal(imageType)) != null ? HpsImageType.BootstrapSingleton.lookupByCode
					.get(new BigDecimal(imageType)).getValue() : imageType;
		setImageTypeDesc(sImageType);
		this.imageType = imageType;
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