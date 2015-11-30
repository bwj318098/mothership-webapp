package com.acss.core.dataentry;

import java.text.MessageFormat;

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
import com.acss.core.model.dataentry.DataEntryDTO;
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
	
	private final static String RS_PROMOTION_URL_KEY = "rs.util.promotion.url";
	
	public boolean save(DataEntryDTO dataEntry) {
		RestTemplate rt = new RestTemplate();	
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String storeCd = userService.getStorecdByUsername(auth.getName());
		//set stored code for retrieving information
		dataEntry.getStore().setStoreCd(storeCd);
		
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

}
