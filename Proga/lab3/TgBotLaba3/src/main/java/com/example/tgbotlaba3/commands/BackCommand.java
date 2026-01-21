package com.example.tgbotlaba3.commands;

import com.example.tgbotlaba3.Service.Command;
import com.example.tgbotlaba3.Service.Navigation;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class BackCommand implements Command {
	private final Navigation navigation;
	private final Map<String, Command> commands; // Внедряем все команды

	public BackCommand(Navigation navigation, List<Command> commandList) {
		this.navigation = navigation;
		this.commands = commandList.stream().collect(Collectors.toMap(Command::getCommandName, cmd -> cmd));
	}

	@Override
	public String getCommandName() {
		return "Назад";
	}

	@Override
	public SendMessage execute(Update update) {
		Long chatId = update.getMessage().getChatId();
		String lastCommandName = navigation.cancelLastStep(chatId);
		Command lastCommand = commands.get(lastCommandName);
		return lastCommand.execute(update);
	}
}
