package com.acss.core.search;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.merchantupload.ApplicationImage;
import com.acss.core.merchantupload.ApplicationUploadDetails;


@Controller
public class ApplicationSearchController {
	
	@RequestMapping(value = "search")
	public String search(){
		return "search/searchapplication";
	}
	
	@RequestMapping(value = "detail/{appNo}", method = RequestMethod.GET)
	public String appDetail(@PathVariable String appNo,RedirectAttributes ra,Model model){
        ApplicationUploadDetails appDetailsForm = new ApplicationUploadDetails();
		setApplicationDetailStub(appNo,appDetailsForm);
		
		model.addAttribute("appDetailsForm",appDetailsForm);
		return "upload/applicationdetail";
	}
	
	/**
	 * Put some stub object. this will be replace with real deal once back end services are complete.
	 * @param appNo
	 * @param appDetailsForm
	 */
	private void setApplicationDetailStub(String appNo,
			ApplicationUploadDetails appDetailsForm) {
		ApplicationImage appImage = new ApplicationImage("C:\\dev\\images\\"+appNo+"\\"+appNo+"_AF.jpg","Application Form:");
		ApplicationImage idImage = new ApplicationImage("C:\\dev\\images\\"+appNo+"\\"+appNo+"_ID.jpg","ID Proof:");
		List<ApplicationImage> images = new ArrayList<ApplicationImage>();
		images.add(appImage);
		images.add(idImage);
		
		appDetailsForm.setCustomerName(appNo+"'s Name");
		appDetailsForm.setAppNo(appNo);
		appDetailsForm.setImages(images);
	}
}
