package com.example.tgbotlaba3.commands;

import com.example.tgbotlaba3.MainClasses.ScrapMetal;
import com.example.tgbotlaba3.Service.Command;
import com.example.tgbotlaba3.Service.EntityService;
import com.example.tgbotlaba3.Service.ReplyKeyboard;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

@Component
public class FindScrapCommand implements Command, ReplyKeyboard {
	private final EntityService entityService;
	public FindScrapCommand(EntityService entityService) {
		this.entityService = entityService;
	}
	@Override
	public String getCommandName(){
		return "Собирать металл";
	}
	@Override
	public SendMessage execute(Update update){
		var entities = entityService.getEntities(update.getMessage().getChatId());
		ScrapMetal scrap = new ScrapMetal(2);
		scrap.checkMoney(entities.get(0), entities.get(1));
		SendMessage message = SendMessage.builder().chatId(update.getMessage().getChatId()).text(scrap.getFullMessage()).build();
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
		row1.add("Купить телек");
		row1.add("Спать");
		keyboard.add(row1);
		story.setKeyboard(keyboard);
		return  story;
	}
}
