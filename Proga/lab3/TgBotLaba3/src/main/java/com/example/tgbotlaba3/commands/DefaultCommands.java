package com.example.tgbotlaba3.commands;

import com.example.tgbotlaba3.Service.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class DefaultCommands {
	public SendMessage unknownCommand(Update update) {
		return SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text("НЕТ ТАКОЙ КОМАНДЫ").build();
	}
}