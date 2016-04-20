package com.acss.core.home;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acss.core.merchantupload.HpsImageType;
import com.acss.core.merchantupload.UploadInformationDTO;

@Controller
public class HomeController {
	//private static final String DATAENTRY_MODEL_ATTRIB_KEY = "dataEntryForm";	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index(HttpServletRequest request,Model model) {
		model.addAttribute("listImageType", HpsImageType.values());
		//if model already contains this attribute then initialize it.
		if(!model.containsAttribute("uploadInformation")){
			model.addAttribute("uploadInformation",new UploadInformationDTO());
		}
		// this is the default role for the user in the system.
		//return "home/index";
		return request.isUserInRole("ROLE_USER") ? "upload/ajaxupload" : "home/index";
	}
	
}
