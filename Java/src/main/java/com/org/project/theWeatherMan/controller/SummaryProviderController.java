package com.org.project.theWeatherMan.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.project.theWeatherMan.services.SummaryProviderService;
import com.org.project.theWeatherMan.util.CustomResponseEntity;

import io.swagger.annotations.ApiOperation;

/**
 * This controller class handle request/response for summary provider.
 *
 * @author abhishek.sisodiya
 * @since 03/07/2019.
 */

@RestController
public class SummaryProviderController {

	@Autowired
	private SummaryProviderService summaryProviderService;

	@ApiOperation(value = "Returns precise provider.")
	@RequestMapping(value = "/summaryprovider", method = RequestMethod.GET)
	public ResponseEntity createAuthenticationToken()
			throws Exception {
		return new ResponseEntity(
				new CustomResponseEntity().getResponseObject(summaryProviderService.summaryProvider()),
				HttpStatus.OK);
	}

}
