package com.acss.core.dataentry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.model.dataentry.DataEntryDTO;
import com.acss.core.support.web.AjaxUtils;
import com.acss.core.support.web.MessageHelper;

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
	
	@RequestMapping(value = "dataentry", method = RequestMethod.POST)
	public String dataEntry(@ModelAttribute @Validated DataEntryDTO dataEntry,
							BindingResult bindingResult,
							Model model,
							RedirectAttributes ra) {
		
		ra.addFlashAttribute(DATAENTRY_MODEL_ATTRIB_KEY, dataEntry);
		
		if (bindingResult.hasErrors()) {
			//This is to preserve the validation results in case of redirection.
			ra.addFlashAttribute(BINDING_RESULT_KEY+DATAENTRY_MODEL_ATTRIB_KEY, bindingResult);
			return "redirect:/dataentry";
		}
		
		//binds all the enum to model
		dataEntryService.bindAllEnumsToModel(model);
				
		if(dataEntryService.save(dataEntry)){
        	MessageHelper.addSuccessAttribute(ra, "de.success");
        } else {
        	MessageHelper.addErrorAttribute(ra, "de.error");
        }
		
		return "redirect:/dataentry";
	}
}
