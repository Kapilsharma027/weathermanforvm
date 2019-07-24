package com.org.project.theWeatherMan.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.org.project.theWeatherMan.model.WeatherManException;


@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	 @ExceptionHandler(value = { WeatherManException.class })
	  protected ResponseEntity<com.org.project.theWeatherMan.model.Response> handleCommonException(final WeatherManException ex,
	      final WebRequest request) {
	    if (ex.getObject() == null) {
	      return new ResponseEntity<com.org.project.theWeatherMan.model.Response>(
	          createErrorResponse(ex.getHttpStatus().value(), ex.getMessage(), null),
	          ex.getHttpStatus());
	    } else {
	      return new ResponseEntity<com.org.project.theWeatherMan.model.Response>(
	          createErrorResponse(ex.getHttpStatus().value(), ex.getMessage(), ex.getObject()),
	          ex.getHttpStatus());
	    }

	  }
	 
	 
	  @ExceptionHandler(value = { Exception.class })
	  protected ResponseEntity<com.org.project.theWeatherMan.model.Response> handleGlobalException(final Exception ex,
	      final WebRequest request) {
	    ex.printStackTrace();
	    return new ResponseEntity<com.org.project.theWeatherMan.model.Response>(
	        createErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), null),
	        HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	  
	  
	 
	 private com.org.project.theWeatherMan.model.Response createErrorResponse(int statusCode, String message, Object data) {
		    return new com.org.project.theWeatherMan.model.Response(statusCode, message, data);

		  }
}
