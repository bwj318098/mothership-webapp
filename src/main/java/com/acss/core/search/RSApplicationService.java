package com.acss.core.search;

import java.util.Arrays;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.acss.core.model.application.HpsApplication;
import com.acss.core.model.image.ApplicationImage;

@Service
public class RSApplicationService implements HpsApplicationService{
	
	@Autowired
	private Environment env;
	
	private final String RS_IMAGES_URL_KEY = "rs.images.url";
	private final String RS_APPLICATIONS_URL_KEY = "rs.applications.url";
	
	public ApplicationDetailDTO getHpsApplication(String appNo) {
		RestTemplate template = new RestTemplate();
		ModelMapper mapper = new ModelMapper();
		//Application Model object from repository.
		HpsApplication application = template.getForObject(env.getProperty(RS_APPLICATIONS_URL_KEY)+"/"+appNo, HpsApplication.class);
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

}
