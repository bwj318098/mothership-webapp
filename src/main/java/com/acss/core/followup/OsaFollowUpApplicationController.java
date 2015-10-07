package com.acss.core.followup;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.acss.core.application.ApplicationSearchCriteriaDTO;
import com.acss.core.application.ApplicationStatus;
import com.acss.core.application.HpsApplicationService;
import com.acss.core.merchantupload.FileUploadService;
import com.acss.core.merchantupload.HpsImageType;
import com.acss.core.merchantupload.HpsUploadFileDTO;
import com.acss.core.support.web.MessageHelper;

@Controller
public class OsaFollowUpApplicationController {
	
	private static final String APPSEARCH_MODEL_ATTRIB_KEY = "followUpSearchForm";
	private static final String APPSEARCH_MODEL_APPSTATUS_KEY = "listAppStatus";
	private static final String APPDETAIL_MODEL_ATTRIB_KEY = "followupappDetailsForm";
	private static final String uri = "/followups?";
	
	@Autowired
	private HpsApplicationService rsApplicationService;
	@Autowired
	private FileUploadService rsFileUpload;
	
	/**
	 * Searches for the application under verification with
	 * 
	 * @param model
	 * @param applicationNo
	 * @param appDateFrom
	 * @param appDateTo
	 * @param customerName
	 * @param seqNo
	 * @param appStatus
	 * @return document follow up search page.
	 */
	@RequestMapping(value = "followup")
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
		//dropdown values for application status.
		model.addAttribute(APPSEARCH_MODEL_APPSTATUS_KEY,ApplicationStatus.values());
		model.addAttribute("followUpSearchUrl", searchCriteria.appendParameters(uri));
		
		return "followup/documentfollowup";
	}
	
	/**
	 * Re Initializes the search criteria and show the search page
	 * @param model
	 * @return
	 */
	@RequestMapping(value="followup",params={"resetFields"})
	public String search(Model model){
		model.addAttribute(APPSEARCH_MODEL_ATTRIB_KEY,new ApplicationSearchCriteriaDTO());
		return "redirect:/followup";
	}
	
	@RequestMapping(value = "followup/{appNo}")
	public String followupDetails(@PathVariable String appNo,Model model){
		FollowupDetailDTO appDetail = rsApplicationService.getAppDetailForFollowup(appNo);
		model.addAttribute("listImageType", HpsImageType.values());
		if(!model.containsAttribute(APPDETAIL_MODEL_ATTRIB_KEY)){
			rsApplicationService.getAppDetailForFollowup(appNo);
			model.addAttribute(APPDETAIL_MODEL_ATTRIB_KEY,appDetail);
		}else{
			FollowupDetailDTO fromPreviousScreen = (FollowupDetailDTO) model.asMap().get(APPDETAIL_MODEL_ATTRIB_KEY);
			fromPreviousScreen.setExistingImages(appDetail.getExistingImages());
			fromPreviousScreen.setRequestedDocuments(appDetail.getRequestedDocuments());
			model.addAttribute(APPDETAIL_MODEL_ATTRIB_KEY,fromPreviousScreen);
		}
		return "followup/followupapplicationdetail";
	}
	
	
	/**
	 * Adds more images/upload field/s for user to upload.
	 * 
	 * @param appNo
	 * @param ra
	 * @param appDetailsForm
	 * @return application detail screen.
	 */
	@RequestMapping(value = "followup/{appNo}",method = RequestMethod.POST)
	public String uploadRequestedFile(@PathVariable String appNo,
			RedirectAttributes ra,@ModelAttribute FollowupDetailDTO followupappDetailsForm){
		
		if(rsFileUpload.uploadFollowUpImage(followupappDetailsForm)){
			
			MessageHelper.addSuccessAttribute(ra, "upload.success",followupappDetailsForm.getAppNo());
		}else
			MessageHelper.addErrorAttribute(ra, "upload.error");
		
		return "redirect:/followup/"+appNo;
	}
	
	/**
	 * Adds more images/upload field/s for user to upload.
	 * 
	 * @param appNo
	 * @param ra
	 * @param appDetailsForm
	 * @return application detail screen.
	 */
	@RequestMapping(value = "followup/{appNo}",params = {"addImage"})
	public String addMoreImages(@PathVariable String appNo,
			RedirectAttributes ra,@ModelAttribute FollowupDetailDTO followupappDetailsForm){
		HpsUploadFileDTO additionalImage = new HpsUploadFileDTO();
		followupappDetailsForm.addMoreImages(additionalImage);
		ra.addFlashAttribute(APPDETAIL_MODEL_ATTRIB_KEY, followupappDetailsForm);
		return "redirect:/followup/"+appNo;
	}

	
}
