package com.acss.core.forgotpwd;

import org.joda.time.DateTime;

import com.acss.kaizen.jooq.poc.account.Account;

public class PasswordResetToken {
	private static final int EXPIRATION = 60 * 24;
	private Long id;
	private String token;
	private Account user;
	private DateTime expiryDate;
	public PasswordResetToken() {
        super();
    }

    public PasswordResetToken(String token) {
        super();

        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
       
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Account getUser() {
		return user;
	}

	public void setUser(Account user) {
		this.user = user;
	}

	public DateTime getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(DateTime expiryDate) {
		this.expiryDate = expiryDate;
	}

	private DateTime calculateExpiryDate(int expiryTimeInMinutes) {
    	DateTime cal = DateTime.now();
        return cal.plusMinutes(expiryTimeInMinutes);
    }
    
    public void updateToken(String token) {
        this.token = token;
        this.expiryDate = calculateExpiryDate(EXPIRATION);
    }
}
