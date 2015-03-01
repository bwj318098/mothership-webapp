package com.acss.core.forgotpwd;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

public class ForgotPasswordForm {
	private static final String EMAIL_MESSAGE = "{email.message}";
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";

	private String userName;
    @NotBlank(message = ForgotPasswordForm.NOT_BLANK_MESSAGE)
	@Email(message = ForgotPasswordForm.EMAIL_MESSAGE)
	private String email;
	
	private String token;
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
}