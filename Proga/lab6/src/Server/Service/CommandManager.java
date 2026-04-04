package server.Service;

import common.CommandInfo;
import server.Commands.Command;import server.Commands.*;

import java.util.*;import java.util.stream.Collectors;

/**
 * The type Command manager.
 */
public class CommandManager {
	private final Map<String, Command> commands = new LinkedHashMap<>();
	public List<String> fileNames = new ArrayList<>();
	private static CommandManager instance;


	public CommandManager() {
		registerCmd(new InfoCommand());
		registerCmd(new HelpCommand(this));
		registerCmd(new ShowCommand());
		registerCmd(new InsertCommand());
		registerCmd(new UpdateIDCommand());
		registerCmd(new RemoveKeyCommand());
		registerCmd(new ClearCommand());
		registerCmd(new SaveCommand());// delete from client app
		//registerCmd(new ExecuteScriptCommand());
		registerCmd(new ExitCommand());
		registerCmd(new ReplaceIfLowerCommand());
		registerCmd(new RemoveGreaterKeyCommand());
		registerCmd(new RemoveLowerKeyCommand());
		registerCmd(new MinByTypeCommand());
		registerCmd(new FilterGreaterThanPersonCommand());
		registerCmd(new PrintDescendingCommand());
	}


	public static CommandManager getInstance() {
		if (instance == null) {
			instance = new CommandManager();
		}
		return instance;
	}

	private void registerCmd(Command  cmd) {
		commands.put(cmd.getName(),cmd);
	}

	public Command getCommand(String name){
		return commands.get(name);
	}

	public Set<String> getCommandKey(){
		return commands.keySet();
	}

	public Map<String, CommandInfo> getCommandsData() {
		return commands.values().stream()
				.filter(cmd -> !cmd.getName().equalsIgnoreCase("save"))
				.collect(Collectors.toMap(
						Command::getName,
						cmd -> new CommandInfo(
								cmd.getName(),
								cmd.needsArgument(),
								cmd.needMoreThenOneArgument()
						)
				));

	}


}
