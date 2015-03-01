package com.acss.core.error;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Throwables;

/**
 * General error handler for the application.
 */
@ControllerAdvice
class ExceptionHandler {

	/**
	 * Handle exceptions thrown by handlers.
	 */
	@org.springframework.web.bind.annotation.ExceptionHandler(value = Exception.class)	
	public String exception(Exception exception, WebRequest request,RedirectAttributes ra) {
		
		ra.addFlashAttribute("message", Throwables.getRootCause(exception));
		ra.addFlashAttribute("detailedErrorMsg", ExceptionUtils.getStackTrace(exception));
		
		return "redirect:/error";
	}
}