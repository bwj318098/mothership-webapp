package com.acss.core.signin;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.support.web.MessageHelper;
@Controller
public class SigninController {

	@RequestMapping(value = "signin")
	public String signin(Principal principal) {
		//dispatch to signed in page if logged in instead of login page.
		return principal != null ? "redirect:/" : "signin/signin";
    }
	
	@RequestMapping(value="invalidSession")
	public String invalidSession(RedirectAttributes ra) {
    	MessageHelper.addErrorAttribute(ra, "signin.sessionExpired");
		return "redirect:/signin";
    }
}
