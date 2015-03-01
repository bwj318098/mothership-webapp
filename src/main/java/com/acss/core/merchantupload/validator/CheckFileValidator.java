package com.acss.core.merchantupload.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.web.multipart.MultipartFile;

public class CheckFileValidator implements ConstraintValidator<NotEmptyUpload,MultipartFile>{

	@Override
	public void initialize(NotEmptyUpload constraintAnnotation) {}

	@Override
	public boolean isValid(MultipartFile file, ConstraintValidatorContext constraintContext) {	
		return !file.isEmpty();
	}

}
