package itmo.lab7.server.Commands;

import itmo.lab7.server.Service.ArgsForCommands;
import itmo.lab7.server.Service.DBManager;
import itmo.lab7.server.Service.TicketManager;

/**
 * The type Update id command.
 */
public class UpdateIDCommand implements Command,TypeOfArgument, TypeOfSecondArgument {
	private ArgsForCommands argsForUpdate;
	TicketManager tm = TicketManager.getInstance();
	DBManager dbManager = DBManager.getInstance();

	@Override
	public void setArgs(Object args){
		argsForUpdate = (ArgsForCommands) args;
	}

	@Override
	public String executeCommand(){
		int userId = dbManager.getUserId(argsForUpdate.login);
		if(dbManager.updateTicket(argsForUpdate.key, argsForUpdate.ticket,userId)){
			tm.ticketRegister(argsForUpdate.key, argsForUpdate.ticket);
			return "Ticket has been updated";
		} else{
			return ("Ticket with this key not found or u a not owner");
		}

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
