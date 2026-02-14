package Service;

import Commands.*;

import java.util.*;

public class CommandManager {
	private final Map<String, Command> commands = new LinkedHashMap<>();

	public CommandManager() {
		registerCmd(new InfoCommand());
		registerCmd(new HelpCommand(this));
		registerCmd(new ShowCommand());
		registerCmd(new InsertCommand());
		registerCmd(new UpdateIDCommand());
		registerCmd(new RemoveKeyCommand());
		registerCmd(new ClearCommand());
		registerCmd(new SaveCommand());
		registerCmd(new ExecuteScriptCommand());
		registerCmd(new ExitCommand());
		registerCmd(new ReplaceIfLowerCommand());
		registerCmd(new RemoveGreaterKeyCommand());
		registerCmd(new RemoveLowerKeyCommand());
		registerCmd(new MinByTypeCommand());
		registerCmd(new FilterGreaterThanPersonCommand());
		registerCmd(new PrintDescendingCommand());
	}
	private void registerCmd(Command cmd){
		commands.put(cmd.getName(),cmd);
	}

	public Command getCommand(String name){
		return commands.get(name);
	}

	public Set<String> getCommandKey(){
		return commands.keySet();
	}



}
