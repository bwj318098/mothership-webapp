package com.acss.core.merchantupload;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.support.web.MessageHelper;

@Controller
public class MerchantUploadController {
	
	final String BINDING_RESULT_KEY = "org.springframework.validation.BindingResult.";
	final String FILEUPLOAD_MODEL_ATTRIB_KEY = "uploadInformation";
	
	@Autowired
	private FileUploadService uploadService;
	
	@RequestMapping(value = "upload" , method = RequestMethod.POST)
	public String upload(RedirectAttributes ra,
			@ModelAttribute @Valid UploadInformation uploadInformation,BindingResult errors){
		
		if (errors.hasErrors()) {
			//This is to preserve the validation results in case of redirection.
			ra.addFlashAttribute(FILEUPLOAD_MODEL_ATTRIB_KEY, uploadInformation);
			ra.addFlashAttribute(BINDING_RESULT_KEY+FILEUPLOAD_MODEL_ATTRIB_KEY, errors);
			return "redirect:/";
		}
		
        if(uploadService.processUpload(uploadInformation)){
        	MessageHelper.addSuccessAttribute(ra, "upload.success",uploadInformation.getAppNo());
        }else{
        	MessageHelper.addErrorAttribute(ra, "upload.error");
        }
        
		return "redirect:/";
	}
	
	@RequestMapping(value = "upload",params = {"addImage"})
	public String addMoreImage(RedirectAttributes ra,
			@ModelAttribute UploadInformation newUpload,
			BindingResult errors){

		AdditionalImage additionalImage = new AdditionalImage();
		newUpload.addMoreImages(additionalImage);
		
		ra.addFlashAttribute(FILEUPLOAD_MODEL_ATTRIB_KEY, newUpload);
		ra.addFlashAttribute(BINDING_RESULT_KEY+FILEUPLOAD_MODEL_ATTRIB_KEY, errors);
		return "redirect:/";
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
