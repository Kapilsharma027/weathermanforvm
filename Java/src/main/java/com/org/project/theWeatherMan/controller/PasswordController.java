package com.org.project.theWeatherMan.controller;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.org.project.theWeatherMan.model.DAOUser;
import com.org.project.theWeatherMan.model.Response;
import com.org.project.theWeatherMan.services.EmailService;
import com.org.project.theWeatherMan.services.JwtUserDetailsService;
import com.org.project.theWeatherMan.util.CacheManager;

@RestController
public class PasswordController {

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private EmailService emailService;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	// Process form submission from forgotPassword page
	@RequestMapping(value = "/forgot", method = RequestMethod.POST)
	public Response processForgotPasswordForm(@RequestParam("email") String userEmail,
			HttpServletRequest request) throws Exception {

		// Lookup user in database by e-mail
		try {
			DAOUser user = jwtUserDetailsService.findByUsername(userEmail);
		} catch (Exception e) {
			System.out.println("User not found " + e);
			return new Response(HttpStatus.OK.value(), "User not found", null);
		}

		// Generate random 36-character string token for reset password
//			User user = optional.get();
//			user.setResetToken(UUID.randomUUID().toString());

		// Save token to database
//			userService.saveUser(user);

//		cacheManager.put(userEmail, UUID.randomUUID().toString());

		String appUrl = request.getScheme() + "://" + request.getServerName();

		// Email message
		SimpleMailMessage passwordResetEmail = new SimpleMailMessage();
		passwordResetEmail.setFrom("abhishek.sisodiya@diaspark.com");
		passwordResetEmail.setTo(userEmail);
		passwordResetEmail.setSubject("Password Reset Request");
		passwordResetEmail.setText("To reset your password, click the link below:\n" + appUrl + "/reset?token="
				+ cacheManager.get(userEmail));

		emailService.sendEmail(passwordResetEmail);

		return new Response(HttpStatus.OK.value(), "Mail has been sent to your registered Email Id", null);

	}

}
