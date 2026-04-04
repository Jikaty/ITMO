package server.Commands;


import server.Service.ArgsForCommands;
import server.Service.TicketManager;import java.time.ZonedDateTime;
import java.util.UUID;


/**
 * The type Insert command.
 */
public class InsertCommand implements  Command, TypeOfArgument, TypeOfSecondArgument{
	private final TicketManager tm = TicketManager.getInstance();
	private ArgsForCommands argsForInsert;


	@Override
	public void setArgs(Object args) {
		this.argsForInsert = (ArgsForCommands) args;
	}

	@Override
	public String executeCommand(){
		if(tm.getTicketCollection().containsKey(argsForInsert.key)){
			return ("Ticket with this key already created");
		}else {
			argsForInsert.ticket.setId(UUID.randomUUID().hashCode());
			argsForInsert.ticket.setCreationDate(ZonedDateTime.now());
			tm.ticketRegister(argsForInsert.key,argsForInsert.ticket);
			return ("Complete");
		}
	}

	@Override
	public String describeCommand(){
		return " - insert new element with your key";
	}
	@Override
	public String typeOfArgument() {
		return "Integer";
	}

	@Override
	public boolean needsArgument(){
		return true;
	}

	@Override
	public boolean needMoreThenOneArgument(){
		return true;
	}

	@Override
	public String typeOfSecondArgument() {
		return "Ticket";
	}
}
