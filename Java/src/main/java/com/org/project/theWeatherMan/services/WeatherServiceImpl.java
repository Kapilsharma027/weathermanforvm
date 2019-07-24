package com.org.project.theWeatherMan.services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.org.project.theWeatherMan.dao.ProviderDao;
import com.org.project.theWeatherMan.util.CacheManager;

/**
 * WeatherServiceImpl is an implementation of interface {@link WeatherService}.
 * This service will be used to implement fetch weather.
 * 
 * @author abhishek.sisodiya
 * @since 01/07/2019.
 */

@Service("WeatherService")
public class WeatherServiceImpl implements WeatherService {

	@Autowired
	private CacheManager cacheManager;

	@Autowired
	private ProviderRepository providerRepository;

	@Override
	public Map<String, Object> openweathermap(String lat, String longi) throws Exception {
		Map<String, Object> json3 = cacheManager.get(lat + longi, "openweathermap", Map.class);

		System.out.println("!!!!!!!!!json3!!!!!!!!" + json3);
		if (json3 != null) {
			System.out.println("!!!!!!!!!!openweathermap Return from cache!!!!!!!!!!!");
			return json3;
		} else {
			Map<String, Object> json = new HashMap<String, Object>();

// GET WEATHER DATA FROM openweathermap.org
			String url = String.format(
					"http://api.openweathermap.org/data/2.5/forecast?id=524901&APPID=2f2cb44826b399a6788b437ec8a3b430&lat=%s&lon=%s",
					lat, longi);
			RestTemplate restTemplate = new RestTemplate();
			try {
				ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
				List<LinkedHashMap<String, Object>> getList = ((List<LinkedHashMap<String, Object>>) ((LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap>>) response
						.getBody()).get("list"));
				LinkedHashMap<String, Object> getList1 = getList.get(0);
				Map<String, Object> json1 = new HashMap<String, Object>();
				json1.put("id", providerRepository.findByProvidername("openweathermap").getId());
				json1.put("main", getList1.get("main"));
				json1.put("weather", getList1.get("weather"));
				json.put("openweathermap", json1);
				cacheManager.put(lat + longi, "openweathermap", json);
				cacheManager.expire(lat + longi);
			} catch (Exception e) {
				System.out.println("openweathermap " + e);
				json.put("openweathermap", "No data available at this moment.");
			}

			return json;
		}
	}

	@Override
	public Map<String, Object> darksky(String lat, String longi) throws Exception {
		Map<String, Object> json3 = cacheManager.get(lat + longi, "darksky", Map.class);
		System.out.println("!!!!!!!!!json3!!!!!!!!" + json3);
		if (json3 != null) {
			System.out.println("!!!!!!!!!!darksky Return from cache!!!!!!!!!!!");
			return json3;
		} else {
			Map<String, Object> json = new HashMap<String, Object>();
			RestTemplate restTemplate = new RestTemplate();
			String url = String.format("https://api.darksky.net/forecast/a7b0d6dc62af18119ab48bef923726a7/%s,%s", lat,
					longi);
			try {
				ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);

				LinkedHashMap<String, Object> getList = (LinkedHashMap<String, Object>) response.getBody();
				LinkedHashMap<String, Object> getCurrent = (LinkedHashMap<String, Object>) getList.get("currently");
				getCurrent.put("id", providerRepository.findByProvidername("darksky").getId());
				json.put("darksky", getCurrent);
				System.out.println("!!!!!!!!!!!darksky Not Return from cache!!!!!!!!!!!!");
				cacheManager.put(lat + longi, "darksky", json);
				cacheManager.expire(lat + longi);
// cacheManager.remove("darksky");
			} catch (Exception e) {
				System.out.println("darksky " + e);
				json.put("darksky", "No data available at this moment.");
			}
			return json;
		}
	}

	@Override
	public Map<String, Object> accuweather(String lat, String longi) throws Exception {
		Map<String, Object> json3 = cacheManager.get(lat + longi, "accuweather", Map.class);
		System.out.println("!!!!!!!!!json3!!!!!!!!" + json3);
		if (json3 != null) {
			System.out.println("!!!!!!!!!!accuweather Return from cache!!!!!!!!!!!");
			return json3;
		} else {
			String url = "http://dataservice.accuweather.com/locations/v1/cities/search?apikey=JUutZdJ8j0EwP9APDW3Aon7uWHRsAVvG&q="
					+ lat + "," + longi;
			RestTemplate restTemplate = new RestTemplate();
			restTemplate = new RestTemplate();
			Map<String, Object> json = new HashMap<String, Object>();
			try {
				List<Object> result1 = restTemplate.getForObject(url, List.class);
				LinkedHashMap<String, List<String>> hMap = (LinkedHashMap<String, List<String>>) result1.get(0);
				List<List<String>> l = new ArrayList<List<String>>(hMap.values());
				url = String.format(
						"http://dataservice.accuweather.com/forecasts/v1/daily/1day/%s?apikey=JUutZdJ8j0EwP9APDW3Aon7uWHRsAVvG&details=true",
						l.get(1));

				ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
				List<LinkedHashMap<String, Object>> getList = ((List<LinkedHashMap<String, Object>>) ((LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap>>) response
						.getBody()).get("DailyForecasts"));
				LinkedHashMap<String, Object> getList1 = getList.get(0);
				Map<String, Object> json1 = new HashMap<String, Object>();
				json1.put("Temperature", getList1.get("Temperature"));
				json1.put("Day", getList1.get("Day"));
				json1.put("Night", getList1.get("Night"));
				json1.put("id", providerRepository.findByProvidername("accuweather").getId());
				json.put("accuweather", json1);
				System.out.println("!!!!!!!!!!!accuweather Not Return from cache!!!!!!!!!!!!");
				cacheManager.put(lat + longi, "accuweather", json);
				cacheManager.expire(lat + longi);
// cacheManager.remove("accuweather");
			} catch (Exception e) {
				System.out.println("accuweather " + e);
				json.put("accuweather", "No data available at this moment.");
			}
			return json;
		}
	}

	@Override
	public Map<String, Object> weatherbit(String lat, String longi) throws Exception {
		Map<String, Object> json3 = cacheManager.get(lat + longi, "weatherbit", Map.class);
		System.out.println("!!!!!!!!!json3!!!!!!!!" + json3);
		if (json3 != null) {
			System.out.println("!!!!!!!!!!weatherbit Return from cache!!!!!!!!!!!");
			return json3;
		} else {
			String url = String.format(
					"https://api.weatherbit.io/v2.0/current?key=b82d5c821c9441f6af27d768e0f8e309&lat=%s&lon=%s", lat,
					longi);
			RestTemplate restTemplate = new RestTemplate();
			Map<String, Object> json = new HashMap<String, Object>();
			try {
				ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
				List<LinkedHashMap<String, Object>> getList = ((List<LinkedHashMap<String, Object>>) ((LinkedHashMap<String, LinkedHashMap<String, LinkedHashMap>>) response
						.getBody()).get("data"));
				LinkedHashMap<String, Object> getList1 = getList.get(0);
				getList1.put("id", providerRepository.findByProvidername("weatherbit").getId());
				json.put("weatherbit", getList1);
				System.out.println("!!!!!!!!!!!weatherbit Not Return from cache!!!!!!!!!!!!");
				cacheManager.put(lat + longi, "weatherbit", json);
				cacheManager.expire(lat + longi);
// cacheManager.remove("weatherbit");
			} catch (Exception e) {
				System.out.println("weatherbit " + e);
				json.put("weatherbit", "No data available at this moment.");
			}
			return json;
		}
	}

	@Override
	public Map<String, Object> weatherunlocked(String lat, String longi) throws Exception {
		Map<String, Object> json3 = cacheManager.get(lat + longi, "weatherunlocked", Map.class);
		System.out.println("!!!!!!!!!json3!!!!!!!!" + json3);
		if (json3 != null) {
			System.out.println("!!!!!!!!!!weatherunlocked Return from cache!!!!!!!!!!!");
			return json3;
		} else {
			String url = String.format("http://api.weatherunlocked.com/api/current/" + lat + "," + longi
					+ "?app_id=e88ffa1c&app_key=7b1aedfa38c380ec084271d6b174e180");
			RestTemplate restTemplate = new RestTemplate();
			Map<String, Object> json = new HashMap<String, Object>();
			try {
				ResponseEntity<Object> response = restTemplate.getForEntity(url, Object.class);
				response = restTemplate.getForEntity(url, Object.class);
				json.put("id", providerRepository.findByProvidername("weatherunlocked").getId());
				json.put("weatherunlocked", response.getBody());
				System.out.println("!!!!!!!!!!!weatherunlocked Not Return from cache!!!!!!!!!!!!");
				cacheManager.put(lat + longi, "weatherunlocked", json);
				cacheManager.expire(lat + longi);
// cacheManager.remove("weatherunlocked");
			} catch (Exception e) {
				System.out.println("weatherunlocked " + e);
				json.put("weatherunlocked", "No data available at this moment.");
			}
			return json;
		}
	}

}
