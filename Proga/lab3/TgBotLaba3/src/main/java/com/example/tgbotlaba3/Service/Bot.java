package com.example.tgbotlaba3.Service;

import com.example.tgbotlaba3.MainClasses.Bed;
import com.example.tgbotlaba3.MainClasses.Entity;
import com.example.tgbotlaba3.MainClasses.TV;
import com.example.tgbotlaba3.commands.DefaultCommands;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class Bot extends TelegramLongPollingBot {

	private final Map<String, Command> commands;
	private final DefaultCommands defaultCommands;
	private final Navigation navigation;
	private final EntityService entityService;
	private final BedService bedService;
	private final TvService tvService;

	@Value("${bot.name}")
	private String botName;

	public Bot(List<Command> commandList,TvService tvService,EntityService entityService,BedService bedService, DefaultCommands defaultCommands,Navigation navigation ,@Value("${bot.token}") String botToken) {
		super(botToken);
		this.navigation = navigation;
		this.entityService = entityService;
		this.defaultCommands = defaultCommands;
		this.bedService = bedService;
		this.tvService = tvService;
		this.commands = commandList.stream().collect(Collectors.toMap(Command::getCommandName, cmd -> cmd));
	}

	@Override
	public void onUpdateReceived(Update update) {
		if (update.hasMessage() && update.getMessage().hasText()) {
			String text = update.getMessage().getText();
			Long chatId = update.getMessage().getChatId();
			Command command = commands.get(text);
			String state = entityService.getState(chatId);
			if (state.equals("WAITING_FOR_PERSON_NAME")) {
				entityService.setPartTimeName(chatId, text);
				entityService.setState(chatId, "WAITING_FOR_PERSON_MONEY");
				sendText(chatId, "Деньги для " + text + ":");
				return;
			}
			if (state.equals("WAITING_FOR_PERSON_MONEY")) {
				try {
					int money = Integer.parseInt(text);
					String name = entityService.getPartTimeName(chatId);
					entityService.addEntity(chatId, new Entity(name, money));
					int count = entityService.getEntities(chatId).size();
					if (count < 2) {
						entityService.setState(chatId, "WAITING_FOR_PERSON_NAME");
						sendText(chatId, "Теперь имя для второго");
					} else {
						entityService.setState(chatId, "READY");
						sendText(chatId, "Персы созданы");
					}
				} catch (NumberFormatException e) {
					sendText(chatId, "Але цифры введи");
				}
				return;
			}
			String currentState = bedService.getState(chatId);
			if (currentState.equals("WAITING_FOR_BED_NAME")) {
				bedService.saveName(chatId, text);
				bedService.setState(chatId, "WAITING_PRICE_PART1");
				sendText(chatId, "Веди цену за Подушку");
				return;
			}

			if (currentState.equals("WAITING_PRICE_PART1")) {
				bedService.savePrice1(chatId, Integer.parseInt(text));
				bedService.setState(chatId, "WAITING_PRICE_PART2");
				sendText(chatId, "Теперь за одеяло");
				return;
			}

			if (currentState.equals("WAITING_PRICE_PART2")) {
				bedService.savePrice2(chatId, Integer.parseInt(text));
				bedService.setState(chatId, "WAITING_PRICE_PART3");
				sendText(chatId, "Теперь за простынь");
				return;
			}

			if (currentState.equals("WAITING_PRICE_PART3")) {
				int p3 = Integer.parseInt(text);
				int p1 = bedService.getP1(chatId);
				int p2 = bedService.getP2(chatId);
				String name = bedService.getName(chatId);
				Bed newBed = new Bed(name, p1, p2, p3);
				bedService.saveFinalBed(chatId, newBed);
				sendText(chatId, "Кровать " + name + " Общая стоимость: " + (p1 + p2 + p3));
				bedService.setState(chatId, "NONE");
				return;
			}
			String tvState = tvService.getState(chatId);
			if (tvState.equals("WAITING_FOR_TV_NAME")) {
				tvService.saveName(chatId, text);
				tvService.setState(chatId, "WAITING_FOR_TV_PRICE");
				sendText(chatId, "Теперь введи цену:");
				return;
			}
			if (tvState.equals("WAITING_FOR_TV_PRICE")) {
				try {
					int price = Integer.parseInt(text);
					String name = tvService.getName(chatId);
					TV newTv = new TV(name, price);
					tvService.saveFinalTv(chatId, newTv);
					tvService.setState(chatId, "NONE");
					sendText(chatId, "Телевизор " + name + " за " + price + " создан");
				} catch (NumberFormatException e) {
					sendText(chatId, "Але цифры введи");
				}
				return;
			}
			if (command != null) {
				if (!text.equals("Назад")) {
					navigation.push(chatId, text);
				}
				try {
					execute(command.execute(update));
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			} else {
				try {
					execute(defaultCommands.unknownCommand(update));
				} catch (TelegramApiException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public String getBotUsername() {
		return botName;
	}
	private void sendText(Long chatId, String text) {
		try {
			execute(SendMessage.builder().chatId(chatId.toString()).text(text).build());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}
}