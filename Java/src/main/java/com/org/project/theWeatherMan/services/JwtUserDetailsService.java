package com.org.project.theWeatherMan.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.org.project.theWeatherMan.dao.UserDao;
import com.org.project.theWeatherMan.model.DAOUser;
import com.org.project.theWeatherMan.model.UserDTO;
import com.org.project.theWeatherMan.util.CacheManager;


/**
 * Repository for User Table.
 *
 * @author abhishek.sisodiya
 * @since 03/07/2019.
 */

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		DAOUser user = userDao.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + username);
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				new ArrayList<>());
	}

	public DAOUser findByUsername(String username) {
		DAOUser user = userDao.findByUsername(username);
		return user;
	}

	public DAOUser save(UserDTO user) {
		DAOUser newUser = new DAOUser();
		newUser.setUsername(user.getUsername());
		newUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		return userDao.save(newUser);
	}
	public void updatePassword(UserDTO user) {
	
		DAOUser daoUser = userDao.findByUsername(user.getUsername());
		daoUser.setPassword(bcryptEncoder.encode(user.getPassword()));
		daoUser.setUsername(user.getUsername());
		userDao.save(daoUser);
	}
}