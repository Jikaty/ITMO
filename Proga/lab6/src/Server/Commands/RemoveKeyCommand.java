package server.Commands;

import server.Service.ArgsForCommands;
import server.Service.TicketManager;

/**
 * The type Remove key command.
 */
public class RemoveKeyCommand implements Command,TypeOfArgument {
	private ArgsForCommands args;
	TicketManager tm = TicketManager.getInstance();

	@Override
	public void setArgs(Object args){
		this.args = (ArgsForCommands) args;
	}

	@Override
	public String executeCommand(){
		if(tm.getTicketCollection().containsKey(args.key)){
			tm.getTicketCollection().remove(args.key);
			return ("Ticket removed successfully");
		} else {
			return ("Ticket Not Found");
		}
	}



	@Override
	public String getName() {
		return "remove_key";
	}

	@Override
	public String describeCommand(){
		return " - delete collection element by key";
	}
	@Override
	public String typeOfArgument() {
		return "Integer";
	}


	@Override
	public boolean needsArgument(){
		return true;
	}
}
