package com.example.tgbotlaba3.commands;

import com.example.tgbotlaba3.Service.Command;
import com.example.tgbotlaba3.Service.TvService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
@Component
public class CreateTvCommand implements Command {
	private final TvService tvService;

	public CreateTvCommand(TvService tvService) {
		this.tvService = tvService;
	}
	@Override
	public String getCommandName(){
		return "Создать телек";
	}
	@Override
	public SendMessage execute(Update update) {
		Long chatId = update.getMessage().getChatId();
		tvService.setState(chatId, "WAITING_FOR_TV_NAME");
		return SendMessage.builder().chatId(chatId.toString()).text("Введите название телевизора:").build();
	}
}
