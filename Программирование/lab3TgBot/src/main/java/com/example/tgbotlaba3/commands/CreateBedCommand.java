package com.example.tgbotlaba3.commands;

import com.example.tgbotlaba3.Service.BedService;
import com.example.tgbotlaba3.Service.Command;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class CreateBedCommand implements Command {
	private final BedService bedService;

	public CreateBedCommand(BedService bedService) {
		this.bedService = bedService;
	}

	@Override
	public String getCommandName() {
		return "Создать кровать";
	}


	@Override
	public SendMessage execute(Update update) {
		Long chatId = update.getMessage().getChatId();

		bedService.setState(chatId, "WAITING_FOR_BED_NAME");

		return SendMessage.builder().chatId(chatId.toString()).text("Кровать назови").build();
	}
}
