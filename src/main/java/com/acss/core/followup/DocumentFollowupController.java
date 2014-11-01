package com.acss.core.followup;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class DocumentFollowupController {
	
	@RequestMapping(value = "followup")
	public String followup(){
		return "followup/documentfollowup";
	}
}
