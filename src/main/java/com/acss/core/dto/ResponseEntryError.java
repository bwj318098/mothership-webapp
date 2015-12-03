package com.acss.core.dto;

/**
 * Holds errors associated to a property
 * 
 * @author fsolijon
 *
 */
public class ResponseEntryError {

	/**
	 * UI field mapped in the model.
	 */
	private String property;

	/**
	 * Error message associated to the property.
	 */
	private String error;

	public ResponseEntryError(String property, String error) {
		this.property = property;
		this.error = error;
	}

	public ResponseEntryError(String error) {
		this.error = error;
	}

	/**
	 * @return the property
	 */
	public String getProperty() {
		return property;
	}

	public String getError() {
		return error;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((error == null) ? 0 : error.hashCode());
		result = prime * result + ((property == null) ? 0 : property.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseEntryError other = (ResponseEntryError) obj;
		if (error == null) {
			if (other.error != null)
				return false;
		} else if (!error.equals(other.error))
			return false;
		if (property == null) {
			if (other.property != null)
				return false;
		} else if (!property.equals(other.property))
			return false;
		return true;
	}
}
