package com.acss.core.merchantupload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.support.web.MessageHelper;

@Controller
public class MerchantUploadController {
	
	@RequestMapping(value = "upload" , method = RequestMethod.POST)
	public String upload(RedirectAttributes ra){
        MessageHelper.addSuccessAttribute(ra, "upload.success");
		return "redirect:/";
	}
}
