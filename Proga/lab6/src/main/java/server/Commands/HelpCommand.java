package server.Commands;
import server.Service.CommandManager;

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
	public String executeCommand(){
		Set<String> allCommands = cm.getCommandKey();
		StringBuilder res = new StringBuilder();
		for (String name : allCommands){
			Command cmd = cm.getCommand(name);
			res.append(cmd.getName()).append(cmd.describeCommand()).append("\n");
		}
		return res.toString();
	}
	@Override
	public String describeCommand(){
		return " - guide for commands";
	}
}
