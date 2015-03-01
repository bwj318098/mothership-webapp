package com.acss.core.dataentry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.acss.core.dataentry.common.constants.BankAccountType;
import com.acss.core.dataentry.common.constants.Citizenship;
import com.acss.core.dataentry.common.constants.CivilStatus;
import com.acss.core.dataentry.common.constants.DaysOfMonth;
import com.acss.core.dataentry.common.constants.EducationalAttainment;
import com.acss.core.dataentry.common.constants.EmploymentStatus;
import com.acss.core.dataentry.common.constants.Gender;
import com.acss.core.dataentry.common.constants.MailTo;
import com.acss.core.dataentry.common.constants.NatureOfBusiness;
import com.acss.core.dataentry.common.constants.ProcessingFeePayType;
import com.acss.core.dataentry.common.constants.PromoterScreening;
import com.acss.core.dataentry.common.constants.RefRelationship;
import com.acss.core.dataentry.common.constants.Term;
import com.acss.core.dataentry.common.constants.TypeOfEmployment;
import com.acss.core.dataentry.common.constants.TypeOfId;
import com.acss.core.dataentry.common.constants.TypeOfResidence;

@Controller
public class OsaDataEntryController {
	
	private static final String DATAENTRY_MODEL_ATTRIB_KEY = "dataEntryForm";
	
	@RequestMapping(value = "dataentry", method = RequestMethod.GET)
	public String index(HttpServletRequest request,Model model) {
		bindAllEnumToModel(model);
		model.addAttribute(DATAENTRY_MODEL_ATTRIB_KEY, new DataEntryDTO());
		
		return "application/dataentry";
	}
	
	@RequestMapping(value = "dataentry", method = RequestMethod.POST)
	public String dataEntry(@ModelAttribute DataEntryDTO dataEntry,Model model) {
		bindAllEnumToModel(model);
		model.addAttribute(DATAENTRY_MODEL_ATTRIB_KEY, dataEntry);
		
		return "application/dataentry";
	}
	
	private void bindAllEnumToModel(Model model) {
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
	}
}
