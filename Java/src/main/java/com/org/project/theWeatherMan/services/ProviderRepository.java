package com.org.project.theWeatherMan.services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.org.project.theWeatherMan.dao.ProviderDao;

/**
 * User interface.
 *
 * @author abhishek.sisodiya
 * @since 03/07/2019.
 */

@Repository
public interface ProviderRepository  extends CrudRepository<ProviderDao, Integer> {

	ProviderDao findByProvidername(String name);
	}