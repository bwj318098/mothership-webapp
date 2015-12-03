package com.acss.core.dataentry;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.application.HpsApplicationService;
import com.acss.core.dto.RestResponseDTO;
import com.acss.core.model.application.PromotionRules;
import com.acss.core.model.dataentry.DataEntryDTO;
import com.acss.core.support.web.AjaxUtils;

@Controller
public class OsaDataEntryController {
	
	private static final Logger logger = LoggerFactory.getLogger(OsaDataEntryController.class);
	
	private static final String DOCUMENT_SUBMITTED="Documents Submitted";
	static final String BINDING_RESULT_KEY = "org.springframework.validation.BindingResult.";	
	static final String DATAENTRY_MODEL_ATTRIB_KEY = "dataEntryForm";
		
	@Autowired
	private DataEntryService dataEntryService;
	
	@Autowired
	private HpsApplicationService rsApplicationService;

	@Autowired
	private SimpMessagingTemplate messageTemplate;

	
	@RequestMapping(value = "dataentry/{appCd}", method = RequestMethod.GET)
	public String index(HttpServletRequest request,Model model,@PathVariable String appCd) {
		
		String appStatus = rsApplicationService.getApplicationStatus(appCd);
		//redirects to data entry case if the application is ready for entry
		if(DOCUMENT_SUBMITTED.equalsIgnoreCase(appStatus)){
			DataEntryDTO initialDTO = new com.acss.core.model.dataentry.DataEntryDTO();
			initialDTO.setApplicationNo(appCd);
			
			//binds all the enum to model
			dataEntryService.bindAllEnumsToModel(model);
			//if attribute is already present then do not initialize anything.	
			if(!model.containsAttribute(DATAENTRY_MODEL_ATTRIB_KEY))
				model.addAttribute(DATAENTRY_MODEL_ATTRIB_KEY,initialDTO);
			
			return "application/dataentry";
		}else{
			//remain on home if not.
			return "redirect:/";
		}
		
	}
	
	@RequestMapping(value = "dataentry/ajax", method = RequestMethod.POST)
	public String calc(HttpServletRequest request,
						Model model,
						@ModelAttribute DataEntryDTO dataEntry,
						RedirectAttributes ra,
						@RequestHeader("X-Requested-With") String requestedWith) {
		
		if(AjaxUtils.isAjaxRequest(requestedWith)){
			
			if(dataEntry==null) {
				model.addAttribute(DATAENTRY_MODEL_ATTRIB_KEY, new com.acss.core.model.dataentry.DataEntryDTO());
			}
			
			dataEntryService.bindAllEnumsToModel(model);
			
			model.addAttribute("installment",dataEntry.getInstallment());
			model.addAttribute("store", dataEntry.getStore());
			model.addAttribute("comment", dataEntry.getComment());
			
			return "fragments/dataentry/_prodDetail";
		}
		
		dataEntryService.bindAllEnumsToModel(model);
		model.addAttribute(DATAENTRY_MODEL_ATTRIB_KEY,dataEntry);
		//do default
		return "redirect:/dataentry/"+dataEntry.getApplicationNo();
	}
	
	/**
	 * Processes and saves the inputs in aplication data entry page.
	 */
	@RequestMapping(value = "dataentry/{appCd}", 
					method = RequestMethod.POST, 
					produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody RestResponseDTO<DataEntryDTO> saveDataEntry(
			@ModelAttribute @Validated 	DataEntryDTO dataEntry,
										BindingResult bindingResult,
						 @PathVariable 	String appCd) {
		
		RestResponseDTO<DataEntryDTO> result = new RestResponseDTO<DataEntryDTO>();
		
		this.messageTemplate.convertAndSend("/topic/dataentry", "{test: 'success'}");;
		
		if(bindingResult.hasErrors()){

			result.setSuccess(false)
				.setShowInModal(false)
				.setFieldErrors(bindingResult.getFieldErrors());
			
		} else {
		
			try {
				
				result.setSuccess(true); // assume successful unless validated otherwise
		
				performBusinessValidations(dataEntry, result);
				
				if(result.isSuccess()){
					result.setSuccess( dataEntryService.save(dataEntry) );
					
					if(!result.isSuccess()){
						result.addError("error", "Error in saving data.");
					}
				}
				
			} catch (Exception e) {
				
				result.setSuccess(false)
					.addError("error", "Error in saving data.");
				
				logger.error("Error in saveDataEntry", e);
			}	
			
		}
		
		return result;
	}
	
	private void performBusinessValidations(DataEntryDTO dataEntry, RestResponseDTO<DataEntryDTO> result) {
		if(dataEntry.getInstallment().getPromotionCode() != null
					&& dataEntry.getInstallment().getPromotionCode().trim().isEmpty()){
			
			ValidatePromotion validatePromo =  new ValidatePromotion();
			
			PromotionRules rules = dataEntryService.getPromotionDetails(dataEntry.getInstallment().getPromotionCode());
			
			if(rules.getPromotion() != null){
				
				Set<FieldError> promotionErrors = validatePromo.promotionErrors(dataEntry,rules);
				
				if(promotionErrors.size() > 0){
					
					result.setSuccess(false)
						.setShowInModal(true)
						.setFieldErrors(promotionErrors);
				}
				
			} else {
				
				Set<FieldError> promotionErrors = validatePromo.promotionErrors(dataEntry,rules);
				result.setSuccess(false)
					.setFieldErrors(promotionErrors);
				
			}
		}
	}
	
}
