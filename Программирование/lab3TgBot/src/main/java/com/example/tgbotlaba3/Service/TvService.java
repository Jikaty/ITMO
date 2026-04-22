package com.example.tgbotlaba3.Service;

import com.example.tgbotlaba3.MainClasses.TV;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class TvService {
	private final Map<Long, String> states = new HashMap<>();
	private final Map<Long, String> tempNames = new HashMap<>();
	private final Map<Long, TV> userTvs = new HashMap<>();

	public void setState(Long chatId, String state) {
		states.put(chatId, state);
	}

	public String getState(Long chatId) {
		return states.getOrDefault(chatId, "NONE");
	}

	public void saveName(Long chatId, String name) {
		tempNames.put(chatId, name);
	}

	public String getName(Long chatId) {
		return tempNames.get(chatId);
	}

	public void saveFinalTv(Long chatId, TV tv) {
		userTvs.put(chatId, tv);
	}

	public TV getTv(Long chatId) {
		return userTvs.get(chatId);
	}
}
