package com.org.project.theWeatherMan.model;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.org.project.theWeatherMan.dao.VotingDao;

/**
 * User entity model.
 *
 * @author abhishek.sisodiya
 * @since 03/07/2019.
 */

@Entity
@Table(name = "user")
public class DAOUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Set<VotingDao> votingDaos;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "username", unique = true, nullable = false)
	private String username;
	@Column(name = "password", nullable = false)
	@JsonIgnore
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

	public Set<VotingDao> getVotingDaos() {
		return votingDaos;
	}

	public void setVotingDaos(Set<VotingDao> votingDaos) {
		this.votingDaos = votingDaos;
	}

}