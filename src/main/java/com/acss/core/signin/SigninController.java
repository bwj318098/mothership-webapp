package com.acss.core.signin;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.support.web.MessageHelper;
@Controller
public class SigninController {

	@Autowired
	Environment env;
	
	@RequestMapping(value = "signin")
	public String signin(HttpServletRequest request,Model model) {
		
		includeBuildInfo(model);
		//dispatch to signed in page if logged in instead of login page.
		return request.isUserInRole("ROLE_USER") ? "redirect:/" : "signin/signin";
    }

	@RequestMapping(value="invalidSession")
	public String invalidSession(RedirectAttributes ra,Model model) {
		includeBuildInfo(model);
		
    	MessageHelper.addErrorAttribute(ra, "signin.sessionExpired");
		return "redirect:/signin";
    }
	
	private void includeBuildInfo(Model model) {
		model.addAttribute("version", env.getProperty("version"));
		model.addAttribute("buildDate", env.getProperty("build.date"));
	}
}
