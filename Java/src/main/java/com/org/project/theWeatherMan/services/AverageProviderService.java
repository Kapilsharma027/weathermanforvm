package com.org.project.theWeatherMan.services;

import java.util.Map;

/**
 * AverageProviderService implements an application to define method which have
 * business logic and communicate with controller and summary data.
 * 
 * @author abhishek.sisodiya
 * @since 01/07/2019.
 */
public interface AverageProviderService {

	Map<String, Object> averageProvider(String latLong) throws Exception;
}