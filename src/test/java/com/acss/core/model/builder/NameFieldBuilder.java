package com.acss.core.model.builder;

import com.acss.core.model.dataentry.common.NameField;

public class NameFieldBuilder {

	NameField nameField = new NameField();

	/**
	 * @param firstName
	 * @see com.acss.core.model.dataentry.common.NameField#setFirstName(java.lang.String)
	 */
	public NameFieldBuilder setFirstName(String firstName) {
		nameField.setFirstName(firstName);
		return this;
	}

	/**
	 * @param middleName
	 * @see com.acss.core.model.dataentry.common.NameField#setMiddleName(java.lang.String)
	 */
	public NameFieldBuilder setMiddleName(String middleName) {
		nameField.setMiddleName(middleName);
		return this;
	}

	/**
	 * @param surName
	 * @see com.acss.core.model.dataentry.common.NameField#setSurName(java.lang.String)
	 */
	public NameFieldBuilder setSurName(String surName) {
		nameField.setSurName(surName);
		return this;
	}
	
	public static NameFieldBuilder create(){
		return new NameFieldBuilder();
	}
	
	public NameField get(){
		return this.nameField;
	}
	
}
