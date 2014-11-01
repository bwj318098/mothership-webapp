package com.acss.core.merchantupload;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class MerchantUploadController {
	
	@RequestMapping(value = "upload" , method = RequestMethod.POST)
	public String upload(){
		return "";
	}
}
