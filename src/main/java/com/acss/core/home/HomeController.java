package com.acss.core.home;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acss.core.merchantupload.UploadInformation;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(Principal principal,Model model) {
		model.addAttribute(new UploadInformation());
		return principal != null ? "home/merchantupload" : "home/index";
	}
}
