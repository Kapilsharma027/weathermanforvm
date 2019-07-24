package com.org.project.theWeatherMan.services;

import java.util.Map;

/**
 * WeatherService implements an application to define method which have business
 * logic and communicate with controller and weather data.
 * 
 * @author abhishek.sisodiya
 * @since 01/07/2019.
 */
public interface WeatherService {

	Map<String, Object> openweathermap(String lat, String longi) throws Exception;

	Map<String, Object> darksky(String lat, String longi) throws Exception;

	Map<String, Object> accuweather(String lat, String longi) throws Exception;

	Map<String, Object> weatherbit(String lat, String longi) throws Exception;

	Map<String, Object> weatherunlocked(String lat, String longi) throws Exception;

}