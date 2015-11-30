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
import com.acss.core.application.ApplicationSearchCriteriaDTO;
import com.acss.core.model.application.HpsApplication;
import com.acss.core.model.application.ZipCode;
import com.acss.core.model.dataentry.CustomerSearchDTO;
import com.acss.core.model.dataentry.common.constants.TypeOfId;
import com.acss.core.application.ZipCodeSearchCriteriaDTO;


@RestController
public class ApplicationSearchRestController {
	@Autowired
	private Environment env;
	
	private static final String APPLICATIONS_URL_KEY = "rs.search.applications.url";
	private static final String FOLLOWUP_URL_KEY = "rs.search.followup.url";
	private static final String ZIPCODE_URL_KEY = "rs.util.zipcode.url";
	private static final String MCUSTOMER_URL_KEY = "rs.util.customers.url";
	
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
	
	@RequestMapping(value = "followups",method = RequestMethod.GET)
	public List<HpsApplication> followup(
			@RequestParam(value = "applicationNo", required = false) String applicationNo,
			@RequestParam(value = "appDateFrom", required = false) String appDateFrom,
			@RequestParam(value = "appDateTo", required = false) String appDateTo,
			@RequestParam(value = "customerName", required = false) String customerName,
			@RequestParam(value = "seqNo", required = false) String seqNo,
			@RequestParam(value = "appStatus", required = false) String appStatus,
			Principal user){
		
		String storeCd = userService.getStorecdByUsername(user.getName());
		String uri = MessageFormat.format(env.getProperty(FOLLOWUP_URL_KEY),storeCd);
		uri = uri+"/?";
		ApplicationSearchCriteriaDTO searchCriteria = new ApplicationSearchCriteriaDTO(applicationNo, seqNo,
				customerName, appDateFrom, appDateTo, appStatus);
		
		uri = searchCriteria.appendParameters(uri);
		
		RestTemplate template = new RestTemplate();
		HpsApplication[] applications = template.getForObject(uri, HpsApplication[].class);

		return Arrays.asList(applications);
	}
	
	@RequestMapping(value="zipcode", method = RequestMethod.GET)
	public List<ZipCode> zipCode(@RequestParam(value="postCode", required=false) String postCode,
			@RequestParam(value="areaName", required=false) String areaName,
			@RequestParam(value="cityNm", required=false) String cityNm,
			@RequestParam(value="region", required=false) String region,
			@RequestParam(value="postId", required=false) String postId){
			
		String uri = MessageFormat.format(env.getProperty(ZIPCODE_URL_KEY),"");
		uri = uri + "/?";
		
		ZipCodeSearchCriteriaDTO zipCodeSearchCriteria = new ZipCodeSearchCriteriaDTO(postCode,areaName,cityNm,region,postId);
		uri = zipCodeSearchCriteria.appendParameters(uri);
		
		RestTemplate template = new RestTemplate();
		
		ZipCode[] zipCodes = template.getForObject(uri, ZipCode[].class);
		return Arrays.asList(zipCodes);	
	}
	
	@RequestMapping(value="/dataentry/mcustomers", method = RequestMethod.GET)
	public List<CustomerSearchDTO> customers(
			@RequestParam(value = "applicantName.firstName", required = false) String firstName,
			@RequestParam(value = "applicantName.middleName", required = false) String middleName,
			@RequestParam(value = "applicantName.surName", required = false) String lastName,
			@RequestParam(value = "applicantId.idType", required = false) String idCardType,
			@RequestParam(value = "applicantId.idCardNo", required = false) String idCardNo,
			@RequestParam(value = "dateOfBirth", required = false) String dateOfBirth){
			
		String uri = MessageFormat.format(env.getProperty(MCUSTOMER_URL_KEY),"");
		uri = uri + "/?";
		uri = appendParameters(uri,firstName,middleName,lastName,idCardType,idCardNo,dateOfBirth);
		RestTemplate template = new RestTemplate();
		
		CustomerSearchDTO[] mCustomers = template.getForObject(uri, CustomerSearchDTO[].class);
		return Arrays.asList(mCustomers);	
	}
	
	private String appendParameters(String uri, String firstName, 
			String middleName, String lastName, String idCardType,
			String idCardNo, String dateOfBirth) {
		//bootstrap the enum for map.
		TypeOfId.values();
		
		uri = firstName != null ? uri + "firstName=" + firstName + "&" : uri;
		uri = middleName != null ? uri + "middleName=" + middleName + "&" : uri;
		uri = lastName != null ? uri + "lastName=" + lastName + "&" : uri;
		uri = idCardType != null && idCardType.length()>0? uri + "idCardType=" + TypeOfId.valueOf(idCardType).getCode() + "&" : uri;
		uri = idCardNo != null ? uri + "idCardNo=" + idCardNo + "&" : uri;
		uri = dateOfBirth != null ? uri + "dateOfBirth=" + dateOfBirth.replaceAll("-", "") + "&" : uri;
		return uri;
	}
	
}
