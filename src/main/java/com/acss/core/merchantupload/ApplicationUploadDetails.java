package com.acss.core.merchantupload;

import java.util.List;

public class ApplicationUploadDetails {
	private String appNo;
	private String customerName;
	private List<ApplicationImage> images;
	
	public String getCustomerName() {
		return customerName;
	}

	public void setCustomerName(String customerName) {
		this.customerName = customerName;
	}

	public String getAppNo() {
		return appNo;
	}

	public void setAppNo(String appNo) {
		this.appNo = appNo;
	}

	public List<ApplicationImage> getImages() {
		return images;
	}

	public void setImages(List<ApplicationImage> images) {
		this.images = images;
	}
	
	
}
