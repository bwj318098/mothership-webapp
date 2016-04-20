package com.acss.core.internal.rs;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.acss.core.model.image.ApplicationImage;

@RestController
public class ImageStreamerController {
	@Autowired
	private Environment env;
	
	private static final String IMAGES_URL_KEY = "rs.images.url";
	
	@RequestMapping(value = "/images/{imageCode}/view",method = RequestMethod.GET)
	public ResponseEntity<FileSystemResource> images(@PathVariable String imageCode){
		
		RestTemplate template = new RestTemplate();
		String uri = env.getProperty(IMAGES_URL_KEY);
		ResponseEntity<ApplicationImage> result = template.getForEntity(uri+"/"+imageCode+"/view",ApplicationImage.class);
		
		String imagePath = result.getBody().getImagePath();
		File imageFile = new File(imagePath);
		
		
		return ResponseEntity.ok()
	            .contentType(MediaType.IMAGE_JPEG)
	            .body(new FileSystemResource(imageFile));
	}
	
}
