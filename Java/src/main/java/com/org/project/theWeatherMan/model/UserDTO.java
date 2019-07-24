package com.org.project.theWeatherMan.model;

import java.io.Serializable;

import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;

/**
 * User DTO.
 *
 * @author abhishek.sisodiya
 * @since 03/07/2019.
 */

public class UserDTO extends JdkSerializationRedisSerializer
implements Serializable, Cloneable {
	private String username;
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}