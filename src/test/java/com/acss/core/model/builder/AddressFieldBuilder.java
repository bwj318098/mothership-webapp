package com.acss.core.model.builder;

import com.acss.core.model.dataentry.common.AddressField;

/**
 * 
 * @author fsolijon
 *
 */
public class AddressFieldBuilder {

	AddressField addressField = new AddressField();

	/**
	 * @param zipCode
	 * @see com.acss.core.model.dataentry.common.AddressField#setZipCode(java.lang.Integer)
	 */
	public AddressFieldBuilder setZipCode(Integer zipCode) {
		addressField.setZipCode(zipCode);
		return this;
	}

	/**
	 * @param unitNo
	 * @see com.acss.core.model.dataentry.common.AddressField#setUnitNo(java.lang.String)
	 */
	public AddressFieldBuilder setUnitNo(String unitNo) {
		addressField.setUnitNo(unitNo);
		return this;
	}

	/**
	 * @param street
	 * @see com.acss.core.model.dataentry.common.AddressField#setStreet(java.lang.String)
	 */
	public AddressFieldBuilder setStreet(String street) {
		addressField.setStreet(street);
		return this;
	}

	/**
	 * @param baranggay
	 * @see com.acss.core.model.dataentry.common.AddressField#setBaranggay(java.lang.String)
	 */
	public AddressFieldBuilder setBaranggay(String baranggay) {
		addressField.setBaranggay(baranggay);
		return this;
	}

	/**
	 * @param city
	 * @see com.acss.core.model.dataentry.common.AddressField#setCity(java.lang.String)
	 */
	public AddressFieldBuilder setCity(String city) {
		addressField.setCity(city);
		return this;
	}

	/**
	 * @param landMark
	 * @see com.acss.core.model.dataentry.common.AddressField#setLandMark(java.lang.String)
	 */
	public AddressFieldBuilder setLandMark(String landMark) {
		addressField.setLandMark(landMark);
		return this;
	};
	
	public static AddressFieldBuilder create(){
		return new AddressFieldBuilder();
	}
	
	public AddressField get(){
		return this.addressField;
	}
	
}
