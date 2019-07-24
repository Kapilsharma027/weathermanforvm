package com.org.project.theWeatherMan.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.org.project.theWeatherMan.config.JwtTokenUtil;
import com.org.project.theWeatherMan.dao.UserDao;
import com.org.project.theWeatherMan.model.JwtRequest;
import com.org.project.theWeatherMan.model.Response;
import com.org.project.theWeatherMan.model.WeatherManException;
import com.org.project.theWeatherMan.services.JwtUserDetailsService;

import io.swagger.annotations.ApiOperation;

/**
 * This controller class handle request/response for login and register.
 *
 * @author abhishek.sisodiya
 * @since 03/07/2019.
 */

@RestController
public class JwtAuthenticationController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private UserDao userDao;

	@ApiOperation(value = "Authenticate User")
	@RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public Response createAuthenticationToken(@RequestBody JwtRequest authenticationRequest) throws Exception {

		authenticate(authenticationRequest.getUsername(), authenticationRequest.getPassword());

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());

		final String token = jwtTokenUtil.generateToken(userDetails);

		Map<String, Object> json = new HashMap<String, Object>();
		json.put("token", token);
		json.put("userId", userDao.findByUsername(authenticationRequest.getUsername()).getId());

		return new Response(HttpStatus.OK.value(), "login Successfully", json);
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new WeatherManException(HttpStatus.UNAUTHORIZED, "Invalid Username/Password");
		}
	}

}