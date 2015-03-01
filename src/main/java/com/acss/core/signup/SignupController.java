package com.acss.core.signup;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.account.UserService;
import com.acss.core.support.web.MessageHelper;
import com.acss.kaizen.jooq.poc.account.Account;
import com.acss.kaizen.jooq.poc.base.UpdateableRepository;


@Controller
public class SignupController {
	private static final String SIGNUP_VIEW_NAME = "signup/signup";
	//private static final String USER_HOME_VIEW_NAME = "home/merchantupload";
	
	@Autowired
	private UpdateableRepository<Account,Long> accountRepo;

	@Autowired
	private UserService userService;
	
	/**
	 * Displays the login page
	 */
	@RequestMapping(value = "signup")
	public String signup(Principal principal,Model model) {
		model.addAttribute(new SignupForm());
		return principal != null ? "redirect:/" : SIGNUP_VIEW_NAME;
	}
	
	/**
	 * Registers a new account and automatically log it in.
	 */
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}
		Account account = accountRepo.add(signupForm.createAccount());
		userService.signin(account);
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/merchantupload.html
        MessageHelper.addSuccessAttribute(ra, "signup.success",signupForm.getUsername());
		return "redirect:/";
	}
}
