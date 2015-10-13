package com.acss.core.followup;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.acss.core.application.ApplicationDetailDTO;
import com.acss.core.merchantupload.validator.FollowupDocumentData;
import com.acss.core.merchantupload.validator.NotInValidImageFile;
import com.acss.core.merchantupload.validator.UploadInformationData;

public class FollowupDetailDTO extends ApplicationDetailDTO{
	
	private List<RequestedDocumentDTO> requestedDocuments;
	
	@NotInValidImageFile(message="upload.followup.error",groups = {UploadInformationData.class,FollowupDocumentData.class})
	private MultipartFile followupImage;
	
	private RequestedDocumentDTO reqDocumentForUpdate;
	
	public RequestedDocumentDTO getReqDocumentForUpdate() {
		return reqDocumentForUpdate;
	}

	public void setReqDocumentForUpdate(RequestedDocumentDTO reqDocumentForUpdate) {
		this.reqDocumentForUpdate = reqDocumentForUpdate;
	}

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
