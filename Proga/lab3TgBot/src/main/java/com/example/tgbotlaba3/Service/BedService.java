package com.example.tgbotlaba3.Service;


import com.example.tgbotlaba3.MainClasses.Bed;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class BedService{
	private final Map<Long, String> userState = new HashMap<>();
	private final Map<Long, Bed> userBeds = new HashMap<>();
	private final Map<Long, String> partTimeName = new HashMap<>();
	private final Map<Long, Integer> partTimePrice1 = new HashMap<>();
	private final Map<Long, Integer> partTimePrice2 = new HashMap<>();

	public void setState(Long chatId, String state) { userState.put(chatId, state); }
	public String getState(Long chatId) { return userState.getOrDefault(chatId, "NONE"); }


	public void saveName(Long chatId, String name) { partTimeName.put(chatId, name); }
	public void savePrice1(Long chatId, int p) { partTimePrice1.put(chatId, p); }
	public void savePrice2(Long chatId, int p) { partTimePrice2.put(chatId, p); }


	public String getName(Long chatId) { return partTimeName.get(chatId); }
	public int getP1(Long chatId) { return partTimePrice1.get(chatId); }
	public int getP2(Long chatId) { return partTimePrice2.get(chatId); }

	public void saveFinalBed(Long chatId, Bed bed) {
		userBeds.put(chatId, bed);
		userState.put(chatId, "READY");
	}
	public Bed getBed(Long chatId) { return userBeds.get(chatId); }

}
