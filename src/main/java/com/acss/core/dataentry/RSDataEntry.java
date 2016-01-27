package com.acss.core.dataentry;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

import com.acss.core.account.OsaUserDetailsService;
import com.acss.core.model.application.PromotionRules;
import com.acss.core.model.dataentry.CustomerSearchDTO;
import com.acss.core.model.dataentry.DataEntryDTO;
import com.acss.core.model.dataentry.common.NameField;
import com.acss.core.model.dataentry.common.constants.BankAccountType;
import com.acss.core.model.dataentry.common.constants.Citizenship;
import com.acss.core.model.dataentry.common.constants.CivilStatus;
import com.acss.core.model.dataentry.common.constants.DaysOfMonth;
import com.acss.core.model.dataentry.common.constants.DeliveryMethod;
import com.acss.core.model.dataentry.common.constants.EducationalAttainment;
import com.acss.core.model.dataentry.common.constants.EmploymentStatus;
import com.acss.core.model.dataentry.common.constants.Gender;
import com.acss.core.model.dataentry.common.constants.LivePerson;
import com.acss.core.model.dataentry.common.constants.MailTo;
import com.acss.core.model.dataentry.common.constants.NatureOfBusiness;
import com.acss.core.model.dataentry.common.constants.ProcessingFeePayType;
import com.acss.core.model.dataentry.common.constants.PromoterScreening;
import com.acss.core.model.dataentry.common.constants.RefRelationship;
import com.acss.core.model.dataentry.common.constants.Term;
import com.acss.core.model.dataentry.common.constants.TypeOfEmployment;
import com.acss.core.model.dataentry.common.constants.TypeOfId;
import com.acss.core.model.dataentry.common.constants.TypeOfResidence;

/**
 * Restful implementation of saving data entry information.
 * @author gvargas
 */
@Service
public class RSDataEntry implements DataEntryService{
	
	@Autowired
	private Environment env;
	@Autowired
	private OsaUserDetailsService userService;
	/**
	 * refer to osa.properties for the value of key 'rs.images.url'
	 */
	private final static String RS_DATAENTRY_URL_KEY = "rs.dataentry.url";
	
	private final static String RS_DATAENTRYDETAIL_URL_KEY = "rs.dataentrydetails.url";
	
	private final static String RS_PROMOTION_URL_KEY = "rs.util.promotion.url";
	
	private static final String MCUSTOMER_URL_KEY = "rs.util.customers.url";
	
	public boolean save(DataEntryDTO dataEntry) {
		RestTemplate rt = new RestTemplate();	
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String storeCd = userService.getStorecdByUsername(auth.getName());
		//set stored code for retrieving information
		dataEntry.getStore().setStoreCd(storeCd);
		//audit purposes
		dataEntry.setCrePerson(auth.getName());
		dataEntry.setUpdPerson(auth.getName());
		/*
		 * re-check for customer existence in case user did not explicitly click 
		 * search to do an automatic detection so no new customer code.
		 */
		if(dataEntry.getCustomerCd().length()==0){
			dataEntry.setCustomerCd(checkIfCustomerExists(dataEntry.getApplicantName(), dataEntry.getDateOfBirth()));
		}
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<DataEntryDTO> requestEntity = new HttpEntity<DataEntryDTO>(dataEntry,headers);
		
		String uri = MessageFormat.format(env.getProperty(RS_DATAENTRY_URL_KEY), storeCd);
		
		ResponseEntity<DataEntryDTO> res = rt.exchange(uri,
				HttpMethod.POST,requestEntity, DataEntryDTO.class);
		
		
		return res==null?false:true;
	}

	public void bindAllEnumsToModel(Model model) {
		model.addAttribute(TypeOfId.MODEL_ATTRIB_KEY, TypeOfId.values());
		model.addAttribute(EmploymentStatus.MODEL_ATTRIB_KEY,EmploymentStatus.values());
		model.addAttribute(TypeOfId.MODEL_ATTRIB_KEY, TypeOfId.values());
		model.addAttribute(EducationalAttainment.MODEL_ATTRIB_KEY, EducationalAttainment.values());
		model.addAttribute(Citizenship.MODEL_ATTRIB_KEY, Citizenship.values());
		model.addAttribute(Gender.MODEL_ATTRIB_KEY, Gender.values());
		model.addAttribute(MailTo.MODEL_ATTRIB_KEY, MailTo.values());
		model.addAttribute(TypeOfResidence.MODEL_ATTRIB_KEY, TypeOfResidence.values());
		model.addAttribute(CivilStatus.MODEL_ATTRIB_KEY, CivilStatus.values());
		model.addAttribute(NatureOfBusiness.MODEL_ATTRIB_KEY,NatureOfBusiness.values());
		model.addAttribute(TypeOfEmployment.MODEL_ATTRIB_KEY,TypeOfEmployment.values());
		model.addAttribute(DaysOfMonth.MODEL_ATTRIB_KEY,DaysOfMonth.values());
		model.addAttribute(BankAccountType.MODEL_ATTRIB_KEY,BankAccountType.values());
		model.addAttribute(RefRelationship.MODEL_ATTRIB_KEY,RefRelationship.values());
		model.addAttribute(Term.MODEL_ATTRIB_KEY,Term.values());
		model.addAttribute(ProcessingFeePayType.MODEL_ATTRIB_KEY,ProcessingFeePayType.values());
		model.addAttribute(PromoterScreening.MODEL_ATTRIB_KEY,PromoterScreening.values());
		model.addAttribute(LivePerson.MODEL_ATTRIB_KEY,LivePerson.values());
		model.addAttribute(DeliveryMethod.MODEL_ATTRIB_KEY,DeliveryMethod.values());
	}
	/**
	 * @author fcortez
	 * return PromotionRules
	 * 
	 */
	public PromotionRules getPromotionDetails(String promotionCd){
		
		RestTemplate template = new RestTemplate();
		
		ModelMapper mapper = new ModelMapper();
		
		String uri = MessageFormat.format(env.getProperty(RS_PROMOTION_URL_KEY),promotionCd);
		
		PromotionRules rawDetails = template.getForObject(uri, PromotionRules.class);
		
		PromotionRules installmentDetail = mapper.map(rawDetails, PromotionRules.class);
		
		return installmentDetail;	
	}

	public DataEntryDTO getDetails(String customerCd) {
		RestTemplate template = new RestTemplate();
		String uri = MessageFormat.format(env.getProperty(RS_DATAENTRYDETAIL_URL_KEY),customerCd);
		ResponseEntity<DataEntryDTO> response = template.getForEntity(uri,DataEntryDTO.class);
		return response.getBody();
	}

	/**
	 * Uses the internal restful endpoint to look for customer data.
	 * if it exists then return the customer code.
	 * if not then return blank.
	 */
	private String checkIfCustomerExists(NameField customerName, DateTime dateOfBirth) {
		RestTemplate template = new RestTemplate();
		//format to proper system recognized format
		DateTimeFormatter dtfOut = DateTimeFormat.forPattern("yyyyMMdd");
		
		String uri = MessageFormat.format(env.getProperty(MCUSTOMER_URL_KEY),"");
		uri = uri + "/?";
		uri = appendParameters(uri, customerName.getFirstName(), customerName.getMiddleName(),
				customerName.getSurName(), dtfOut.print(dateOfBirth));
		
		String baseUrl = MessageFormat.format(env.getProperty(MCUSTOMER_URL_KEY),"")+"/?";
		CustomerSearchDTO[] mCustomers = null;
		
		List<CustomerSearchDTO> customers = new ArrayList<>();
		if(baseUrl.compareToIgnoreCase(uri)!=0){
			mCustomers = template.getForObject(uri, CustomerSearchDTO[].class);
			customers = Arrays.asList(mCustomers);
		}
		
		if(customers.isEmpty()){
			return null;
		}else{
			//if it exists then just get the top most result.
			return customers.get(0).getCustomerCd();
		}
		
	}
	
	/**
	 * Append neeed parameter string for the request to utils endpoints.
	 * @param uri
	 * @param firstName
	 * @param middleName
	 * @param lastName
	 * @param dateOfBirth
	 * @return
	 */
	private String appendParameters(String uri, String firstName, 
			String middleName, String lastName,String dateOfBirth) {
		//bootstrap the enum for map.
		TypeOfId.values();
		
		uri = firstName != null && firstName.length()>0 ? uri + "firstName=" + firstName + "&" : uri;
		uri = middleName != null && middleName.length()>0 ? uri + "middleName=" + middleName + "&" : uri;
		uri = lastName != null && lastName.length()>0 ? uri + "lastName=" + lastName + "&" : uri;
		uri = dateOfBirth != null && dateOfBirth.length()>0 ? uri + "dateOfBirth=" + dateOfBirth.replaceAll("-", "") + "&" : uri;
		return uri;
	}

}
