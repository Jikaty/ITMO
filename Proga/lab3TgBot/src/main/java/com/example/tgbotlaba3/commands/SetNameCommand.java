package com.example.tgbotlaba3.commands;

import com.example.tgbotlaba3.Service.Command;
import com.example.tgbotlaba3.Service.EntityService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class SetNameCommand implements Command {
	private final EntityService entityService;

	public SetNameCommand(EntityService entityService) { this.entityService = entityService; }

	@Override
	public String getCommandName() {
		return "Задать имена";
	}

	@Override
	public SendMessage execute(Update update) {
		entityService.setState(update.getMessage().getChatId(), "WAITING_FOR_PERSON_NAME");
		return SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text("Введите имя персонажа:").build();
	}
}
