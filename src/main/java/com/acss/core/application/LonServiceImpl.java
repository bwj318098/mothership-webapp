package com.acss.core.application;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class LonServiceImpl implements LonService {
	
	@Autowired
	private final static String LON_ENDPOINT = "rs.reports.lon.url";
	
	@Autowired
	private Environment env;
	
	public ResponseEntity<byte[]> generateLon(String sysAppCd) throws IOException {
		
		String uri = MessageFormat.format(env.getProperty(LON_ENDPOINT), sysAppCd);
		
		List<HttpMessageConverter<?>> messageConverters = new ArrayList<HttpMessageConverter<?>>();
		messageConverters.add(new ByteArrayHttpMessageConverter());
		
		RestTemplate restTemplate = new RestTemplate(messageConverters);

		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(headers);
		
		ResponseEntity<byte[]> response = restTemplate.exchange(
				uri,
				HttpMethod.GET, entity, byte[].class);
		
		return response;
	}

}
