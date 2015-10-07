package com.acss.core.followup;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.acss.core.application.ApplicationDetailDTO;
import com.acss.core.merchantupload.validator.NotInValidImageFile;
import com.acss.core.merchantupload.validator.UploadInformationData;

public class FollowupDetailDTO extends ApplicationDetailDTO{
	
	private List<RequestedDocumentDTO> requestedDocuments;
	
	@NotInValidImageFile(groups = UploadInformationData.class)
	private MultipartFile followupImage;
	
	public List<RequestedDocumentDTO> getRequestedDocuments() {
		return requestedDocuments;
	}

	public void setRequestedDocuments(List<RequestedDocumentDTO> requestedDocuments) {
		this.requestedDocuments = requestedDocuments;
	}

	public MultipartFile getFollowupImage() {
		return followupImage;
	}

	public void setFollowupImage(MultipartFile followupImage) {
		this.followupImage = followupImage;
	}
	
	
}
