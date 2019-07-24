package com.org.project.theWeatherMan.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.org.project.theWeatherMan.config.JwtTokenUtil;
import com.org.project.theWeatherMan.model.DAOUser;
import com.org.project.theWeatherMan.model.Response;
import com.org.project.theWeatherMan.model.UserDTO;
import com.org.project.theWeatherMan.model.WeatherManException;
import com.org.project.theWeatherMan.services.JwtUserDetailsService;
import com.org.project.theWeatherMan.services.MailContentBuilder;
import com.org.project.theWeatherMan.util.AppConstants;
import com.org.project.theWeatherMan.util.CacheManager;
import com.org.project.theWeatherMan.util.Encryptor;
import com.org.project.theWeatherMan.util.SendMail;

import io.swagger.annotations.ApiOperation;

@RestController
public class UserController {
	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private JwtUserDetailsService userDetailsService;

	@Autowired
	private SendMail sendMail;
	

	@ApiOperation(value = "Register User")
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public Response saveUser(@RequestBody UserDTO user) throws Exception {
		DAOUser user1 = userDetailsService.findByUsername(user.getUsername());

		if (user1 != null) {
			throw new WeatherManException(HttpStatus.CONFLICT, "Duplicate Username");
		}
		return new Response(HttpStatus.OK.value(), "User added Successfully", userDetailsService.save(user));
	}

	@RequestMapping(value = "/reset-password", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(value = "Change password of user")
	public Response resetPassword(@RequestBody UserDTO user, @RequestParam("userName") String userName) {
		// userService.setPassword(authDetails, false);
		UserDTO updateUser = new UserDTO();
		String userNameUpdate = Encryptor.decrypt(AppConstants.ENCRYPTION_KEY, AppConstants.ENCRYPTION_INIT_VECTOR,
				userName);
		updateUser.setUsername(userNameUpdate);
		updateUser.setPassword(user.getPassword());

		userDetailsService.updatePassword(updateUser);
		return new Response(HttpStatus.CREATED.value(), "Password reset successfully", null);
	}

	@RequestMapping(value = "/check-link-expiration", method = RequestMethod.GET)
	@ApiOperation(value = "Check Link Expiration")
	public Response isLinkExpire(@RequestParam("userName") String userName) {
		UserDTO user = (UserDTO) cacheManager.get(userName);
		if (user == null) {
			throw new WeatherManException(HttpStatus.UNAUTHORIZED, "Your Link is expired.");
		}
		return new Response(HttpStatus.OK.value(), "Ok", null);
	}

	@ApiOperation(value = "Forgot Password")
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.GET)
	public Response forgotPassword(@RequestParam("username") String username,
			@RequestHeader("Origin") String clientHostName) throws Exception {
		DAOUser user = userDetailsService.findByUsername(username);
		if (user == null) {
			throw new WeatherManException(HttpStatus.NOT_FOUND, username + " doesn't exists");
		}

		String userName = Encryptor.encrypt(AppConstants.ENCRYPTION_KEY, AppConstants.ENCRYPTION_INIT_VECTOR,
				user.getUsername());
		UserDTO userdto = new UserDTO();
		userdto.setUsername(user.getUsername());
		cacheManager.setlink(userName, userdto);
		try {
			userName = URLEncoder.encode(userName, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			throw new WeatherManException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
		}
		if (clientHostName == null) {
			sendMail.sendMail(user.getUsername(), "/#/verifyLink/?userName=" + userName);

		} else {
			sendMail.sendMail(user.getUsername(), clientHostName + "/#/verifyLink/?userName=" + userName);

		}
		return new Response(HttpStatus.OK.value(), "Activation link has been sent to registered email address.", null);
	}

}
