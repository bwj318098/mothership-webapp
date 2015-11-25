/**
 * 
 */
package com.acss.core.model.builder;

import com.acss.core.model.dataentry.common.AddressField;
import com.acss.core.model.dataentry.common.NameField;
import com.acss.core.model.dataentry.common.PhoneField;
import com.acss.core.model.dataentry.common.ReferenceData;
import com.acss.core.model.dataentry.common.constants.RefRelationship;

/**
 * @author fsolijon
 *
 */
public class ReferenceDataBuilder {

	ReferenceData referenceData = new ReferenceData();

	/**
	 * @param refName
	 * @see com.acss.core.model.dataentry.common.ReferenceData#setRefName(com.acss.core.model.dataentry.common.NameField)
	 */
	public ReferenceDataBuilder setRefName(NameField refName) {
		referenceData.setRefName(refName);
		return this;
	}

	/**
	 * @param refAddress
	 * @see com.acss.core.model.dataentry.common.ReferenceData#setRefAddress(com.acss.core.model.dataentry.common.AddressField)
	 */
	public ReferenceDataBuilder setRefAddress(AddressField refAddress) {
		referenceData.setRefAddress(refAddress);
		return this;
	}

	/**
	 * @param relationship
	 * @see com.acss.core.model.dataentry.common.ReferenceData#setRelationship(com.acss.core.model.dataentry.common.constants.RefRelationship)
	 */
	public ReferenceDataBuilder setRelationship(RefRelationship relationship) {
		referenceData.setRelationship(relationship);
		return this;
	}

	/**
	 * @param refPhone
	 * @see com.acss.core.model.dataentry.common.ReferenceData#setRefPhone(com.acss.core.model.dataentry.common.PhoneField)
	 */
	public ReferenceDataBuilder setRefPhone(PhoneField refPhone) {
		referenceData.setRefPhone(refPhone);
		return this;
	}

	/**
	 * @param refMobile
	 * @see com.acss.core.model.dataentry.common.ReferenceData#setRefMobile(com.acss.core.model.dataentry.common.PhoneField)
	 */
	public ReferenceDataBuilder setRefMobile(PhoneField refMobile) {
		referenceData.setRefMobile(refMobile);
		return this;
	}

	/**
	 * @param refCorpPhone
	 * @see com.acss.core.model.dataentry.common.ReferenceData#setRefCorpPhone(com.acss.core.model.dataentry.common.PhoneField)
	 */
	public ReferenceDataBuilder setRefCorpPhone(PhoneField refCorpPhone) {
		referenceData.setRefCorpPhone(refCorpPhone);
		return this;
	}

	/**
	 * @param companyName
	 * @see com.acss.core.model.dataentry.common.ReferenceData#setCompanyName(java.lang.String)
	 */
	public ReferenceDataBuilder setCompanyName(String companyName) {
		referenceData.setCompanyName(companyName);
		return this;
	}
	
	public static ReferenceDataBuilder create(){
		return new ReferenceDataBuilder();
	}
	
	public ReferenceData get(){
		return this.referenceData;
	}
	
}
