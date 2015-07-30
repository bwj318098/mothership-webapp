package com.acss.core.merchantupload;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.support.web.MessageHelper;

/**
 * Controller for the merchant upload process which controls the page transition, handles validation
 * and delegate process to appropriate service.
 * Controllers should have minimal process/code.
 * 
 * @author gvargas
 *
 */
@Controller
public class MerchantUploadController {
	
	final String BINDING_RESULT_KEY = "org.springframework.validation.BindingResult.";
	final String FILEUPLOAD_MODEL_ATTRIB_KEY = "uploadInformation";
	
	@Autowired
	private FileUploadService uploadService;
	
	/**
	 * Uploads the images to server and persists the information to hps database via
	 * web services.
	 * 
	 * @param ra
	 * @param uploadInformation
	 * @param errors
	 * @return redirect to home.
	 */
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
	
	/**
	 * Adds more image/s in case the pre-defined image set is not enough.
	 * 
	 * @param ra
	 * @param newUpload
	 * @param errors
	 * @return redirect to home.
	 */
	@RequestMapping(value = "upload",params = {"addImage"})
	public String addMoreImage(RedirectAttributes ra,
			@ModelAttribute UploadInformation newUpload,
			BindingResult errors){

		HpsUploadFile additionalImage = new HpsUploadFile();
		newUpload.addMoreImages(additionalImage);
		
		ra.addFlashAttribute(FILEUPLOAD_MODEL_ATTRIB_KEY, newUpload);
		ra.addFlashAttribute(BINDING_RESULT_KEY+FILEUPLOAD_MODEL_ATTRIB_KEY, errors);
		return "redirect:/";
	}
	
	/**
	 * Generates Application No.
	 * @param ra
	 * @param newUpload
	 * @param errors
	 * @return application No.
	 */
	@RequestMapping(value = "upload",params = {"generate"})
	public String generateAppCd(RedirectAttributes ra,
			@ModelAttribute UploadInformation newUpload,
			BindingResult errors){
		newUpload.setAppNo(uploadService.generateAppNo());
		ra.addFlashAttribute(FILEUPLOAD_MODEL_ATTRIB_KEY, newUpload);
		ra.addFlashAttribute(BINDING_RESULT_KEY+FILEUPLOAD_MODEL_ATTRIB_KEY, errors);
		return "redirect:/";
	}
	
	
	
}
