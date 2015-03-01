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

import com.acss.core.model.application.ApplicationSeqNo;
import com.acss.core.model.image.ApplicationImage;
import com.acss.core.model.image.ImageBuilder;
import com.acss.core.search.ApplicationDetailDTO;


/**
 * Implements the file-upload service used for saving file to the server directory
 * for streaming purposes.
 * @author gvargas
 *
 */
@Service
public class RSFileUpload implements FileUploadService {
	
	@Autowired
	private Environment env;
	/**
	 * refer to osa.properties for the value of key 'upload.directory'
	 */
	private final static String UPLOAD_DIRECTORY_KEY = "upload.directory";
	/**
	 * refer to osa.properties for the value of key 'rs.images.url'
	 */
	private final static String RS_IMAGES_URL_KEY = "rs.images.url";
	private final static String RS_SEQUENCE_URL_KEY = "rs.sequence.url";
	private final static String RS_APPNO_URL_KEY = "rs.appno.url";
	private final static String RS_APPSEQNO_URL_KEY = "rs.appseqno.url";
	
	private final static String IMAGECODE_NUMTYPE_ENTRY = "T_IMAGE_IMAGECODE";
	private final static String GROUPID_NUMTYPE_ENTRY = "T_IMAGE_GROUPID";
	private final static String APPNO_NUMTYPE_ENTRY = "T_APPLICATION_APPCD";
	private final static BigDecimal PENDING_STATUS_IN_OSA = new BigDecimal(3);
	
	/**
	 * Saves the file into the server.
	 * the upload directory can be configured in osa.properties.
	 * with key 'upload.directory'
	 * 
	 * @param applicationFolder - application number as file grouping.
	 * @param file - the uploaded file from the client.
	 * @return boolean
	 */
	private boolean saveFile(String groupFolder,MultipartFile file,ApplicationImage appImage) {
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
	public boolean processUpload(UploadInformationDTO uploadInformation) {
		String appNo = uploadInformation.getAppNo();
		
		RestTemplate rt = new RestTemplate();
		ApplicationSeqNo appSeqNo = new ApplicationSeqNo();
		appSeqNo.setAppCd(uploadInformation.getAppNo());
		appSeqNo.setAppSeqNo(uploadInformation.getSeqNo());

		//saves the new sequence no with this application no.
		rt.postForEntity(env.getProperty(RS_APPSEQNO_URL_KEY), appSeqNo, ApplicationSeqNo.class);
		return doPostInRsImageUpload(appNo,uploadInformation.getUploadFiles(),uploadInformation.isForPendingSubmission());
	}
	
	/**
	 * Implements the uploading of additional images.
	 * @param additionalUpload
	 * @return boolean
	 */
	public boolean uploadMoreImages(ApplicationDetailDTO additionalUpload) {
		String appNo = additionalUpload.getAppNo();
		//do a post on image restful web services.
		return doPostInRsImageUpload(appNo,additionalUpload.getAdditionalImages(),false);
	}
	/**
	 * POST's to the image web services to create a new image.
	 * @param appNo
	 * @param hpsFiles
	 * @return true if succeed false if not.
	 */
	private boolean doPostInRsImageUpload(String appNo,List<HpsUploadFileDTO> hpsFiles,boolean isForPendingSubmission){
		//get a sequence number first.
		String usingThisGroup = generateRequestedNumType(GROUPID_NUMTYPE_ENTRY);
		RestTemplate rt = new RestTemplate();
		
		//if files aren't empty then proceed.
		if(!hpsFiles.isEmpty()){
			for(HpsUploadFileDTO hpsFile:hpsFiles){
				MultipartFile withThisFile = hpsFile.getImageFile();
				//creates a new instance of application image dto to persist
				ApplicationImage withThisDTO = 
						new ImageBuilder().withDefaultValues().build();
				
				withThisDTO.setDataCd(appNo);
				withThisDTO.setGroupId(usingThisGroup);
				withThisDTO.setImageType(new BigDecimal(hpsFile.getImageType()));
				//set the regStatus into 3 - meaning this application is pending.
				if(isForPendingSubmission){
					withThisDTO.setRegStatus(PENDING_STATUS_IN_OSA);
				}
				
				if(!saveFile(usingThisGroup,withThisFile,withThisDTO)) return false;

					//do a post on rs-images restful end point.
					try {
						String imagesRestFulEndpoint = env.getProperty(RS_IMAGES_URL_KEY);
						rt.postForEntity(imagesRestFulEndpoint,withThisDTO,ApplicationImage.class);
					//need to create an elegant way to implement exception handling
					} catch (Exception e) {
						return false;
					}
			}
		//otherwise return false.
		}else{
			return false;
		}
		//return true if no errors encountered.
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