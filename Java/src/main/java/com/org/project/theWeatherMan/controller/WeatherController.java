package com.org.project.theWeatherMan.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.org.project.theWeatherMan.services.WeatherService;
import com.org.project.theWeatherMan.util.CustomResponseEntity;

import io.swagger.annotations.ApiOperation;

/**
 * This controller class handle request and response for Weather.
 *
 * @author abhishek.sisodiya
 * @since 01/07/2019.
 */

@RestApiController("/getweather")
public class WeatherController extends APIController {

	@Autowired
	private WeatherService weatherService;
	
	@ApiOperation(value = "Get Weather Detail By www.openweathermap.org")
	@RequestMapping(value = "/openweathermap", method = RequestMethod.GET)
	public ResponseEntity openweathermap(@RequestParam String lat,@RequestParam String longi, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ResponseEntity(new CustomResponseEntity().getResponseObject(weatherService.openweathermap(lat, longi)),
				HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Get Weather Detail By www.darksky.net")
	@RequestMapping(value = "/darksky", method = RequestMethod.GET)
	public ResponseEntity darksky(@RequestParam String lat,@RequestParam String longi, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ResponseEntity(new CustomResponseEntity().getResponseObject(weatherService.darksky(lat, longi)),
				HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Get Weather Detail By www.accuweather.com")
	@RequestMapping(value = "/accuweather", method = RequestMethod.GET)
	public ResponseEntity accuweather(@RequestParam String lat,@RequestParam String longi, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ResponseEntity(new CustomResponseEntity().getResponseObject(weatherService.accuweather(lat, longi)),
				HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Get Weather Detail By www.weatherbit.io")
	@RequestMapping(value = "/weatherbit", method = RequestMethod.GET)
	public ResponseEntity weatherbit(@RequestParam String lat,@RequestParam String longi, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ResponseEntity(new CustomResponseEntity().getResponseObject(weatherService.weatherbit(lat, longi)),
				HttpStatus.OK);
	}
	
	
	@ApiOperation(value = "Get Weather Detail By www.weatherunlocked.io")
	@RequestMapping(value = "/weatherunlocked", method = RequestMethod.GET)
	public ResponseEntity weatherunlocked(@RequestParam String lat,@RequestParam String longi, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return new ResponseEntity(new CustomResponseEntity().getResponseObject(weatherService.weatherunlocked(lat, longi)),
				HttpStatus.OK);
	}
}