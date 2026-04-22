package com.example.tgbotlaba3.Service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public interface ReplyKeyboard {
	ReplyKeyboardMarkup createReplyKeyboard();
	SendMessage execute(Update update);
}
