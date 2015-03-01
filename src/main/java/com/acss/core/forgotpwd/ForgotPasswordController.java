package com.acss.core.forgotpwd;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.account.OsaUserDetailsService;
import com.acss.core.account.ResetPasswordService;
import com.acss.core.support.web.MessageHelper;
import com.acss.kaizen.jooq.poc.account.Account;

@Controller
public class ForgotPasswordController {
	
	private static final String BINDING_RESULT_KEY = "org.springframework.validation.BindingResult.";
	private static final String PASSWORDUPDATE_MODEL_ATTRIB_KEY = "updatePasswordForm";
	private static final String FORGOTPASSWORD_MODEL_ATTRIB_KEY = "forgotPasswordForm";

	@Autowired
	private ResetPasswordService pwdResetService;
	@Autowired
	private OsaUserDetailsService userService;
	
	
	@Autowired
	public ForgotPasswordController(ResetPasswordService pwdResetService) {
		this.pwdResetService = pwdResetService;
	}
	
	@RequestMapping(value = "forgotpwd")
	public String forgotPwd(Model model,HttpServletRequest request) {
		//dispatch to signed in page if logged in instead of login page.
		if(!model.containsAttribute(FORGOTPASSWORD_MODEL_ATTRIB_KEY)){
			model.addAttribute(FORGOTPASSWORD_MODEL_ATTRIB_KEY,new ForgotPasswordForm());
		}
				
		return request.isUserInRole("ROLE_USER") ?"redirect:/":"forgotpwd/forgotpwd";
    }
	
	@RequestMapping(value = "updatePassword")
	public String showUpdatePassword(Model model) {
		//dispatch to signed in page if logged in instead of login page.
		if(!model.containsAttribute(PASSWORDUPDATE_MODEL_ATTRIB_KEY)){
			model.addAttribute(PASSWORDUPDATE_MODEL_ATTRIB_KEY,new UpdatePasswordForm());
		}
		return "account/changepwd";
    }
	
	@RequestMapping(value = "updatePassword",method = RequestMethod.POST)
	public String updatePassword(@ModelAttribute @Valid UpdatePasswordForm newPassword,BindingResult errors,
			Principal principal, RedirectAttributes ra) {
		
		if (errors.hasErrors()) {
			//This is to preserve the validation results in case of redirection.
			ra.addFlashAttribute(PASSWORDUPDATE_MODEL_ATTRIB_KEY, newPassword);
			ra.addFlashAttribute(BINDING_RESULT_KEY+PASSWORDUPDATE_MODEL_ATTRIB_KEY, errors);
			return "redirect:/updatePassword";
		}
		//Proceed if no errors.
		Account updatedUser = pwdResetService.changePassword(principal.getName(),newPassword);
		
		if(updatedUser!=null){
			//deletes the token if update was complete:
			pwdResetService.deleteResetToken(updatedUser.getId().toString());
			
			//authenticate this user and redirect to home.
			userService.signin(updatedUser);
	        //prompt success message
	        MessageHelper.addSuccessAttribute(ra, "auth.update.success",principal.getName());
		}else{
			MessageHelper.addErrorAttribute(ra, "auth.update.failed",principal.getName());
			return "redirect:/updatePassword";
		}
        //dispatch to signed in page if logged in instead of login page.
		return "redirect:/";
    }
	
	
	@RequestMapping(value = "forgotpwd",method = RequestMethod.POST)
	public String resetPassword(@ModelAttribute @Valid ForgotPasswordForm forgotPwdForm,
			BindingResult errors, RedirectAttributes ra,
			HttpServletRequest request) {
		
		if (errors.hasErrors()) {
			//This is to preserve the validation results in case of redirection.
			ra.addFlashAttribute(FORGOTPASSWORD_MODEL_ATTRIB_KEY, forgotPwdForm);
			ra.addFlashAttribute(BINDING_RESULT_KEY+FORGOTPASSWORD_MODEL_ATTRIB_KEY, errors);
			return "redirect:/forgotpwd";
		}
		
		Account user = pwdResetService.loadUserByEmailAddress(forgotPwdForm.getEmail());
		//deletes the token if something exists.
		pwdResetService.deleteResetToken(user.getId().toString());
		//creates new password token
		PasswordResetToken token = pwdResetService.createPasswordTokenForUser(user);
		
		String appUrl ="http://" + request.getServerName() +":" + request.getServerPort() + request.getContextPath();
		
		pwdResetService.sendResetLinkToUser(user,appUrl,token.getToken());
		
		MessageHelper.addSuccessAttribute(ra, "reset.success",forgotPwdForm.getEmail());
		
		return "redirect:/";
    }
	
	@RequestMapping(value="changePassword")
	public String showChangePasswordPage(RedirectAttributes ra,@RequestParam("id") long id, @RequestParam("token") String token){
		PasswordResetToken passToken = pwdResetService.getPasswordResetToken(token);
		Account account = passToken!=null?passToken.getUser():null;
		if(passToken==null || account.getId() != id){
			MessageHelper.addErrorAttribute(ra, "auth.token.invalid");
			return "redirect:/";
		}
		//deletes the expired token
		if(passToken.getExpiryDate().isBeforeNow()){
			MessageHelper.addErrorAttribute(ra, "auth.token.expired");
			//delete expired token.
			pwdResetService.deleteResetToken(account.getId().toString());
			return "redirect:/";
		}
		//need to update this to password update form. add the for update Temporary role to protect the existing.
		userService.authenticateTemporarily(account);
        MessageHelper.addErrorAttribute(ra, "changepwd.show");
		return "redirect:/updatePassword";
	}
}
