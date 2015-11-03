package com.acss.core.dataentry;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * Restful implementation of saving data entry information.
 * @author gvargas
 *
 */
@Service
public class RSDataEntry implements DataEntryService{

	public boolean save(com.acss.core.model.dataentry.DataEntryDTO dataEntry) {
		RestTemplate rt = new RestTemplate();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<com.acss.core.model.dataentry.DataEntryDTO> requestEntity = new HttpEntity<com.acss.core.model.dataentry.DataEntryDTO>(dataEntry,headers);
		
		ResponseEntity<com.acss.core.model.dataentry.DataEntryDTO> res = rt.exchange("http://nhps.com:8084/dataentry",
				HttpMethod.POST,requestEntity, com.acss.core.model.dataentry.DataEntryDTO.class);
		
		
		return res==null?false:true;
	}

}
