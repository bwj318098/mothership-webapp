package com.acss.core.account;

import org.springframework.security.core.userdetails.UserDetailsService;

import com.acss.kaizen.jooq.poc.account.Account;

public interface OsaUserDetailsService extends UserDetailsService{
	/**
	 * Signs in with an account.
	 */
	public void signin(Account account);
	/**
	 * Authenticates user temporarily for update password operation.
	 */
	public void authenticateTemporarily(Account account);
	
	/**
	 * 
	 */
	public String getStorecdByUsername(String username);
}
