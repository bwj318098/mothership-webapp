package com.acss.core.application;

import com.acss.core.followup.FollowupDetailDTO;


public interface HpsApplicationService {
	ApplicationDetailDTO getHpsApplication(String appNo);
	FollowupDetailDTO getAppDetailForFollowup(String appNo);
	boolean updateRequestedDocuments(FollowupDetailDTO followupappDetailsForm);
}
