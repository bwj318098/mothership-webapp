package com.acss.core.home;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acss.core.merchantupload.UploadInformationDTO;

@Controller
public class HomeController {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request,Model model) {
		//if model already contains this attribute then initialize it.
		if(!model.containsAttribute("uploadInformation")){
			model.addAttribute("uploadInformation",new UploadInformationDTO());
		}
		// this is the default role for the user in the system.
		return request.isUserInRole("ROLE_USER") ? "home/merchantupload" : "home/index";
	}
}
