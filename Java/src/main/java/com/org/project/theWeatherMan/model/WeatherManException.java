package com.org.project.theWeatherMan.model;

import org.springframework.http.HttpStatus;

public class WeatherManException extends RuntimeException{
	/**
	   * Status Code.
	   */
	  private HttpStatus httpStatus;
	  /**
	   * Response message.
	   */
	  private String message;

	  /** Response Object. */
	  private Object object;
	  

	  public WeatherManException(HttpStatus httpStatus) {
			super();
			this.httpStatus = httpStatus;
		}
	  
	  public WeatherManException(HttpStatus httpStatus, String message) {
			super();
			this.httpStatus = httpStatus;
			this.message = message;
		}
	  
	  public WeatherManException(HttpStatus httpStatus, String message, Object object) {
			super();
			this.httpStatus = httpStatus;
			this.message = message;
			this.object = object;
		}
	  
	  public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Object getObject() {
		return object;
	}

	public void setObject(Object object) {
		this.object = object;
	}

	
}
