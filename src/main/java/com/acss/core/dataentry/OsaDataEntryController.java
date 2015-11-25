package com.acss.core.dataentry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.model.application.PromotionRules;
import com.acss.core.model.dataentry.DataEntryDTO;
import com.acss.core.support.web.AjaxUtils;

@Controller
public class OsaDataEntryController {
	
	static final String BINDING_RESULT_KEY = "org.springframework.validation.BindingResult.";	
	static final String DATAENTRY_MODEL_ATTRIB_KEY = "dataEntryForm";
		
	@Autowired
	private DataEntryService dataEntryService;
	
	@RequestMapping(value = "dataentry", method = RequestMethod.GET)
	public String index(HttpServletRequest request,Model model) {
		//binds all the enum to model
		dataEntryService.bindAllEnumsToModel(model);
		//if attribute is already present then do not initialize anything.	
		if(!model.containsAttribute(DATAENTRY_MODEL_ATTRIB_KEY))
			model.addAttribute(DATAENTRY_MODEL_ATTRIB_KEY, new com.acss.core.model.dataentry.DataEntryDTO());
		
		return "application/dataentry";
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
		return "redirect:/dataentry";
	}
	
	@RequestMapping(value = "dataentry", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataEntryResult dataEntry(@ModelAttribute @Validated DataEntryDTO dataEntry,
									BindingResult bindingResult,
									Model model) {
		
		DataEntryResult result = new DataEntryResult();
		
		if(dataEntry.getInstallment().getPromotionCode()!=null && 
		      !dataEntry.getInstallment().getPromotionCode().equals("")){
			
			ValidatePromotion validatePromo =  new ValidatePromotion();
			
			PromotionRules rules = dataEntryService.getPromotionDetails(dataEntry.getInstallment().getPromotionCode());
			
			if(!(rules.getPromotion()==null)){
				
				List<FieldError> promotionErrors = validatePromo.promotionErrors(dataEntry,rules);
				
				if(promotionErrors.size()>0){
					
					result.success = false;
					result.showInModal = true;
					result.setFieldErrors(promotionErrors);
					result.getDataEntryError();
					return result;
				}
				
			}else{
				
				List<FieldError> promotionErrors = validatePromo.promotionErrors(dataEntry,rules);
				result.success = false;
				result.setFieldErrors(promotionErrors);
				
			}
		}
				
		if(bindingResult.hasErrors()){

			result.success = false;
			result.showInModal = false;
			result.setFieldErrors(bindingResult.getFieldErrors());
			
		} else {
			
			//binds all the enum to model
			dataEntryService.bindAllEnumsToModel(model);

			try {
				result.success = dataEntryService.save(dataEntry);
				
				if(!result.success){
					result.success = false;
					result.addError(new ObjectError("error", "Error in saving data."));
				}
				
			} catch (Exception e) {
			
				result.success = false;
				result.addError(new ObjectError("error", "Error in saving data."));
				
			}	
			
		}
		return result;
	}
	
	/**
	 * 
	 * @author fsolijon
	 *
	 */
	public static class DataEntryResult {
		
		boolean success;
				
		Map<String, String> fieldErrors = new HashMap<String, String>();
		
		DataEntryDTO dataEntry;
		
		boolean showInModal;

		/**
		 * @return the success
		 */
		public boolean getSuccess() {
			return success;
		}

		/**
		 * @return the fieldErrors
		 */
		public Map<String, String> getFieldErrors() {
			return fieldErrors;
		}
		
		public DataEntryDTO getDataEntry(){
			return this.dataEntry;
		}
		
		private void setFieldErrors(List<FieldError> fieldErrors){
			for(FieldError error : fieldErrors){
				this.fieldErrors.put(error.getField(), error.getDefaultMessage());	
			}
		}
		
		private void addError(ObjectError error){
			this.fieldErrors.put(error.getObjectName(), error.getDefaultMessage());
		}

		public boolean isShowInModal() {
			return showInModal;
		}
		
		public List<DataEntryError> getDataEntryError(){
			List<DataEntryError> error = new ArrayList<DataEntryError>();
			
			for(String key : fieldErrors.values()){
				error.add(new DataEntryError(key));
			}

			return error;
			
		}
		
	}
	
	/**
	 * 
	 * @author fcortez
	 *
	 */
	public static class DataEntryError {
		
		private String error;
		
		public DataEntryError(String error){
			this.error = error;
		}
		
		public String getError() {
			return error;
		}

		
	}
}
