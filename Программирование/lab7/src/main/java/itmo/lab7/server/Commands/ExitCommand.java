package itmo.lab7.server.Commands;

import itmo.lab7.server.Service.ArgsForCommands;


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
