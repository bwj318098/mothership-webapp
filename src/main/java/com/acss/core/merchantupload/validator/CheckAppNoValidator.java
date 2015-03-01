package com.acss.core.merchantupload.validator;

import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class CheckAppNoValidator implements ConstraintValidator<NotInvalidAppNo, String>{
	private String message;
	@Override
	public void initialize(NotInvalidAppNo constraintAnnotation) {
		setMessage("{notBlank.message}");
	}

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		boolean isValid=true;
		if(value==null || value.isEmpty() ||value.equalsIgnoreCase("")){
			isValid = false;
		}else if(!Pattern.matches("([\\p{Digit}]{9})[\\-]([\\p{Digit}]{1})", value)){
			isValid = false;
			setMessage("{invalidAppno.message}");
		}else if(value.length()!=11){
			isValid = false;
			setMessage("{invalidAppNoLength.message}");
		}else if(!validateLastDigit(value)){
			isValid = false;
			setMessage("{invalidAppNoGen.message}");
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

	private boolean validateLastDigit(String value) {
		String lastDigit = value.substring(10);
		String removedLastDigit = value.substring(0,9);
		String validLastDigit = generateLastDigit(removedLastDigit);
		return lastDigit.equalsIgnoreCase(validLastDigit);
	}
	/**
	 * Generates the last digit for application number.
	 * this is directly taken from HPS implementation.
	 * This is duplicated method from Rest Service Class: Application RestController
	 * Please put this into jar and consolidate commonly used methods.
	 * 
	 * @param strAPPNo
	 * @return the last digit for application no.
	 */
	private String generateLastDigit(String strAPPNo) {
        //First 15 digit of agreement number string.
        char[] chrAPPNo = strAPPNo.toCharArray();

        //Multiplier array for each digit of AGNo array.
        int[] iErs = { 2, 1, 2, 1, 2, 1, 2, 1, 2 };

        //Temporary result during generate.
        StringBuffer sbTempResult = new StringBuffer();

        for (int i = 0; i < chrAPPNo.length; i++) {
            sbTempResult.append(Character.getNumericValue(chrAPPNo[i]) * iErs[i]);
        }

        char[] chrTempResult = sbTempResult.toString().toCharArray();
        int iTempResult = 0;

        for (int i = 0; i < chrTempResult.length; i++) {
            iTempResult += Character.getNumericValue(chrTempResult[i]);
        }

        //Integer value of check character.
        int iAPPNo = 0;

        if (iTempResult < 10) {
            iAPPNo = 10 - iTempResult;
        } else {
            iAPPNo = 10 - iTempResult % 10;
        }

        if (iAPPNo == 10) {
            iAPPNo = 0;
        }

        return String.valueOf(iAPPNo);
    }
}
