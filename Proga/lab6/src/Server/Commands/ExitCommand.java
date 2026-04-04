package server.Commands;

import server.Service.ArgsForCommands;
import server.Service.TicketManager;


/**
 * The type Exit command.
 */
public class ExitCommand implements Command, TypeOfArgument{
	ArgsForCommands args;


	@Override
	public String executeCommand() {
		return "Work was finished";
	}
	@Override
	public String describeCommand(){
		return " - finish program";
	}

	@Override
	public String typeOfArgument() {
		return "SocketChannel";
	}


}
