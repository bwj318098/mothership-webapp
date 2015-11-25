package com.acss.core.application;

import com.acss.core.followup.FollowupDetailDTO;


public interface HpsApplicationService {
	String getApplicationStatus(String appNo);
	ApplicationDetailDTO getHpsApplication(String appNo);
	FollowupDetailDTO getAppDetailForFollowup(String appNo);
	FollowupDetailDTO updateRequestedDocuments(FollowupDetailDTO followupappDetailsForm);
}
