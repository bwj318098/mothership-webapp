package com.acss.core.forgotpwd;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

import com.acss.core.merchantupload.validator.FieldMatch;

@FieldMatch.List({
    @FieldMatch(first = "password", second = "confirmPassword", message = "The password fields must match.")
})
public class UpdatePasswordForm {
	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	
	@NotBlank(message = UpdatePasswordForm.NOT_BLANK_MESSAGE)
	@Size(min=8, max=25)
	private String password;
	@NotBlank(message = UpdatePasswordForm.NOT_BLANK_MESSAGE)
	@Size(min=8, max=25)
	private String confirmPassword;

	public UpdatePasswordForm() {}
	public UpdatePasswordForm(String password, String confirmPassword) {
		super();
		this.password = password;
		this.confirmPassword = confirmPassword;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}
	
	
	
}
