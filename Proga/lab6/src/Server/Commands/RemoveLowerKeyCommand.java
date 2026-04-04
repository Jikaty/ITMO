package server.Commands;

import server.Service.ArgsForCommands;
import server.Service.TicketManager;

/**
 * The type Remove lower key command.
 */
public class RemoveLowerKeyCommand implements Command,TypeOfArgument {
	private ArgsForCommands args;
	private final TicketManager tm =  TicketManager.getInstance();

	@Override
	public void setArgs(Object args){
		this.args = (ArgsForCommands) args;
	}

	@Override
	public String executeCommand(){
		if(tm.getCollectionSize()>0){
			tm.getTicketCollection().keySet().stream().filter(integer -> integer < args.key).forEach(integer -> tm.getTicketCollection().remove(integer));
			return ("Removed is finished");
		} else {
			return ("Collection is empty");
		}
	}

	@Override
	public boolean needsArgument() {
		return true;
	}

	@Override
	public String getName() {
		return "remove_lower_key";
	}
	@Override
	public String typeOfArgument() {
		return "Integer";
	}

	@Override
	public String describeCommand(){
		return " - delete all collection elements whose key more than specified";
	}
}
