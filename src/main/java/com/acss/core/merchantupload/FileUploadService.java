package com.acss.core.merchantupload;

import com.acss.core.search.ApplicationDetailDTO;


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
	 * Generates App No
	 * @return Application No.
	 */
	public String generateAppNo();
}
