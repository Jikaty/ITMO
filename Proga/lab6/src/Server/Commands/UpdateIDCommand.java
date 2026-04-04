package server.Commands;

import server.Service.ArgsForCommands;
import server.Service.TicketManager;

/**
 * The type Update id command.
 */
public class UpdateIDCommand implements Command,TypeOfArgument, TypeOfSecondArgument {
	private ArgsForCommands argsForUpdate;
	private final TicketManager tm  = TicketManager.getInstance();

	@Override
	public void setArgs(Object args){
		argsForUpdate = (ArgsForCommands) args;
	}

	@Override
	public String executeCommand(){
		if(tm.getTicketCollection().containsKey(argsForUpdate.key)){
			tm.ticketRegister(argsForUpdate.key, argsForUpdate.ticket);
		} else{
			return ("Ticket with this key not found");
		}
		return "Ticket has been updated";
	}

	@Override
	public boolean needsArgument() {
		return true;
	}

	@Override
	public boolean needMoreThenOneArgument() {
		return true;
	}

	@Override
	public String typeOfArgument() {
		return "Integer";
	}

	@Override
	public String typeOfSecondArgument() {
		return "Ticket";
	}

	@Override
	public String getName() {
		return "update";
	}

	@Override
	public String describeCommand(){
		return " - updates the value of a collection element whose id is equal to the specified value";
	}
}
