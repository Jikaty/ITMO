package com.example.tgbotlaba3.commands;

import com.example.tgbotlaba3.Service.Command;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;


@Component
public class SleepCommand implements Command {

	private final StartCommand startCommand;
	private final DefaultAbsSender bot;


	public SleepCommand(StartCommand startCommand,@Lazy DefaultAbsSender bot) {
		this.startCommand = startCommand;
		this.bot = bot;
	}
	@Override
	public String getCommandName() {
		return "Спать";
	}

	@Override
	public SendMessage execute(Update update){
		try{
			bot.execute(SendMessage.builder().chatId(update.getMessage().getChatId()).text("Парни легли спать").build());
		}catch(Exception e){}
		return startCommand.execute(update);
	}

}
