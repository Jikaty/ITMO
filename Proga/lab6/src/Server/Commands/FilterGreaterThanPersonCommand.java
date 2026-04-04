package server.Commands;

import server.Service.ArgsForCommands;
import server.Service.TicketManager;

/**
 * The type Filter greater than person command.
 */
public class FilterGreaterThanPersonCommand implements Command,TypeOfArgument {
	private final TicketManager tm = TicketManager.getInstance();
	ArgsForCommands args;

	@Override
	public void setArgs(Object args){
		this.args = (ArgsForCommands) args;
	}

	@Override
	public String executeCommand(){
		StringBuilder result = new StringBuilder();
		tm.getTicketCollection().keySet().stream().filter(hashMapKey -> tm.getTicketPower(tm.getTicketCollection().get(hashMapKey)) > args.key)
				.forEach(hashMapKey -> {result.append(tm.getTicketCollection().get(hashMapKey).toString()).append("\n");});
		return  result.toString();
	}

	@Override
	public boolean needsArgument(){
		return true;
	}

	@Override
	public String typeOfArgument() {
		return "Integer";
	}

	@Override
	public String getName() {
		return "filter_greater_than_person";
	}

	@Override
	public String describeCommand(){
		return " - displays elements whose person field value is greater than the specified value";
	}
}
