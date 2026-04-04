package server.Commands;

import server.Service.ArgsForCommands;
import server.Service.TicketManager;


import java.util.ArrayList;
import java.util.List;

/**
 * The type Remove greater key command.
 */
public class RemoveGreaterKeyCommand implements Command,TypeOfArgument {
	private ArgsForCommands args;
	private final TicketManager tm =  TicketManager.getInstance();

	@Override
	public void setArgs(Object args){
		this.args = (ArgsForCommands) args;
	}

	@Override
	public String executeCommand(){
		List<Integer> keys = new ArrayList<>(tm.getTicketCollection().keySet());
		keys.sort(null);
		if(tm.getCollectionSize()>0){
			tm.getTicketCollection().keySet().stream().filter(key -> key > args.key).forEach(key -> {tm.getTicketCollection().remove(key);});
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
	public String typeOfArgument() {
		return "Integer";
	}

	@Override
	public String getName() {
		return "remove_greater_key";
	}

	@Override
	public String describeCommand(){
		return " - delete all collection elements whose key less than specified";
	}
}
