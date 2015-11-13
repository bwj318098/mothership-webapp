package com.acss.core.error;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.base.Throwables;

/**
 * General error handler for the application.
 */
@EnableWebMvc
@ControllerAdvice
class GeneralExceptionHandler extends ResponseEntityExceptionHandler{

	/**
	 * Handle exceptions thrown by handlers.
	 */
	@ExceptionHandler(Throwable.class)
	public String exception(Exception exception, WebRequest request,RedirectAttributes ra) {
		
		ra.addFlashAttribute("message", Throwables.getRootCause(exception));
		ra.addFlashAttribute("detailedErrorMsg", ExceptionUtils.getStackTrace(exception));
		
		return "redirect:/error";
	}
}