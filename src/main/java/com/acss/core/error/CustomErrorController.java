package com.acss.core.error;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Throwables;

@Controller
class CustomErrorController {
	
	/**
	 * Display an error page, as defined in web.xml <code>custom-error</code> element.
	 */
	@RequestMapping("generalError")	
	public String generalError(HttpServletRequest request, HttpServletResponse response, Model model,RedirectAttributes ra) {
		// retrieve some useful information from the request
		Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
		Throwable throwable = (Throwable) request.getAttribute("javax.servlet.error.exception");
		// String servletName = (String) request.getAttribute("javax.servlet.error.servlet_name");
		String exceptionMessage = getExceptionMessage(throwable, statusCode);
		
		String requestUri = (String) request.getAttribute("javax.servlet.error.request_uri");
		if (requestUri == null) {
			requestUri = "Unknown";
		}
		
		String message = MessageFormat.format("{0} returned for {1} with message {2}", 
			statusCode, requestUri, exceptionMessage
		); 
		
		//Redirects to home in case of 404.
		if(statusCode.intValue() == HttpStatus.NOT_FOUND.value() ||
				//Redirects to home in case of 403.
				statusCode.intValue() == HttpStatus.FORBIDDEN.value()){
			return "redirect:/";
		}
		ra.addFlashAttribute("errorMessage",message);
		
		return "redirect:/error";
	}
	
	@RequestMapping("error")
	public String showErrorPage(Model model){
		return model.containsAttribute("message")?"error/general":"redirect:/";
	}
	
	private String getExceptionMessage(Throwable throwable, Integer statusCode) {
		if (throwable != null) {
			return Throwables.getRootCause(throwable).getMessage();
		}
		HttpStatus httpStatus = HttpStatus.valueOf(statusCode);
		return httpStatus.getReasonPhrase();
	}
}
