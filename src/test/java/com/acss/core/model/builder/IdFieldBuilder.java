/**
 * 
 */
package com.acss.core.model.builder;

import java.util.ArrayList;
import java.util.List;

import com.acss.core.model.dataentry.common.IdField;
import com.acss.core.model.dataentry.common.constants.TypeOfId;

/**
 * @author fsolijon
 *
 */
public class IdFieldBuilder {

	IdField idField;
	
	List<IdField> idFields;

	int currentIndex;
	
	public IdFieldBuilder() {
		this.idFields = new ArrayList<IdField>();
		this.idFields.add(new IdField());
		this.currentIndex = 0;
		this.idField = this.idFields.get(currentIndex);
	}

	/**
	 * @param idType
	 * @see com.acss.core.model.dataentry.common.IdField#setIdType(com.acss.core.model.dataentry.common.constants.TypeOfId)
	 */
	public IdFieldBuilder setIdType(TypeOfId idType) {
		idField.setIdType(idType);
		return this;
	}

	/**
	 * @param idCardNo
	 * @see com.acss.core.model.dataentry.common.IdField#setIdCardNo(java.lang.String)
	 */
	public IdFieldBuilder setIdCardNo(String idCardNo) {
		idField.setIdCardNo(idCardNo);
		return this;
	}

	/**
	 * @param primaryStatus
	 * @see com.acss.core.model.dataentry.common.IdField#setPrimaryStatus(boolean)
	 */
	public IdFieldBuilder setPrimaryStatus(boolean primaryStatus) {
		idField.setPrimaryStatus(primaryStatus);
		return this;
	}

	/**
	 * @param idNo
	 * @see com.acss.core.model.dataentry.common.IdField#setIdNo(int)
	 */
	public IdFieldBuilder setIdNo(int idNo) {
		idField.setIdNo(idNo);
		return this;
	}
	
	public IdFieldBuilder next(){
		this.idFields.add(new IdField());
		this.currentIndex++;
		this.idField = this.idFields.get(this.currentIndex);
		return this;
	}
	
	public static IdFieldBuilder create(){
		return new IdFieldBuilder();
	}
	
	public IdField get(){
		return this.idField;
	}
	
	public List<IdField> list(){
		return this.idFields;
	}
	
}
