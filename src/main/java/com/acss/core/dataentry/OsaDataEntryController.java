package com.acss.core.dataentry;

import java.util.HashSet;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.application.HpsApplicationService;
import com.acss.core.model.application.PromotionRules;
import com.acss.core.model.dataentry.DataEntryDTO;
import com.acss.core.support.web.AjaxUtils;

@Controller
public class OsaDataEntryController {
	private static final String DOCUMENT_SUBMITTED="Documents Submitted";
	static final String BINDING_RESULT_KEY = "org.springframework.validation.BindingResult.";	
	static final String DATAENTRY_MODEL_ATTRIB_KEY = "dataEntryForm";
		
	@Autowired
	private DataEntryService dataEntryService;
	
	@Autowired
	private HpsApplicationService rsApplicationService;
	
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
	
	@RequestMapping(value = "dataentry/{appCd}", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE})
	public @ResponseBody DataEntryResult dataEntry(@ModelAttribute @Validated DataEntryDTO dataEntry,
									BindingResult bindingResult,
									Model model,
									RedirectAttributes ra,
									@PathVariable String appCd) {
		
		DataEntryResult result = new DataEntryResult();
		
		if(dataEntry.getInstallment().getPromotionCode()!=null && 
		      !dataEntry.getInstallment().getPromotionCode().equals("")){
			
			ValidatePromotion validatePromo =  new ValidatePromotion();
			
			PromotionRules rules = dataEntryService.getPromotionDetails(dataEntry.getInstallment().getPromotionCode());
			
			if(!(rules.getPromotion()==null)){
				
				HashSet<FieldError> promotionErrors = validatePromo.promotionErrors(dataEntry,rules);
				
				if(promotionErrors.size()>0){
					
					result.success = false;
					result.showInModal = true;
					result.setFieldErrors(promotionErrors);
					result.getDataEntryError();
					return result;
				}
				
			}else{
				
				HashSet<FieldError> promotionErrors = validatePromo.promotionErrors(dataEntry,rules);
				result.success = false;
				result.setFieldErrors(promotionErrors);
				
			}
		}
				
		if(bindingResult.hasErrors()){

			result.success = false;
			result.showInModal = false;
			result.setFieldErrors(new HashSet<FieldError>(bindingResult.getFieldErrors()));
			
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
		
		HashSet<DataEntryError> dataEntryError = new HashSet<>();
		
		DataEntryDTO dataEntry;
		
		boolean showInModal;

		/**
		 * @return the success
		 */
		public boolean getSuccess() {
			return success;
		}

		public DataEntryDTO getDataEntry(){
			return this.dataEntry;
		}
		
		private void setFieldErrors(HashSet<FieldError> fieldErrors){
			for(FieldError error : fieldErrors){
				this.dataEntryError.add(new DataEntryError(error.getField(), error.getDefaultMessage()));	
			}
		}
		
		private void addError(ObjectError error){
			this.dataEntryError.add(new DataEntryError(error.getObjectName(), error.getDefaultMessage()));
		}

		public boolean isShowInModal() {
			return showInModal;
		}
		
		public HashSet<DataEntryError> getDataEntryError(){
			return this.dataEntryError;
		}
		
	}
	
	/**
	 * 
	 * @author fcortez
	 *
	 */
	public static class DataEntryError {
		
		private String property;
		
		private String error;
		
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((error == null) ? 0 : error.hashCode());
			result = prime * result + ((property == null) ? 0 : property.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			DataEntryError other = (DataEntryError) obj;
			if (error == null) {
				if (other.error != null)
					return false;
			} else if (!error.equals(other.error))
				return false;
			if (property == null) {
				if (other.property != null)
					return false;
			} else if (!property.equals(other.property))
				return false;
			return true;
		}

		public DataEntryError(String property, String error) {
			this.property = property;
			this.error = error;
		}

		public DataEntryError(String error){
			this.error = error;
		}
		
		/**
		 * @return the property
		 */
		public String getProperty() {
			return property;
		}



		public String getError() {
			return error;
		}

		
	}
}
