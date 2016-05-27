package com.acss.core.application;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.ResponseEntity;

public interface LonService {
	
	 public ResponseEntity<byte[]> generateLon(String sysAppCd) throws IOException;
}
