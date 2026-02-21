package Service;

import Commands.*;

import java.util.*;

/**
 * The type Command manager.
 */
public class CommandManager {
	private final Map<String, Command> commands = new LinkedHashMap<>();
	private static CommandManager instance;

	/**
	 * Instantiates a new Command manager.
	 */
	public CommandManager() {
		registerCmd(new InfoCommand());
		registerCmd(new HelpCommand(this));
		registerCmd(new ShowCommand());
		registerCmd(new InsertCommand());
		registerCmd(new UpdateIDCommand());
		registerCmd(new RemoveKeyCommand());
		registerCmd(new ClearCommand());
		registerCmd(new SaveCommand());
		registerCmd(new ExecuteScriptCommand(this));
		registerCmd(new ExitCommand());
		registerCmd(new ReplaceIfLowerCommand());
		registerCmd(new RemoveGreaterKeyCommand());
		registerCmd(new RemoveLowerKeyCommand());
		registerCmd(new MinByTypeCommand());
		registerCmd(new FilterGreaterThanPersonCommand());
		registerCmd(new PrintDescendingCommand());
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 */
	public static CommandManager getInstance() {
		if (instance == null) {
			instance = new CommandManager();
		}
		return instance;
	}

	private void registerCmd(Command cmd){
		commands.put(cmd.getName(),cmd);
	}

	/**
	 * Get command command.
	 *
	 * @param name the name
	 * @return the command
	 */
	public Command getCommand(String name){
		return commands.get(name);
	}

	/**
	 * Get command key set.
	 *
	 * @return the set
	 */
	public Set<String> getCommandKey(){
		return commands.keySet();
	}



}
