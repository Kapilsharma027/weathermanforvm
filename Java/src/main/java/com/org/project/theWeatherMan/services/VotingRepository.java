package com.org.project.theWeatherMan.services;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.org.project.theWeatherMan.dao.VotingDao;

/**
 * User interface.
 *
 * @author abhishek.sisodiya
 * @since 03/07/2019.
 */

@Repository
public interface VotingRepository extends CrudRepository<VotingDao, Integer> {
	List<VotingDao> findByDate(String date);
}