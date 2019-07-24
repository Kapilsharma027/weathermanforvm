package com.org.project.theWeatherMan.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.org.project.theWeatherMan.model.DAOUser;

/**
 * User interface.
 *
 * @author abhishek.sisodiya
 * @since 03/07/2019.
 */

@Repository
public interface UserDao extends CrudRepository<DAOUser, Integer> {
	
	DAOUser findByUsername(String username);
//@Query("")
//	List<VotingDao> findVotingById(@Param("parseInt")int parseInt);
	
}