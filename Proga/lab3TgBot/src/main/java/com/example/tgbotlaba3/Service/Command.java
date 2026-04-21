package com.example.tgbotlaba3.Service;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface Command {
	String getCommandName();
	SendMessage execute(Update update);
}
