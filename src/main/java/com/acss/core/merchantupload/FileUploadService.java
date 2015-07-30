package com.acss.core.merchantupload;


public interface FileUploadService {
	/**
	 * Saves the file to server directory
	 * @param file
	 * @return true is success false if failed.
	 */
	public boolean processUpload(UploadInformation uploadInformation);
	/**
	 * Generates App No
	 * @return Application No.
	 */
	public String generateAppNo();
}
