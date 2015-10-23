package com.acss.core.dataentry;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class OsaDataEntryController {
	
	@RequestMapping(value = "dataentry")
	public String showDataEntry(){
		return "application/dataentry";
	}
	
	@RequestMapping(value = "dataentry", method = RequestMethod.POST)
	public String save(){
		return "/";
	}
}
