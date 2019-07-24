package com.org.project.theWeatherMan.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.org.project.theWeatherMan.dao.ProviderDao;
import com.org.project.theWeatherMan.dao.VotingDao;
import com.org.project.theWeatherMan.model.DAOUser;
import com.org.project.theWeatherMan.services.VotingRepository;
import com.org.project.theWeatherMan.util.CustomResponseEntity;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

/**
 * This controller class handle request/response for login and register.
 *
 * @author abhishek.sisodiya
 * @since 03/07/2019.
 */

@RestController
public class UserVotingController {

	@Autowired
	private VotingRepository votingRepository;

	@ApiOperation(value = "User Vote for particular provider.", authorizations = {
			@Authorization(value = "Authorization") })
	@RequestMapping(value = "/uservote", method = RequestMethod.POST)
	public ResponseEntity createAuthenticationToken(@RequestBody Map<String, Object> payload) throws Exception {
		VotingDao voting = new VotingDao();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String date = dateFormat.format(Calendar.getInstance().getTime());
		voting.setDate(date);
		ProviderDao provider = new ProviderDao();
		provider.setId(Integer.parseInt(payload.get("providerId").toString()));
		voting.setProvider(provider);
		DAOUser user = new DAOUser();
		user.setId(Integer.parseInt(payload.get("userId").toString()));
		voting.setUser(user);
		try {
			votingRepository.save(voting);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity(new CustomResponseEntity().getResponseObject("Thanks for your feedback!"),
				HttpStatus.OK);
	}

}
