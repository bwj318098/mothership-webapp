package com.acss.core.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.merchantupload.FileUploadService;
import com.acss.core.merchantupload.HpsImageType;
import com.acss.core.merchantupload.HpsUploadFileDTO;
import com.acss.core.merchantupload.validator.TaggedAsCompleteInformationData;
import com.acss.core.merchantupload.validator.UploadInformationData;
import com.acss.core.support.web.MessageHelper;

/**
 * Controller for the Application Related operation like search,show details and  upload more image/s.
 * @author gvargas
 *
 */
@Controller
public class OsaApplicationController {
	private static final String BINDING_RESULT_KEY = "org.springframework.validation.BindingResult.";
	private static final String APPDETAIL_MODEL_ATTRIB_KEY = "appDetailsForm";
	private static final String APPSEARCH_MODEL_ATTRIB_KEY = "appSearchForm";
	private static final String APPSEARCH_MODEL_APPSTATUS_KEY = "listAppStatus";
	private static final String uri = "/applications?";
	
	@Autowired
	private HpsApplicationService rsApplicationService;
	@Autowired
	private FileUploadService uploadService;
	
	@RequestMapping(value = "search")
	public String search(Model model,
			@RequestParam(value = "applicationNo", required = false) String applicationNo,
			@RequestParam(value = "appDateFrom", required = false) String appDateFrom,
			@RequestParam(value = "appDateTo", required = false) String appDateTo,
			@RequestParam(value = "customerName", required = false) String customerName,
			@RequestParam(value = "seqNo", required = false) String seqNo,
			@RequestParam(value = "appStatus", required = false) String appStatus) {
		
		ApplicationSearchCriteriaDTO searchCriteria = new ApplicationSearchCriteriaDTO(applicationNo, seqNo,
				customerName, appDateFrom, appDateTo, appStatus);
		//retain the search parameter
		model.addAttribute(APPSEARCH_MODEL_ATTRIB_KEY,searchCriteria);
		model.addAttribute(APPSEARCH_MODEL_APPSTATUS_KEY,ApplicationStatus.values());
		model.addAttribute("searchUrl", searchCriteria.appendParameters(uri));
		
		return "search/searchapplication";
	}
	/**
	 * Re Initializes the search criteria and show the search page
	 * @param model
	 * @return
	 */
	@RequestMapping(value="search",params={"resetFields"})
	public String search(Model model){
		model.addAttribute(APPSEARCH_MODEL_ATTRIB_KEY,new ApplicationSearchCriteriaDTO());
		return "redirect:/search";
	}
	
	/**
	 * Gets the application details using the application code.
	 * 
	 * @param appNo
	 * @param model
	 * @return application detail screen.
	 */
	@RequestMapping(value = "detail/{appNo}")
	public String getApplication(@PathVariable String appNo,Model model){
		model.addAttribute("listImageType", HpsImageType.values());
		if(!model.containsAttribute(APPDETAIL_MODEL_ATTRIB_KEY)){
			ApplicationDetailDTO appDetail = rsApplicationService.getHpsApplication(appNo);
			model.addAttribute("appDetailsForm",appDetail);
		}
		return "upload/applicationdetail";
	}
	
	/**
	 * Adds more images/upload field/s for user to upload.
	 * 
	 * @param appNo
	 * @param ra
	 * @param appDetailsForm
	 * @return application detail screen.
	 */
	@RequestMapping(value = "detail/{appNo}",params = {"addImage"})
	public String addMoreImages(@PathVariable String appNo,
			RedirectAttributes ra,@ModelAttribute ApplicationDetailDTO appDetailsForm){
		HpsUploadFileDTO additionalImage = new HpsUploadFileDTO();
		appDetailsForm.addMoreImages(additionalImage);
		ra.addFlashAttribute(APPDETAIL_MODEL_ATTRIB_KEY, appDetailsForm);
		return "redirect:/detail/"+appNo;
	}
	
	/**
	 * Submits the application with newly added images.
	 * 
	 * @param appNo
	 * @param ra
	 * @param appDetailsForm
	 * @param errors
	 * @return application detail screen.
	 */
	@RequestMapping(value = "detail/{appNo}",method = RequestMethod.POST)
	public String uploadMore(@PathVariable String appNo,RedirectAttributes ra,
				@ModelAttribute @Validated(UploadInformationData.class) ApplicationDetailDTO appDetailsForm,
				BindingResult errors){
		
		if (errors.hasErrors()) {
			//This is to preserve the validation results in case of redirection.
			ra.addFlashAttribute(APPDETAIL_MODEL_ATTRIB_KEY, appDetailsForm);
			ra.addFlashAttribute(BINDING_RESULT_KEY+APPDETAIL_MODEL_ATTRIB_KEY, errors);
			return "redirect:/detail/"+appNo;
		}
		
		if(uploadService.uploadMoreImages(appDetailsForm)){
        	MessageHelper.addSuccessAttribute(ra, "upload.success",appDetailsForm.getAppNo());
        }else{
        	MessageHelper.addErrorAttribute(ra, "upload.error");
        }
		
		return "redirect:/detail/"+appNo;
	}
	
	
	/**
	 * Tags the application as complete and becomes ready for Data Entry.
	 * @RequestMapping(value = "upload",params = {"pending"})
	 * @param ra
	 * @param completeUpload
	 * @param errors
	 * @return view
	 */
	@RequestMapping(value = "detail/{appNo}",method = RequestMethod.POST,params = {"complete"})
	public String completeSubmission(RedirectAttributes ra,
			@PathVariable String appNo,
			@ModelAttribute @Validated(TaggedAsCompleteInformationData.class)ApplicationDetailDTO completeUpload,
			BindingResult errors){
		
		if (errors.hasErrors()) {
			//This is to preserve the validation results in case of redirection.
			ra.addFlashAttribute(APPDETAIL_MODEL_ATTRIB_KEY, completeUpload);
			ra.addFlashAttribute(BINDING_RESULT_KEY+APPDETAIL_MODEL_ATTRIB_KEY, errors);
			return "redirect:/detail/"+appNo;
		}
		
		if(completeUpload.getAdditionalImages().size() > 1){
			if(!uploadService.uploadMoreImages(completeUpload)){
				//put error if it fails.
				MessageHelper.addErrorAttribute(ra, "upload.error");
			}
		}
		
		if(uploadService.updateApplicationStatusAsComplete(appNo)){
        	MessageHelper.addSuccessAttribute(ra, "upload.success",appNo);
        }else{
        	MessageHelper.addErrorAttribute(ra, "upload.status.error");
        }
		
		return "redirect:/detail/"+appNo;
	}
}
