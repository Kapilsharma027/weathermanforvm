package com.org.project.theWeatherMan.services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
//import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.project.theWeatherMan.dao.VotingDao;
import com.org.project.theWeatherMan.util.CacheManager;

/**
 * SummaryProviderServiceImpl is an implementation of interface
 * {@link SummaryProviderService}. This service will be used to implement fetch
 * the best provider.
 * 
 * @author abhishek.sisodiya
 * @since 01/07/2019.
 */

@Service("SummaryProviderService")
public class SummaryProviderServiceImpl implements SummaryProviderService {

	@Autowired
	private VotingRepository votingRepository;

	@Autowired
	private CacheManager cacheManager;

	private int MAX_PROVIDER_ID = 0;

	public Map<String, Object> summaryProvider() throws Exception {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String previousYear = dateFormat.format(getPreviousYear());
		String yesterday = dateFormat.format(yesterday());
		Map<String, Object> json = new HashMap<String, Object>();
		Map<String, Object> fromCache = cacheManager.get("summaryProvider", "FAV_PROVIDER_ID", Map.class);
		if (fromCache != null) {
			System.out.println("!!!!!!!!!!summaryProvider Return from cache!!!!!!!!!!!");
			return fromCache;
		} else {
			try {
				List<VotingDao> votes = votingRepository.findByDate(previousYear);
				if (votes.size() > 0) {
					favProvider(votes);
				} else {
					List<VotingDao> yesterdayVotes = votingRepository.findByDate(yesterday);
					if (yesterdayVotes.size() > 0) {
						favProvider(yesterdayVotes);
					}
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			json.put("FAV_PROVIDER_ID", MAX_PROVIDER_ID);
			cacheManager.put("summaryProvider", "FAV_PROVIDER_ID", json);
			cacheManager.expire("summaryProvider");
		}
		Map<String, Object> fromCacheAfterAdd = cacheManager.get("summaryProvider", "FAV_PROVIDER_ID", Map.class);
		return fromCacheAfterAdd;
	}

	private Date yesterday() {
		final Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DATE, -1);
		return cal.getTime();
	}

	private static Date getPreviousYear() {
		Calendar prevYear = Calendar.getInstance();
		prevYear.add(Calendar.YEAR, -1);
		return prevYear.getTime();
	}

	private int favProvider(List<VotingDao> votes) {
		List<Integer> providerIDS = new ArrayList<Integer>();
		for (VotingDao vote : votes) {
			providerIDS.add(vote.getProvider().getId());
		}
		int maxCounter = 0;
		for (int index = 0; index < providerIDS.size(); index++) {
			int counter = 1;
			for (int innerIndex = index + 1; innerIndex < providerIDS.size(); innerIndex++) {
				if (providerIDS.get(index) == providerIDS.get(innerIndex)) {
					counter++;
				}
			}
			if (maxCounter < counter) {
				maxCounter = counter;
				MAX_PROVIDER_ID = providerIDS.get(index);
			}
		}
		return MAX_PROVIDER_ID;
	}

}
