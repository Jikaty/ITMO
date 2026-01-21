package com.example.tgbotlaba3.commands;

import com.example.tgbotlaba3.MainClasses.Bed;
import com.example.tgbotlaba3.MainClasses.Entity;
import com.example.tgbotlaba3.Service.BedService;
import com.example.tgbotlaba3.Service.Command;
import com.example.tgbotlaba3.Service.EntityService;
import com.example.tgbotlaba3.Service.ReplyKeyboard;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;


import java.util.ArrayList;
import java.util.List;


@Component
public class StartStoryCommand implements ReplyKeyboard, Command {
	private final EntityService entityService;
	private final DefaultAbsSender bot;
	private final BedService bedService;
	public StartStoryCommand(EntityService entityService,BedService bedService, @Lazy DefaultAbsSender bot) {
		this.entityService = entityService;
		this.bot = bot;
		this.bedService = bedService;
	}
	@Override
	public String getCommandName() {
		return "Запустить историю";
	}
	@Override
	public SendMessage execute(Update update) {
		Long chatId = update.getMessage().getChatId();
		Bed bed = bedService.getBed(chatId);
		List<Entity> entities = entityService.getEntities(chatId);

		if (entities.isEmpty()) {
			return SendMessage.builder().chatId(chatId.toString()).text("Не задал имена для персов").build();
		}
		if (bed == null) {
			return SendMessage.builder().chatId(chatId.toString()).text("Кровать не создана").build();
		}

		if (entities.size() == 2) {
			if (bed.getBedTotalCost()>entities.get(0).getMoney() + entities.get(1).getMoney()) {
				return  SendMessage.builder().chatId(chatId.toString()).text("Нет денег даже на один комплект").build();
			}
			for (Entity entity : entities) {
				SendMessage message = SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text(entity.countMoney()).build();
				try {
					bot.execute(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			for (int i = 0; i < 2; i++) {
				if (i == 0) {
					bed.fullBed(entities.get(0), entities.get(1));
					SendMessage message = SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text(bed.getFullMessage()).build();
					try{
						bot.execute(message);
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					SendMessage firstBedReady = SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text("Первую кровать заправили, работаем над второй\n").build();
					bed.fullBed(entities.get(0), entities.get(1));
					SendMessage message = SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text(bed.getFullMessage()).build();
					try{
						bot.execute(firstBedReady);
						bot.execute(message);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		SendMessage messageForKeyboard = SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text("ВЫБИРАЙ ДЕЙСТВИЕ").build();
		messageForKeyboard.setReplyMarkup(createReplyKeyboard());
		return messageForKeyboard;
	}
	@Override
	public ReplyKeyboardMarkup createReplyKeyboard(){
		var story = new ReplyKeyboardMarkup();
		story.setResizeKeyboard(true);
		story.setOneTimeKeyboard(false);
		List<KeyboardRow> keyboard = new ArrayList<>();
		KeyboardRow row1 = new KeyboardRow();
		row1.add("Смотреть телек");
		row1.add("Спать");
		keyboard.add(row1);
		story.setKeyboard(keyboard);
		return  story;
	}


}
