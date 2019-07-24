package com.org.project.theWeatherMan.dao;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User entity model.
 *
 * @author abhishek.sisodiya
 * @since 03/07/2019.
 */

@Entity
@Table(name = "tbl_provider")
public class ProviderDao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;

	@OneToMany(mappedBy = "provider", fetch = FetchType.LAZY)
	public Set<VotingDao> votingDaos;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@Column(name = "providername", unique = true, nullable = false)
	public String providername;

	public String getProvider_name() {
		return providername;
	}

	public void setProvidername(String providername) {
		this.providername = providername;
	}

	public Set<VotingDao> getVotingDaos() {
		return votingDaos;
	}

	public void setVotingDaos(Set<VotingDao> votingDaos) {
		this.votingDaos = votingDaos;
	}

	public String getProvidername() {
		return providername;
	}

}
