package com.acss.core.search;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class ApplicationSearchController {
	
	@RequestMapping(value = "search")
	public String search(){
		return "search/searchapplication";
	}
}
