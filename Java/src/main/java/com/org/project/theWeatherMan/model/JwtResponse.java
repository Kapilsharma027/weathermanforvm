package com.org.project.theWeatherMan.model;

import java.io.Serializable;

/**
 * Jwt response model.
 *
 * @author abhishek.sisodiya
 * @since 03/07/2019.
 */

public class JwtResponse implements Serializable {

	private static final long serialVersionUID = -8091879091924046844L;
	private final String jwttoken;

	public JwtResponse(String jwttoken) {
		this.jwttoken = jwttoken;
	}

	public String getToken() {
		return this.jwttoken;
	}
}