package com.example.tgbotlaba3.commands;

import com.example.tgbotlaba3.Service.Command;
import com.example.tgbotlaba3.Service.ReplyKeyboard;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class StartCommand implements ReplyKeyboard, Command {
	@Override
	public String getCommandName() {
		return "/start";
	}

	@Override
	public SendMessage execute(Update update) {
		SendMessage message = SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text("Добро пожаловать в бота по третей лабе").build();
		message.setReplyMarkup(createReplyKeyboard());
		return message;
	}

	@Override
	public ReplyKeyboardMarkup createReplyKeyboard(){
		var entityName = new ReplyKeyboardMarkup();
		entityName.setOneTimeKeyboard(false);
		entityName.setResizeKeyboard(true);
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow row1 = new KeyboardRow();
		KeyboardRow row2 = new KeyboardRow();
		row1.add("Задать имена");
		row1.add("Создать кровать");
		row2.add("Создать телек");
		row2.add("Запустить историю");
		keyboard.add(row1);
		keyboard.add(row2);
		entityName.setKeyboard(keyboard);
		return entityName;
	}
}