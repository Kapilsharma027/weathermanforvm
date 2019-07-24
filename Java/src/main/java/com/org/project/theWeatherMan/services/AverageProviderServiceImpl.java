package com.org.project.theWeatherMan.services;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.project.theWeatherMan.util.CacheManager;

/**
 * AverageProviderServiceImpl is an implementation of interface
 * {@link AverageProviderService}. This service will be used to implement fetch
 * the average of all the providers.
 * 
 * @author abhishek.sisodiya
 * @since 01/07/2019.
 */

@Service("AverageProviderService")
public class AverageProviderServiceImpl implements AverageProviderService {

	@Autowired
	private CacheManager cacheManager;
	String MAX_DESC = "";
	String MAX_ICON = "";

	@Override
	public Map<String, Object> averageProvider(String latLong) throws Exception {
		Map<String, Object> json = new HashMap<String, Object>();

		double weatherunlockedWindspd_mph = 0, weatherbitWindSpeed = 0, accuweatherWindspeed = 0, darkskyWindSpeed = 0,
				accuweatherMinval = 0, accuweatherMaxval = 0, weatherunlockedTemp_c = 0, weatherunlockedFeelslike_c = 0,
				weatherunlockedHumid_pct = 0, weatherbitApp_temp = 0, weatherbitTemp = 0, weatherbitPressure = 0,
				openweathermapPressure = 0, openweathermapMin_temp = 0, openweathermapMax_temp = 0, darkskyHumidity = 0,
				darkskyPressure = 0, darkskyPrecipIntensity = 0, darkskyApparentTemperature = 0, darkskyTemperature = 0;
		String weatherunlockedWx_desc = "", darkskySummary = "", openweathermapDescription = "",
				weatherbitDescription = "", accuweatherLongPhrase = "";
		int darkskyPressureInt = 0, openweathermapHumidity = 0, weatherbitPrecip = 0,
				accuweatherPrecipitationProbability = 0, darkskyPrecipIntensityInt = 0, weatherbitTempInt = 0,
				weatherbitApp_tempInt = 0;
		double maxTemp = 0, minTemp = 0, pressure = 0, precip = 0, humid = 0, windSpeed = 0;
		List elements = new ArrayList<String>();
		List descVote = new ArrayList<String>();
		List iconVote = new ArrayList<String>();

		try {
			Map<String, Object> json1 = (Map<String, Object>) cacheManager.get(latLong, "openweathermap", Map.class)
					.get("openweathermap");

			Map<String, Object> openweathermap = (Map<String, Object>) json1.get("main");
			ArrayList<Map<String, Object>> openweathermap1 = (ArrayList<Map<String, Object>>) json1.get("weather");
			Map<String, Object> openweathermap2 = openweathermap1.get(0);
			openweathermapDescription = openweathermap2.get("main").toString();
			elements.add(openweathermapDescription);
			openweathermapMin_temp = (double) openweathermap.get("temp_min");
			if (openweathermapMin_temp != 0) {
				minTemp++;
			}
			openweathermapMax_temp = (double) openweathermap.get("temp_max");
			if (openweathermapMax_temp != 0) {
				maxTemp++;
			}
			openweathermapPressure = (double) openweathermap.get("pressure");
			if (openweathermapPressure != 0) {
				pressure++;
			}
			openweathermapHumidity = (Integer) openweathermap.get("humidity");
			if (openweathermapHumidity != 0) {
				humid++;
			}

		} catch (Exception e) {
			System.out.println("openweathermap " + e);
		}

		try {
			Map<String, Object> json2 = (Map<String, Object>) cacheManager.get(latLong, "darksky", Map.class)
					.get("darksky");
			darkskyWindSpeed = (double) json2.get("windSpeed");
			if (darkskyWindSpeed != 0) {
				windSpeed++;
			}
			darkskyHumidity = (double) json2.get("humidity");
			if (darkskyHumidity != 0) {
				humid++;
			}
			try {
				darkskyPressure = (double) json2.get("pressure");
			} catch (Exception e) {
				darkskyPressureInt = (int) json2.get("pressure");
			}
			if (darkskyPressure != 0 || darkskyPressureInt != 0) {
				pressure++;
			}
			darkskySummary = (String) json2.get("summary");
			elements.add(darkskySummary);
			try {
				darkskyPrecipIntensity = (double) json2.get("precipIntensity");
			} catch (Exception e) {
				darkskyPrecipIntensityInt = (int) json2.get("precipIntensity");
			}
			if (darkskyPrecipIntensity != 0 || darkskyPrecipIntensityInt != 0) {
				precip++;
			}
			darkskyApparentTemperature = (double) json2.get("apparentTemperature"); // max
			darkskyApparentTemperature = ((darkskyApparentTemperature - 32) * 5) / 9;
			if (darkskyApparentTemperature != 0) {
				maxTemp++;
			}
			darkskyTemperature = (double) json2.get("temperature"); // min
			darkskyTemperature = ((darkskyTemperature - 32) * 5) / 9;
			if (darkskyTemperature != 0) {
				minTemp++;
			}
		} catch (Exception e) {
			System.out.println("darksky " + e);
		}

		try {
			Map<String, Object> json3 = (Map<String, Object>) cacheManager.get(latLong, "accuweather", Map.class)
					.get("accuweather");

			Map<String, Object> accuweather1 = (Map<String, Object>) json3.get("Temperature");
			Map<String, Object> accuweather2 = (Map<String, Object>) accuweather1.get("Minimum");
			accuweatherMinval = (double) accuweather2.get("Value");
			accuweatherMinval = ((accuweatherMinval - 32) * 5) / 9;
			if (accuweatherMinval != 0) {
				minTemp++;
			}
			Map<String, Object> accuweather3 = (Map<String, Object>) accuweather1.get("Maximum");
			accuweatherMaxval = (double) accuweather3.get("Value");
			accuweatherMaxval = ((accuweatherMaxval - 32) * 5) / 9;
			if (accuweatherMaxval != 0) {
				maxTemp++;
			}
			Map<String, Object> accuweather4 = (Map<String, Object>) json3.get("Day");
			Map<String, Object> accuweather5 = (Map<String, Object>) accuweather4.get("Wind");
			Map<String, Object> accuweather6 = (Map<String, Object>) accuweather4.get("Speed");
			accuweatherWindspeed = (double) accuweather6.get("Value");
			if (accuweatherWindspeed != 0) {
				windSpeed++;
			}
			accuweatherPrecipitationProbability = (int) accuweather4.get("PrecipitationProbability");
			if (accuweatherPrecipitationProbability != 0) {
				precip++;
			}
			accuweatherLongPhrase = (String) accuweather4.get("LongPhrase");
			elements.add(accuweatherLongPhrase);

		} catch (Exception e) {
			System.out.println("accuweather " + e);
		}

		try {
			Map<String, Object> json4 = (Map<String, Object>) cacheManager.get(latLong, "weatherbit", Map.class)
					.get("weatherbit");
			weatherbitWindSpeed = (double) json4.get("wind_spd");
			if (weatherbitWindSpeed != 0) {
				windSpeed++;
			}
			weatherbitPressure = (double) json4.get("pres");
			if (weatherbitPressure != 0) {
				pressure++;
			}
			weatherbitPrecip = (Integer) json4.get("precip");
			if (weatherbitPrecip != 0) {
				precip++;
			}
			try {
				weatherbitTemp = (double) json4.get("temp"); // min
			} catch (Exception e) {
				weatherbitTempInt = (int) json4.get("temp"); // min
			}
			if (weatherbitTemp != 0 || weatherbitTempInt != 0) {
				minTemp++;
			}
			try {
				weatherbitApp_temp = (double) json4.get("app_temp"); // max
			} catch (Exception e) {
				weatherbitApp_tempInt = (int) json4.get("app_temp"); // max
			}
			if (weatherbitApp_temp != 0) {
				maxTemp++;
			}
			Map<String, Object> weatherbit1 = (Map<String, Object>) json4.get("weather");
			weatherbitDescription = (String) weatherbit1.get("description");
			elements.add(weatherbitDescription);

		} catch (Exception e) {
			System.out.println("weatherbit " + e);
		}

		try {
			Map<String, Object> json5 = (Map<String, Object>) cacheManager.get(latLong, "weatherunlocked", Map.class)
					.get("weatherunlocked");
			weatherunlockedWindspd_mph = (double) json5.get("windspd_mph");
			if (weatherunlockedWindspd_mph != 0) {
				windSpeed++;
			}
			weatherunlockedHumid_pct = (double) json5.get("humid_pct");
			if (weatherunlockedHumid_pct != 0) {
				humid++;
			}
			weatherunlockedFeelslike_c = (double) json5.get("feelslike_c"); // max
			if (weatherunlockedFeelslike_c != 0) {
				maxTemp++;
			}
			weatherunlockedTemp_c = (double) json5.get("temp_c"); // min
			if (weatherunlockedTemp_c != 0) {
				minTemp++;
			}
			weatherunlockedWx_desc = (String) json5.get("wx_desc");
			elements.add(weatherunlockedWx_desc);

		} catch (Exception e) {
			System.out.println("weatherunlocked" + e);
		}

		double min_temp = ((openweathermapMin_temp - 273) + darkskyTemperature + weatherbitTemp + weatherunlockedTemp_c
				+ accuweatherMinval) / minTemp;
		double max_temp = ((openweathermapMax_temp - 273) + darkskyApparentTemperature + weatherbitApp_temp
				+ weatherunlockedFeelslike_c + accuweatherMaxval) / maxTemp;
		double pressures = (openweathermapPressure + darkskyPressure + weatherbitPressure) / pressure;
		double precips = (darkskyPrecipIntensity + weatherbitPrecip) / precip;
		double humidity = (openweathermapHumidity + (darkskyHumidity * 100) + weatherunlockedHumid_pct) / humid;
		double windSpeeds = (weatherunlockedWindspd_mph + weatherbitWindSpeed + accuweatherWindspeed + darkskyWindSpeed)
				/ windSpeed;

		for (Object word : elements) {
			if (Pattern.compile(Pattern.quote("rain"), Pattern.CASE_INSENSITIVE).matcher(word.toString()).find()) {
				descVote.add("Rainy");
				iconVote.add("HeavyRain.png");
			}
			if (Pattern.compile(Pattern.quote("cloud"), Pattern.CASE_INSENSITIVE).matcher(word.toString()).find()) {
				descVote.add("Cloudy");
				iconVote.add("Cloudy.png");
			}
			if (Pattern.compile(Pattern.quote("overcast"), Pattern.CASE_INSENSITIVE).matcher(word.toString()).find()) {
				descVote.add("Overcast");
				iconVote.add("overcast.png");
			}
//			if (Pattern.compile(Pattern.quote("mostly"), Pattern.CASE_INSENSITIVE).matcher(word.toString()).find())
//				descVote.add("mostly");
//			iconVote.add("mostly");
			if (Pattern.compile(Pattern.quote("storm"), Pattern.CASE_INSENSITIVE).matcher(word.toString()).find()) {
				descVote.add("Rain & Thunder");
				iconVote.add("CloudRainThunder.png");
			}
			if (Pattern.compile(Pattern.quote("snow"), Pattern.CASE_INSENSITIVE).matcher(word.toString()).find()) {
				descVote.add("Snowy");
				iconVote.add("HeavySnow.png");
			}
			if (Pattern.compile(Pattern.quote("sun"), Pattern.CASE_INSENSITIVE).matcher(word.toString()).find()) {
				descVote.add("Sunny");
				iconVote.add("Sunny.png");
			}
		}

		MAX_DESC = maxVote(descVote);
		MAX_ICON = maxVote(iconVote);

		if (min_temp > 0 && min_temp < 50) {
			json.put("min_temp", min_temp);
		}
		if (max_temp > 0 && max_temp < 60) {
			json.put("max_temp", max_temp);
		}
		if (pressures > 0) {
			json.put("pressure", pressures);
		}
		if (precips > 0) {
			json.put("precip", precips);
		}
		if (humidity > 0) {
			json.put("humidity", humidity);
		}
		if (windSpeed > 0) {
			json.put("windSpeed", windSpeeds);
		}
		if (!MAX_DESC.equals("")) {
			json.put("desc", MAX_DESC);
		}
		if (!MAX_ICON.equals("")) {
			json.put("icon", MAX_ICON);
		}

		return json;
	}

	private String maxVote(List list) {
		int maxCounter = 0;
		String ret = "";
		for (int index = 0; index < list.size(); index++) {
			int counter = 1;
			for (int innerIndex = index + 1; innerIndex < list.size(); innerIndex++) {
				if (list.get(index) == list.get(innerIndex)) {
					counter++;
				}
			}
			if (maxCounter < counter) {
				maxCounter = counter;
				ret = list.get(index).toString();
			}
		}
		return ret;
	}
}
