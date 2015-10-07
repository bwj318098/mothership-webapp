package com.acss.core.merchantupload;

import com.acss.core.application.ApplicationDetailDTO;
import com.acss.core.followup.FollowupDetailDTO;


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
}
