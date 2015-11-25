package com.acss.core.signup;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotBlank;

import com.acss.kaizen.jooq.poc.account.Account;
import com.acss.kaizen.jooq.poc.account.AccountBuilder;
import com.acss.kaizen.jooq.poc.account.constants.AccountStatus;
import com.acss.kaizen.jooq.poc.role.UserRole;

public class SignupForm {

	private static final String NOT_BLANK_MESSAGE = "{notBlank.message}";
	private static final String EMAIL_MESSAGE = "{email.message}";

    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	@Email(message = SignupForm.EMAIL_MESSAGE)
	private String email;

    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String password;
    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String username;
    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String storeCd;
    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String firstName;
    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String lastName;
    @NotBlank(message = SignupForm.NOT_BLANK_MESSAGE)
	private String address;
    
    public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getStoreCd() {
		return storeCd;
	}

	public void setStoreCd(String storeCd) {
		this.storeCd = storeCd;
	}

	/**
	 * Maps the an front-end POJO into an Account Entity.
	 * @return
	 */
	public Account createAccount() {
		
		AccountBuilder builder = new AccountBuilder();
		UserRole role = new UserRole();
		role.setUsername(getUsername());
		role.setRole("ROLE_USER");
		
		//builds the account entity using the front end POJO
		builder
			.withUsername(getUsername())
			.withPassword(getPassword())
			.withLastName(getLastName())
			.withFirstName(getFirstName())
			.withRole(role)
			.withStatus(AccountStatus.ACTIVE)
			.withEmail(getEmail())
			.withStoreCd(getStoreCd());
		
        return builder.build();
	}
}
