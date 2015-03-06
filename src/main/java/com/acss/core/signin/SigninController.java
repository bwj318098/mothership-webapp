package com.acss.core.signin;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SigninController {

	@RequestMapping(value = "signin")
	public String signin(Principal principal) {
		//dispatch to signed in page if logged in instead of login page.
		return principal != null ? "home/homeSignedIn" : "signin/signin";
    }

}
