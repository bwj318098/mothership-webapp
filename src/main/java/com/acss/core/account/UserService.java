package com.acss.core.account;

import static com.acss.kaizen.jooq.poc.db.tables.MAccount.M_ACCOUNT;

import java.util.ArrayList;
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
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.acss.kaizen.jooq.poc.account.Account;
import com.acss.kaizen.jooq.poc.base.UpdateableRepository;

public class UserService implements OsaUserDetailsService{
	
	@Autowired
	private UpdateableRepository<Account,Long> accountRepo;
	
	/**
	 * Implements loading by user name for Spring Security Context.
	 */
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		Condition whereUserNameEquals = M_ACCOUNT.USERNAME.equal(username.toUpperCase());
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
	
	public String getStorecdByUsername(String username){
		Condition whereUserNameEquals = M_ACCOUNT.USERNAME.equal(username);
		Account account = accountRepo.findUsingCondition(whereUserNameEquals).get(0);
		if(account!=null){
			return account.getStoreCd();
		}else
			throw new UsernameNotFoundException("user not found");
	}
	
	/**
	 * Signs in with an account.
	 */
	public void signin(Account account) {
		SecurityContextHolder.getContext().setAuthentication(authenticate(account));
	}
	
	/**
	 * 
	 */
	public void authenticateTemporarily(Account account){
		UserDetails user = createUserDetails(account);
		final Authentication auth = new UsernamePasswordAuthenticationToken(user, null,Collections.singleton(createTempAuthority()));
        SecurityContextHolder.getContext().setAuthentication(auth);
	}
	
	
	/**
	 * Authenticates this account.
	 * @param account
	 * @return Authentication
	 */
	private Authentication authenticate(Account account) {
		return new UsernamePasswordAuthenticationToken(createUserDetails(account), null, createAuthorities(account));		
	}
	
	/**
	 * Creates user details for security context.
	 * @param account
	 * @return
	 */
	private User createUserDetails(Account account) {
		return new User(account.getUsername(), account.getPassword(), createAuthorities(account));
	}
	
	/**
	 * Create the Authority
	 * @param account
	 * @return
	 */
	private List<GrantedAuthority> createAuthorities(Account account) {
		String sRoles = account.getAuthority().getRole();
		String[] arrayRoles = sRoles.split(",");
		List<GrantedAuthority> authorities=new ArrayList<GrantedAuthority>(arrayRoles.length);
		
		for(String sRole:arrayRoles){
			authorities.add(new SimpleGrantedAuthority(sRole));
		}
		return Collections.unmodifiableList(authorities);
	}
	
	/**
	 * ROLE_TEMP for password update only.
	 * @return
	 */
	private GrantedAuthority createTempAuthority() {
		return new SimpleGrantedAuthority("ROLE_TEMP");
	}
}
