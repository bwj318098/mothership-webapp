package com.acss.core.account;

import static com.acss.kaizen.jooq.poc.db.tables.MAccount.M_ACCOUNT;

import java.util.Collections;
import java.util.List;

import org.jooq.Condition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.acss.kaizen.jooq.poc.account.Account;
import com.acss.kaizen.jooq.poc.base.UpdateableRepository;

public class UserService implements UserDetailsService{
	
	@Autowired
	private UpdateableRepository<Account,Long> accountRepo;
	
	/**
	 * Implements loading by user name for Spring Security Context.
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Condition whereUserNameEquals = M_ACCOUNT.USERNAME.equal(username);
		List<Account> accounts = accountRepo.findUsingCondition(whereUserNameEquals);
		if(accounts.size()>0){
			Account account = accounts.get(0);
			if(account!=null){
				return createUserDetails(account);
			}else
				throw new UsernameNotFoundException("user not found"); 
		}else
			throw new UsernameNotFoundException("users not found"); 
	}
	
	/**
	 * Signs in with an account.
	 */
	public void signin(Account account) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(account));
	}
	
	/**
	 * Authenticates this account.
	 * @param account
	 * @return Authentication
	 */
	private Authentication authenticate(Account account) {
		return new UsernamePasswordAuthenticationToken(createUserDetails(account), null, Collections.singleton(createAuthority(account)));		
	}
	
	/**
	 * Creates user details for security context.
	 * @param account
	 * @return
	 */
	private User createUserDetails(Account account) {
		return new User(account.getUsername(), account.getPassword(), Collections.singleton(createAuthority(account)));
	}
	
	/**
	 * Create the Authority
	 * @param account
	 * @return
	 */
	private GrantedAuthority createAuthority(Account account) {
		return new SimpleGrantedAuthority(account.getAuthority().getRole());
	}

}
