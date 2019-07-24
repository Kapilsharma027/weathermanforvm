package com.org.project.theWeatherMan.services;

import java.util.Map;

/**
 * SummaryProviderService implements an application to define method which have
 * business logic and communicate with controller and summary data.
 * 
 * @author abhishek.sisodiya
 * @since 01/07/2019.
 */
public interface SummaryProviderService {

	Map<String, Object> summaryProvider() throws Exception;
}
