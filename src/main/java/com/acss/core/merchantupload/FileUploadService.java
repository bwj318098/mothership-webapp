package com.acss.core.merchantupload;

import org.springframework.web.multipart.MultipartFile;

import com.acss.core.application.ApplicationDetailDTO;
import com.acss.core.followup.FollowupDetailDTO;
import com.acss.core.model.application.ApplicationSeqNo;
import com.acss.core.model.image.ApplicationImage;


public interface FileUploadService {
	/**
	 * Saves the file to server directory
	 * @param file
	 * @return true is success false if failed.
	 */
	public boolean processUpload(UploadInformationDTO uploadInformation);
	
	/**
	 * Adds more files to an existing application.
	 */
	public boolean uploadMoreImages(ApplicationDetailDTO additionalUpload);
	
	/**
	 * Upload follow up image and saves in our image repository.
	 */
	public boolean uploadFollowUpImage(FollowupDetailDTO followupappDetailsForm);
	
	/**
	 * Updates the whole application related images as complete submission so that
	 * it will now be available at data entry module.
	 */
	public boolean updateApplicationStatusAsComplete(String applicationCd);	
	
	/**
	 * Generates App No
	 * @return Application No.
	 */
	public String generateAppNo();
	
	/**
	 * Generates group sequence no for folder grouping
	 */
	public String generateRequestedNumType(String numType);
	
	/**
	 * persists image to disk and database
	 */
	public void saveImage(MultipartFile file,ApplicationImage imageDTO);
	
	/**
	 * creates new application with the new seq no
	 */
	public void createNewApplicationWithSeqNo(ApplicationSeqNo appSeqNo);
}
