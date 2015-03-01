package com.acss.core.search;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.acss.core.account.ApplicationDTO;


@RestController
public class ApplicationSearchRestController {
	private static final String uri = "http://localhost:18080/applications";

	@RequestMapping(value = "applications",method = RequestMethod.GET)
	public List<ApplicationDTO> search(){
		RestTemplate template = new RestTemplate();
		ApplicationDTO[] applications = template.getForObject(uri, ApplicationDTO[].class);

		return Arrays.asList(applications);
	}
}
