package Commands;
import Service.CommandManager;

import java.util.Set;

/**
 * The type Help command.
 */
public class HelpCommand implements Command {
	private final CommandManager cm;

	/**
	 * Instantiates a new Help command.
	 *
	 * @param cm the cm
	 */
	public HelpCommand(CommandManager cm) {
		this.cm = cm;
	}

	@Override
	public void executeCommand(){
		Set<String> allCommands = cm.getCommandKey();
		for (String name : allCommands){
			Command cmd = cm.getCommand(name);
			System.out.println(cmd.getName() + cmd.describeCommand());
		}
	}
	@Override
	public String describeCommand(){
		return " - guide for commands";
	}
}
