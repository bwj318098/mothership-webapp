package com.acss.core.dataentry;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.application.HpsApplicationService;
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
					
		if(bindingResult.hasErrors()){

			result.success = false;
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
		
	}
}
