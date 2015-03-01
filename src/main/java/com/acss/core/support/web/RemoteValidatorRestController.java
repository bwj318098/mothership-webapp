package com.acss.core.support.web;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.acss.core.account.OsaUserDetailsService;

@RestController
public class RemoteValidatorRestController {
	
	@Autowired
	private OsaUserDetailsService userService;
	
	@Autowired
	private Environment env;
	private static final String STORES_URL_KEY = "rs.stores.url";
	
	/**
	 * Checks if user exists return true if does not exists and return false if it exists.
	 * @param username
	 * @return
	 */
	@RequestMapping(value = "users",method = RequestMethod.GET)
	public boolean isExist(
			@RequestParam(value = "username", required = true) String username,
			HttpServletResponse response){
		response.setStatus(HttpServletResponse.SC_OK);
		try{
			userService.loadUserByUsername(username);
		}catch(DataRetrievalFailureException e){
			return Boolean.TRUE;
		}
		
		return Boolean.FALSE;
	}
	
	@RequestMapping(value = "stores",method = RequestMethod.GET)
	public boolean isStoreCdValid(@RequestParam(value = "storeCd", required = true) String storeCd,
			HttpServletResponse response){
	
		RestTemplate template = new RestTemplate();
		response.setStatus(HttpServletResponse.SC_OK);
		String uri = env.getProperty(STORES_URL_KEY)+"?storeCd="+storeCd;
		
		return template.getForObject(uri, Boolean.class);
	}
}
