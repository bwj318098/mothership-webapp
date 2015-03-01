package com.acss.core.internal.rs;

import java.security.Principal;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.acss.core.account.OsaUserDetailsService;
import com.acss.core.model.application.HpsApplication;
import com.acss.core.search.ApplicationSearchCriteriaDTO;


@RestController
public class ApplicationSearchRestController {
	@Autowired
	private Environment env;
	private static final String APPLICATIONS_URL_KEY = "rs.search.applications.url";
	
	@Autowired
	private OsaUserDetailsService userService;
	
	@RequestMapping(value = "applications",method = RequestMethod.GET)
	public List<HpsApplication> search(
			@RequestParam(value = "applicationNo", required = false) String applicationNo,
			@RequestParam(value = "appDateFrom", required = false) String appDateFrom,
			@RequestParam(value = "appDateTo", required = false) String appDateTo,
			@RequestParam(value = "customerName", required = false) String customerName,
			@RequestParam(value = "seqNo", required = false) String seqNo,
			@RequestParam(value = "appStatus", required = false) String appStatus,
			Principal user){
		
		String storeCd = userService.getStorecdByUsername(user.getName());
		String uri = MessageFormat.format(env.getProperty(APPLICATIONS_URL_KEY),storeCd);
		uri = uri+"/?";
		ApplicationSearchCriteriaDTO searchCriteria = new ApplicationSearchCriteriaDTO(applicationNo, seqNo,
				customerName, appDateFrom, appDateTo, appStatus);
		
		uri = searchCriteria.appendParameters(uri);
		
		RestTemplate template = new RestTemplate();
		HpsApplication[] applications = template.getForObject(uri, HpsApplication[].class);

		return Arrays.asList(applications);
	}

}
