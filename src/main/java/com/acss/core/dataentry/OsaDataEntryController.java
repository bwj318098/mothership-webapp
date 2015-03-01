package com.acss.core.dataentry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.model.dataentry.DataEntryDTO;
import com.acss.core.support.web.MessageHelper;

@Controller
public class OsaDataEntryController {
	
	private static final String DATAENTRY_MODEL_ATTRIB_KEY = "dataEntryForm";
	@Autowired
	private DataEntryService dataEntryService;
	
	@RequestMapping(value = "dataentry", method = RequestMethod.GET)
	public String index(HttpServletRequest request,Model model) {
		//binds all the enum to model
		dataEntryService.bindAllEnumsToModel(model);
		
		model.addAttribute(DATAENTRY_MODEL_ATTRIB_KEY, new com.acss.core.model.dataentry.DataEntryDTO());
		return "application/dataentry";
	}
	
	@RequestMapping(value = "dataentry", method = RequestMethod.POST)
	public String dataEntry(@ModelAttribute DataEntryDTO dataEntry,Model model,RedirectAttributes ra) {
		//binds all the enum to model
		dataEntryService.bindAllEnumsToModel(model);
		
		model.addAttribute(DATAENTRY_MODEL_ATTRIB_KEY, dataEntry);
		
		if(dataEntryService.save(dataEntry)){
        	MessageHelper.addSuccessAttribute(ra, "de.success");
        }else{
        	MessageHelper.addErrorAttribute(ra, "de.error");
        }
		
		return "redirect:/dataentry";
	}
}
