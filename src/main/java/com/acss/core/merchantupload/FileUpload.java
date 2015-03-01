package com.acss.core.merchantupload;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.acss.core.model.ACSSDateUtil;
import com.acss.core.model.image.ImageBuilder;


/**
 * Implements the file-upload service used for saving file to the server directory
 * for streaming purposes.
 * @author gvargas
 *
 */
@Service
public class FileUpload implements FileUploadService {
	
	@Autowired
	private Environment env;
	/**
	 * refer to osa.properties for the value of key 'upload.directory'
	 */
	private final String UPLOAD_DIRECTORY_KEY = "upload.directory";
	/**
	 * refer to osa.properties for the value of key 'rs.images.url'
	 */
	private final String RS_IMAGES_URL_KEY = "rs.images.url";
	
	private RestTemplate rt;
	
	
	/**
	 * Saves the file into the server.
	 * the upload directory can be configured in osa.properties.
	 * with key 'upload.directory'
	 * 
	 * @param applicationFolder - application number as file grouping.
	 * @param file - the uploaded file from the client.
	 * @return boolean
	 */
	private boolean saveFile(String applicationFolder,MultipartFile file) {
		String saveDirectory = env.getProperty(UPLOAD_DIRECTORY_KEY);
		 String fileName = file.getOriginalFilename();
	        if (!"".equalsIgnoreCase(fileName)) {
	            // Handle file content - multipartFile.getInputStream()
	        	try {
	        		File newDirectory = new File(saveDirectory +File.separator+applicationFolder);
	        		//creates new directory using the application number
	        		if(!newDirectory.exists()) newDirectory.mkdirs();
					
	        		file.transferTo(new File(newDirectory.getAbsolutePath()+File.separator+fileName));
	        		return true;
				} catch (IllegalStateException | IOException e) {
					return false;
				}
	        }else
	        	return false;
	}
	
	/**
	 * Processes the uploaded images and information supplied.
	 * return true if success; false otherwise
	 * @param uploadInformation - the multipartFile from client which contains information of the upload
	 * 								by the client.
	 * @return boolean
	 */
	public boolean processUpload(UploadInformation uploadInformation) {
		List<MultipartFile> files = uploadInformation.getUploadFiles();
		String appFolder = uploadInformation.getAppNo();
		rt = new RestTemplate();
		//if files aren't empty then proceed.
		if(!files.isEmpty()){
			int x=0;
			for(MultipartFile file:files){
				
				if(!saveFile(appFolder,file)) return false;
				
					com.acss.core.model.image.ApplicationImage newImage = 
							new ImageBuilder().withDefaultValues().build();
					
					newImage.setImageCode(ACSSDateUtil.getDateAsYYYYMMDDFromDateTime().toString()+
							ACSSDateUtil.getTimeAsHHMMSSFromDateTime().toString().substring(3)+x);
					newImage.setGroupId(appFolder);
					newImage.setDataCd(appFolder);
					//do a post on rs-images restful end point.
					try {
						String imagesRestFulEndpoint = env.getProperty(RS_IMAGES_URL_KEY);
						rt.postForEntity(imagesRestFulEndpoint,newImage,
										com.acss.core.model.image.ApplicationImage.class);
					//need to create an elegant way to implement exception handling
					} catch (Exception e) {
						return false;
					}
					x++;
			}
		//otherwise return false.
		}else{
			return false;
		}
		return true;
	}

}
