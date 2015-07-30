package com.acss.core.merchantupload;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

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
	private final String RS_SEQUENCE_URL_KEY = "rs.sequence.url";
	private final String RS_APPNO_URL_KEY = "rs.appno.url";
	
	private final String IMAGECODE_NUMTYPE_ENTRY = "T_IMAGE_IMAGECODE";
	private final String GROUPID_NUMTYPE_ENTRY = "T_IMAGE_GROUPID";
	private final String APPNO_NUMTYPE_ENTRY = "T_APPLICATION_APPCD";
	
	/**
	 * Saves the file into the server.
	 * the upload directory can be configured in osa.properties.
	 * with key 'upload.directory'
	 * 
	 * @param applicationFolder - application number as file grouping.
	 * @param file - the uploaded file from the client.
	 * @return boolean
	 */
	private boolean saveFile(String groupFolder,MultipartFile file,com.acss.core.model.image.ApplicationImage appImage) {
		String saveDirectory = env.getProperty(UPLOAD_DIRECTORY_KEY);
		String fileName = file.getOriginalFilename();
		String fileExtension = FilenameUtils.getExtension(fileName);
		String imageCode = generateRequestedNumType(IMAGECODE_NUMTYPE_ENTRY);
		
		if (!"".equalsIgnoreCase(fileName)) {
			// Handle file content - multipartFile.getInputStream()
			try {
				File newDirectory = new File(saveDirectory + File.separator
						+ groupFolder);
				// creates new directory using the application number
				if (!newDirectory.exists())
					newDirectory.mkdirs();
				//rename the file using the generated key
				file.transferTo(new File(newDirectory.getAbsolutePath()
						+ File.separator + imageCode+"."+fileExtension));
				
				appImage.setImageCode(imageCode);
				appImage.setImageFilename(imageCode+"."+fileExtension);
				appImage.setImagePath(newDirectory.getAbsolutePath()+ File.separator + imageCode+".jpg");
				return true;
			} catch (IllegalStateException | IOException e) {
				return false;
			}
		} else
			return false;
	}
	
	/**
	 * Generates code depending on the requested numtype.
	 * @param numType
	 * @return requested Code
	 */
	private String generateRequestedNumType(String numType) {
		String imagesRestFulEndpoint = env.getProperty(RS_SEQUENCE_URL_KEY);
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> res = rt.postForEntity(imagesRestFulEndpoint,numType,String.class);
		return res.getBody();
	}

	/**
	 * Processes the uploaded images and information supplied.
	 * return true if success; false otherwise
	 * @param uploadInformation - the multipartFile from client which contains information of the upload
	 * 								by the client.
	 * @return boolean
	 */
	public boolean processUpload(UploadInformation uploadInformation) {
		List<HpsUploadFile> hpsFiles = uploadInformation.getUploadFiles();
		String appNo = uploadInformation.getAppNo();
		String usingThisGroup = generateRequestedNumType(GROUPID_NUMTYPE_ENTRY);
		RestTemplate rt = new RestTemplate();
		//if files aren't empty then proceed.
		if(!hpsFiles.isEmpty()){
			for(HpsUploadFile hpsFile:hpsFiles){
				MultipartFile withThisFile = hpsFile.getImageFile();
				//creates a new instance of application image dto to persist
				com.acss.core.model.image.ApplicationImage withThisDTO = 
						new ImageBuilder().withDefaultValues().build();
				
				withThisDTO.setDataCd(appNo);
				withThisDTO.setGroupId(usingThisGroup);
				withThisDTO.setImageType(new BigDecimal(hpsFile.getImageType()));
				
				if(!saveFile(usingThisGroup,withThisFile,withThisDTO)) return false;

					//do a post on rs-images restful end point.
					try {
						String imagesRestFulEndpoint = env.getProperty(RS_IMAGES_URL_KEY);
						rt.postForEntity(imagesRestFulEndpoint,withThisDTO,
										com.acss.core.model.image.ApplicationImage.class);
					//need to create an elegant way to implement exception handling
					} catch (Exception e) {
						return false;
					}
			}
		//otherwise return false.
		}else{
			return false;
		}
		return true;
	}

	/**
	 * Generates Application No from a Restful Webservice URI.
	 */
	public String generateAppNo() {
		String appNoGeneratorURI = env.getProperty(RS_APPNO_URL_KEY);
		RestTemplate rt = new RestTemplate();
		ResponseEntity<String> res = rt.postForEntity(appNoGeneratorURI,APPNO_NUMTYPE_ENTRY,String.class);
		return res.getBody();
	}

}
