package com.example.tgbotlaba3.Service;

import com.example.tgbotlaba3.MainClasses.Entity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class EntityService {
	private final Map<Long, String> userState = new HashMap<>();
	private final Map<Long, List<Entity>> userEntities = new HashMap<>();
	private final Map<Long, String> partTimeName = new HashMap<>();

	public void setState(Long chatId, String state) {
		userState.put(chatId, state);
	}
	public String getState(Long chatId) {
		return userState.getOrDefault(chatId, "NONE");
	}

	public void setPartTimeName(Long chatId, String name) {
		partTimeName.put(chatId, name);
	}
	public String getPartTimeName(Long chatId) {
		return partTimeName.get(chatId);
	}

	public void addEntity(Long chatId, Entity entity) {
		userEntities.computeIfAbsent(chatId, k -> new ArrayList<>()).add(entity);
	}

	public List<Entity> getEntities(Long chatId) {
		return userEntities.getOrDefault(chatId, new ArrayList<>());
	}

}
