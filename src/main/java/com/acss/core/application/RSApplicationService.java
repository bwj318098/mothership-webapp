package com.acss.core.application;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.acss.core.account.OsaUserDetailsService;
import com.acss.core.followup.FollowupDetailDTO;
import com.acss.core.followup.RequestedDocumentDTO;
import com.acss.core.model.application.HpsApplication;
import com.acss.core.model.application.RequestedDocument;
import com.acss.core.model.image.ApplicationImage;

@Service
public class RSApplicationService implements HpsApplicationService{
	
	@Autowired
	private Environment env;
	@Autowired
	private OsaUserDetailsService userService;
	
	private final String RS_IMAGES_URL_KEY = "rs.images.url";
	private final String RS_APPLICATIONS_URL_KEY = "rs.search.applications.url";
	private final String RS_APPLICATIONFOLLOWUPS_URL_KEY = "rs.search.followup.url";
	
	
	/**
	 * Retrieves the application status for checking is the application code is eligible for data entry.
	 */
	public String getApplicationStatus(String appNo){
		RestTemplate template = new RestTemplate();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String storeCd = userService.getStorecdByUsername(auth.getName());
		
		ModelMapper mapper = new ModelMapper();
		//Application Model object from repository.
		String uri = MessageFormat.format(env.getProperty(RS_APPLICATIONS_URL_KEY), storeCd);
		HpsApplication application = template.getForObject(uri+"/"+appNo, HpsApplication.class);
		//Dto object for View.
		ApplicationDetailDTO appDetail = mapper.map(application, ApplicationDetailDTO.class);
		
		return appDetail.getApplicationStatus();
	}
	
	public ApplicationDetailDTO getHpsApplication(String appNo) {
		RestTemplate template = new RestTemplate();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String storeCd = userService.getStorecdByUsername(auth.getName());
		
		ModelMapper mapper = new ModelMapper();
		//Application Model object from repository.
		String uri = MessageFormat.format(env.getProperty(RS_APPLICATIONS_URL_KEY), storeCd);
		HpsApplication application = template.getForObject(uri+"/"+appNo, HpsApplication.class);
		//Dto object for View.
		ApplicationDetailDTO appDetail = mapper.map(application, ApplicationDetailDTO.class);
		//List of Image Model object from repository
		ApplicationImage[] rawImages = template.getForObject(env.getProperty(RS_IMAGES_URL_KEY)+"/"+appNo, ApplicationImage[].class);
		//converts as array list.
		List<ApplicationImage> imageList = Arrays.asList(rawImages);
		//gets the initial images for DTO.
		List<ExistingImageDTO> applicationImages = appDetail.getExistingImages();
		
		for(ApplicationImage image: imageList){
			applicationImages.add(mapper.map(image, ExistingImageDTO.class));
		}
		appDetail.setExistingImages(applicationImages);
		
		return appDetail;
	}
	
	public FollowupDetailDTO getAppDetailForFollowup(String appNo) {
		RestTemplate template = new RestTemplate();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String storeCd = userService.getStorecdByUsername(auth.getName());
		
		ModelMapper mapper = new ModelMapper();
		//Application Model object from repository.
		String uri = MessageFormat.format(env.getProperty(RS_APPLICATIONFOLLOWUPS_URL_KEY), storeCd);
		HpsApplication application = template.getForObject(uri+"/"+appNo, HpsApplication.class);
		//Dto object for View.
		FollowupDetailDTO appDetail = mapper.map(application, FollowupDetailDTO.class);
		//List of Image Model object from repository
		ApplicationImage[] rawImages = template.getForObject(env.getProperty(RS_IMAGES_URL_KEY)+"/"+appNo, ApplicationImage[].class);
		//converts as array list.
		List<ApplicationImage> imageList = Arrays.asList(rawImages);
		//gets the initial images for DTO.
		List<ExistingImageDTO> applicationImages = appDetail.getExistingImages();
		
		for(ApplicationImage image: imageList){
			applicationImages.add(mapper.map(image, ExistingImageDTO.class));
		}
		appDetail.setExistingImages(applicationImages);
		
		List<RequestedDocumentDTO> documentsDTO = new ArrayList<>();
		//prepare the requested document listings.
		for(RequestedDocument document:application.getRequestedDocuments()){
			RequestedDocumentDTO documentDTO = mapper.map(document, RequestedDocumentDTO.class);
			documentsDTO.add(documentDTO);
		}
		appDetail.setRequestedDocuments(documentsDTO);
		
		return appDetail;
	}

	
	public FollowupDetailDTO updateRequestedDocuments(FollowupDetailDTO followupappDetailsForm) {

		RestTemplate rt = new RestTemplate();
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String storeCd = userService.getStorecdByUsername(auth.getName());

		//Application Model object from repository.
		String uri = MessageFormat.format(env.getProperty(RS_APPLICATIONFOLLOWUPS_URL_KEY), storeCd);
		
		HpsApplication application = rt.getForObject(uri+"/"+followupappDetailsForm.getAppNo(), HpsApplication.class);
		application.updateRequestedDocIntoUploaded(followupappDetailsForm.getReqDocumentForUpdate().getSeqId(),auth.getName());
		
		ModelMapper mapper = new ModelMapper();
		RequestedDocumentDTO documentDTO = mapper.map(application.getRequestedDocumentForUpdate(), RequestedDocumentDTO.class);
		followupappDetailsForm.setReqDocumentForUpdate(documentDTO);
		
		HttpHeaders headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("Accept", "*/*");
		HttpEntity<HpsApplication> requestEntity = new HttpEntity<HpsApplication>(application,headers);
		
		ResponseEntity<HpsApplication> response = rt.exchange(uri+"/"+followupappDetailsForm.getAppNo()+"/"+followupappDetailsForm.getReqDocumentForUpdate().getSeqId(),
				HttpMethod.PUT,requestEntity, HpsApplication.class);
		if (HttpStatus.OK.equals(response.getStatusCode())) {
			return followupappDetailsForm;
		 }
		 	return null;
	}
	
	
}
