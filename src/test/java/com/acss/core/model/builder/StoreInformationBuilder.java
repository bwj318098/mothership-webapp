/**
 * 
 */
package com.acss.core.model.builder;

import org.joda.time.DateTime;

import com.acss.core.model.dataentry.common.PhoneField;
import com.acss.core.model.dataentry.common.StoreInformation;
import com.acss.core.model.dataentry.common.constants.PromoterScreening;

/**
 * @author fsolijon
 *
 */
public class StoreInformationBuilder {

	StoreInformation storeInformation = new StoreInformation();

	/**
	 * @param storeCd
	 * @see com.acss.core.model.dataentry.common.StoreInformation#setStoreCd(java.lang.String)
	 */
	public StoreInformationBuilder setStoreCd(String storeCd) {
		storeInformation.setStoreCd(storeCd);
		return this;
	}

	/**
	 * @param storeName
	 * @see com.acss.core.model.dataentry.common.StoreInformation#setStoreName(java.lang.String)
	 */
	public StoreInformationBuilder setStoreName(String storeName) {
		storeInformation.setStoreName(storeName);
		return this;
	}

	/**
	 * @param storePhone
	 * @see com.acss.core.model.dataentry.common.StoreInformation#setStorePhone(com.acss.core.model.dataentry.common.PhoneField)
	 */
	public StoreInformationBuilder setStorePhone(PhoneField storePhone) {
		storeInformation.setStorePhone(storePhone);
		return this;
	}

	/**
	 * @param receivedDate
	 * @see com.acss.core.model.dataentry.common.StoreInformation#setReceivedDate(org.joda.time.DateTime)
	 */
	public StoreInformationBuilder setReceivedDate(DateTime receivedDate) {
		storeInformation.setReceivedDate(receivedDate);
		return this;
	}

	/**
	 * @param merchantStaffName
	 * @see com.acss.core.model.dataentry.common.StoreInformation#setMerchantStaffName(java.lang.String)
	 */
	public StoreInformationBuilder setMerchantStaffName(String merchantStaffName) {
		storeInformation.setMerchantStaffName(merchantStaffName);
		return this;
	}

	/**
	 * @param aeonStaff
	 * @see com.acss.core.model.dataentry.common.StoreInformation#setAeonStaff(java.lang.String)
	 */
	public StoreInformationBuilder setAeonStaff(String aeonStaff) {
		storeInformation.setAeonStaff(aeonStaff);
		return this;
	}

	/**
	 * @param promoScreening
	 * @see com.acss.core.model.dataentry.common.StoreInformation#setPromoScreening(com.acss.core.model.dataentry.common.constants.PromoterScreening)
	 */
	public StoreInformationBuilder setPromoScreening(PromoterScreening promoScreening) {
		storeInformation.setPromoScreening(promoScreening);
		return this;
	}
	
	
	public static StoreInformationBuilder create(){
		return new StoreInformationBuilder();
	}
	
	public StoreInformation get(){
		return this.storeInformation;
	}
}
