package com.acss.core.error;

import java.text.MessageFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.exception.ExceptionUtils;
import org.joda.time.DateTime;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.google.common.base.Throwables;
/**
 * General error handler for the application controllers.
 */
@ControllerAdvice
class GeneralExceptionHandler{
	
	/**
	 * Handle exceptions thrown by handlers.
	 */
    @ExceptionHandler(value = {Exception.class, RuntimeException.class})
    public String defaultErrorHandler(HttpServletRequest request,Exception e) {
        String exceptionMessage = getExceptionMessage(e);
		
		String requestUri = request.getRequestURL().toString();
		if (requestUri == null) {
			requestUri = "Unknown";
		}
		
		String message = MessageFormat.format("Sorry, Error encountered on {0} with message {1}. Please Contact System Administrator"
				,requestUri, exceptionMessage); 
		
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (outputFlashMap != null){
            outputFlashMap.put("myAttribute", true);
            outputFlashMap.put("datetime", new DateTime());
            outputFlashMap.put("exception", e);

            outputFlashMap.put("errorMessage",message);
            outputFlashMap.put("message", Throwables.getRootCause(e));
            outputFlashMap.put("detailedErrorMsg", ExceptionUtils.getStackTrace(e));
        }
        
        return "redirect:/error";
    }
    
    private String getExceptionMessage(Throwable throwable) {
		if (throwable != null) {
			return Throwables.getRootCause(throwable).getMessage();
		}else
			return "Message not available";
	}
    
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleError404(HttpServletRequest request, Exception e)   {
		String exceptionMessage = getExceptionMessage(e);

		String requestUri = request.getRequestURL().toString();
		if (requestUri == null) {
			requestUri = "Unknown";
		}
		String message = MessageFormat.format("Sorry, Error encountered on {0} with message {1}. Please Contact System Administrator"
				,requestUri, exceptionMessage); 
		
        FlashMap outputFlashMap = RequestContextUtils.getOutputFlashMap(request);
        if (outputFlashMap != null){
            outputFlashMap.put("myAttribute", true);
            outputFlashMap.put("datetime", new DateTime());
            outputFlashMap.put("exception", e);

            outputFlashMap.put("errorMessage",message);
            outputFlashMap.put("message", Throwables.getRootCause(e));
            outputFlashMap.put("detailedErrorMsg", ExceptionUtils.getStackTrace(e));
        }

		return "redirect:/error";
    }
}