/**
 * 
 */
package com.acss.core.model.builder;

import com.acss.core.model.dataentry.common.PhoneField;

/**
 * @author fsolijon
 *
 */
public class PhoneFieldBuilder {

	PhoneField phoneField = new PhoneField();

	/**
	 * @param region
	 * @see com.acss.core.model.dataentry.common.PhoneField#setRegion(java.lang.String)
	 */
	public PhoneFieldBuilder setRegion(String region) {
		phoneField.setRegion(region);
		return this;
	}

	/**
	 * @param phoneNo
	 * @see com.acss.core.model.dataentry.common.PhoneField#setPhoneNo(java.lang.String)
	 */
	public PhoneFieldBuilder setPhoneNo(String phoneNo) {
		phoneField.setPhoneNo(phoneNo);
		return this;
	}

	/**
	 * @param local
	 * @see com.acss.core.model.dataentry.common.PhoneField#setLocal(java.lang.String)
	 */
	public PhoneFieldBuilder setLocal(String local) {
		phoneField.setLocal(local);
		return this;
	}
	
	public static PhoneFieldBuilder create(){
		return new PhoneFieldBuilder();
	}
	
	public PhoneField get(){
		return this.phoneField;
	}
}
