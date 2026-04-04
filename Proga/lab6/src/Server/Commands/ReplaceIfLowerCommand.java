package server.Commands;

import server.Service.ArgsForCommands;
import server.Service.TicketManager;

/**
 * The type Replace if lower command.
 */
public class ReplaceIfLowerCommand implements Command,TypeOfArgument, TypeOfSecondArgument {
	ArgsForCommands args;
	private final TicketManager tm = TicketManager.getInstance();

	@Override
	public void setArgs(Object args){
		this.args = (ArgsForCommands) args;
	}

	@Override
	public String executeCommand(){
		if(tm.getTicketCollection().containsKey(args.key)){
			if(tm.getTicketPower(tm.getTicketCollection().get(args.key)) > tm.getTicketPower(args.ticket)){
				tm.ticketRegister(args.key,args.ticket);
				return ("Ticket has been updated");
			} else {
				return ("New ticket better than old ticket");
			}
		} else return ("Ticket not found");
	}

	@Override
	public boolean needMoreThenOneArgument() {
		return true;
	}
	@Override
	public String typeOfSecondArgument() {
		return "Ticket";
	}

	@Override
	public boolean needsArgument(){
		return true;
	}

	@Override
	public String getName() {
		return "replace_if_lowe";
	}
	@Override
	public String typeOfArgument() {
		return "Integer";
	}

	@Override
	public String describeCommand(){
		return " -  replaces the value by key if the new value is less than the old value";
	}
}
