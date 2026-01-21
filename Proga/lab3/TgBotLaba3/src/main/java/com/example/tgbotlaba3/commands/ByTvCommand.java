package com.example.tgbotlaba3.commands;


import com.example.tgbotlaba3.Service.Command;
import com.example.tgbotlaba3.Service.EntityService;
import com.example.tgbotlaba3.Service.TvService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class ByTvCommand implements Command {
	private final TvService tvService;
	private final EntityService entityService;
	private final StartCommand startCommand;
	private final DefaultAbsSender bot;

	public ByTvCommand(TvService tvService,EntityService entityService,StartCommand startCommand,@Lazy DefaultAbsSender bot) {
		this.tvService = tvService;
		this.entityService = entityService;
		this.startCommand = startCommand;
		this.bot = bot;
	}
	@Override
	public String getCommandName(){
		return "Купить телек";
	}
	@Override
	public SendMessage execute(Update update){
		var tv = tvService.getTv(update.getMessage().getChatId());
		var entities = entityService.getEntities(update.getMessage().getChatId());
		tv.useTv(entities.get(0), entities.get(1));
		try{
			bot.execute(SendMessage.builder().chatId(update.getMessage().getChatId().toString()).text(tv.getFullMessage()).build());
		}catch (TelegramApiException e){}
		return startCommand.execute(update);
	}
}
