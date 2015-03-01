package com.acss.core.signup;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.support.web.MessageHelper;
import com.acss.kaizen.jooq.poc.account.Account;
import com.acss.kaizen.jooq.poc.base.UpdateableRepository;


@Controller
public class SignupController {
	private static final String SIGNUP_VIEW_NAME = "signup/signup";
	
	@Autowired
	private UpdateableRepository<Account,Long> accountRepo;
	/**
	 * Displays the signup page only admin has access to this page.
	 */
	@RequestMapping(value = "signup")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String signup(HttpServletRequest request,Model model) {
		model.addAttribute(new SignupForm());
		return SIGNUP_VIEW_NAME;
	}
	
	/**
	 * Registers a new account and automatically log it in.
	 */
	@RequestMapping(value = "signup", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public String signup(@Valid @ModelAttribute SignupForm signupForm, Errors errors, RedirectAttributes ra) {
		if (errors.hasErrors()) {
			return SIGNUP_VIEW_NAME;
		}
		accountRepo.add(signupForm.createAccount());
        // see /WEB-INF/i18n/messages.properties and /WEB-INF/views/merchantupload.html
        MessageHelper.addSuccessAttribute(ra, "signup.regist.success",signupForm.getUsername());
		return "redirect:/signup";
	}
}
