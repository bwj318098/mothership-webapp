package com.acss.core.merchantupload.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckSeqNoValidator implements ConstraintValidator<NotInValidSeqNo, String>{
	private String message;
	@Override
	public void initialize(NotInValidSeqNo constraintAnnotation) {
		setMessage("{notBlank.message}");
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean isValid=true;
		if(value==null || value.isEmpty() ||value.equalsIgnoreCase("")){
			isValid = false;
		}else if(!(Pattern.matches("[\\s]*[0-9]*[0-9]+", value))){
			isValid = false;
			setMessage("{invalidSeqno.message}");
		}else if(value.length()!=7){
			isValid = false;
			setMessage("{invalidSeqnoLength.message}");
		}else
			isValid = true;
		
		context.disableDefaultConstraintViolation();
		context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
		return isValid;
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
}
