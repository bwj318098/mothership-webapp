package com.acss.core.dto;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

public class RestResponseDTO<T> {

	private boolean success;

	private boolean showInModal;

	private Set<ResponseEntryError> errorSet = new HashSet<>();
	
	private T data;

	public T getData() {
		return this.data;
	}
	
	public RestResponseDTO<T> setData(T data) {
		this.data = data;
		return this;
	}

	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}
	
	/**
	 * @param success the success to set
	 */
	public RestResponseDTO<T> setSuccess(boolean success) {
		this.success = success;
		return this;
	}

	public RestResponseDTO<T> setFieldErrors(Collection<FieldError> fieldErrors) {
		for (FieldError error : fieldErrors) {
			this.errorSet.add(new ResponseEntryError(error.getField(), error.getDefaultMessage()));
		}
		return this;
	}

	public RestResponseDTO<T> addError(String property, String error) {
		if (error == null || error.trim().isEmpty()) {
			return this;
		}
		ObjectError errorObject = new ObjectError(property, error); // resolve error message for parameters and i18n.
		this.errorSet.add(new ResponseEntryError(errorObject.getObjectName(), errorObject.getDefaultMessage()));
		return this;
	}

	public boolean isShowInModal() {
		return showInModal;
	}

	/**
	 * @param showInModal the showInModal to set
	 */
	public RestResponseDTO<T> setShowInModal(boolean showInModal) {
		this.showInModal = showInModal;
		return this;
	}

	public Set<ResponseEntryError> getErrors() {
		return this.errorSet;
	}

}
