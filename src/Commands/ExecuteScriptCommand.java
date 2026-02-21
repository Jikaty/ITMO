package Commands;


import Service.CommandManager;
import Service.ScriptAndPeapleScanner;


/**
 * The type Execute script command.
 */
public class ExecuteScriptCommand implements Command {
	private final CommandManager commandManager;

	/**
	 * Instantiates a new Execute script command.
	 *
	 * @param commandManager the command manager
	 */
	public ExecuteScriptCommand(CommandManager commandManager) {
		this.commandManager = commandManager;
	}


	@Override
	public void executeCommand() {
		ScriptAndPeapleScanner scanner = new ScriptAndPeapleScanner();
		scanner.setScannerFlag(true);
		scanner.scanFromFile();
	}

	@Override
	public String getName() {
		return "execute_script";
	}
	@Override
	public String describeCommand(){
		return " - script from file";
	}
}
