package com.acss.core.dataentry.common;

import org.joda.time.DateTime;
import org.springframework.format.annotation.DateTimeFormat;

import com.acss.core.dataentry.common.constants.PromoterScreening;

public class StoreInformation {
	
	private String storeCd;
	private String storeName;
	private PhoneField storePhone;
	private String merchantStaffName;
	private String aeonStaff;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private DateTime receivedDate;
	
	private PromoterScreening promoScreening;
	
	public String getStoreCd() {
		return storeCd;
	}

	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}

	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public PhoneField getStorePhone() {
		return storePhone;
	}

	public void setStorePhone(PhoneField storePhone) {
		this.storePhone = storePhone;
	}

	public DateTime getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(DateTime receivedDate) {
		this.receivedDate = receivedDate;
	}

	public String getMerchantStaffName() {
		return merchantStaffName;
	}

	public void setMerchantStaffName(String merchantStaffName) {
		this.merchantStaffName = merchantStaffName;
	}

	public String getAeonStaff() {
		return aeonStaff;
	}

	public void setAeonStaff(String aeonStaff) {
		this.aeonStaff = aeonStaff;
	}

	public PromoterScreening getPromoScreening() {
		return promoScreening;
	}

	public void setPromoScreening(PromoterScreening promoScreening) {
		this.promoScreening = promoScreening;
	}
	
	
}
