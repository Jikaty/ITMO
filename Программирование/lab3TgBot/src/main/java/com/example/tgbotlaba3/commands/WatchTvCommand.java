package com.example.tgbotlaba3.commands;

import com.example.tgbotlaba3.Service.Command;
import com.example.tgbotlaba3.Service.EntityService;
import com.example.tgbotlaba3.Service.ReplyKeyboard;
import com.example.tgbotlaba3.Service.TvService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class WatchTvCommand implements Command, ReplyKeyboard {
	private final TvService tvService;
	private final EntityService entityService;

	public WatchTvCommand(TvService tvService,EntityService entityService) {
		this.tvService = tvService;
		this.entityService = entityService;
	}
	@Override
	public String getCommandName(){
		return "Смотреть телек";
	}
	@Override
	public SendMessage execute(Update update){
		var tv = tvService.getTv(update.getMessage().getChatId());
		var entities = entityService.getEntities(update.getMessage().getChatId());
		tv.useTv(entities.get(0), entities.get(1));
		SendMessage message = SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text(tv.getFullMessage()).build();
		message.setReplyMarkup(createReplyKeyboard());
		return message;
	}
	@Override
	public ReplyKeyboardMarkup createReplyKeyboard(){
		var story = new ReplyKeyboardMarkup();
		story.setResizeKeyboard(true);
		story.setOneTimeKeyboard(false);
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow row1 = new KeyboardRow();
		row1.add("Собирать металл");
		row1.add("Спать");
		keyboard.add(row1);
		story.setKeyboard(keyboard);
		return  story;
	}
}
