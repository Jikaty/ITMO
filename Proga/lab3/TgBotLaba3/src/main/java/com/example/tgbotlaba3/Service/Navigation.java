package com.example.tgbotlaba3.Service;


import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

@Service
public class Navigation {
	private final Map<Long, Stack<String>> userHistory = new HashMap<>();

	public void push(Long chatId, String commandName) {
		userHistory.computeIfAbsent(chatId, k -> new Stack<>()).push(commandName);
	}

	public String cancelLastStep(Long chatId) {
		Stack<String> history = userHistory.get(chatId);
		if (history != null && history.size() > 1) {
			history.pop();
			return history.peek();
		}
		return "/start";
	}
}
