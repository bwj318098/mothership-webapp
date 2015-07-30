package com.acss.core.merchantupload.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class CheckFileExtensionValidator implements ConstraintValidator<NotInValidImageFile,MultipartFile>{
	private String message;
	@Override
	public void initialize(NotInValidImageFile constraintAnnotation) {
		setMessage("{fileNotBlank.message}");
	}

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
		if(file.isEmpty()){
			return false;
		}else if(!file.getContentType().startsWith("image/")){
			setMessage("{fileNotInvalidImageFile.message}");
			//disable existing violation message
		    context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
			return false;
		}else if(file.getSize()>=new Long(5000000)){
			setMessage("{fileNotExceed5mb.message}");
			//disable existing violation message
		    context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
			return false;
		}else
			return true;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

}
