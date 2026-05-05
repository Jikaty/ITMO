package itmo.lab7.server.Commands;


import itmo.lab7.server.Service.ArgsForCommands;
import itmo.lab7.server.Service.DBManager;
import itmo.lab7.server.Service.TicketManager;

import java.time.ZonedDateTime;
import java.util.UUID;


/**
 * The type Insert command.
 */
public class InsertCommand implements  Command, TypeOfArgument, TypeOfSecondArgument{
	private final TicketManager tm = TicketManager.getInstance();
	private ArgsForCommands argsForInsert;
	private DBManager dbManager = DBManager.getInstance();


	@Override
	public void setArgs(Object args) {
		this.argsForInsert = (ArgsForCommands) args;
	}

	@Override
	public String executeCommand(){
		if(tm.getTicketCollection().containsKey(argsForInsert.key)){
			return ("Ticket with this key already created");
		}else {
			int userId = dbManager.getUserId(argsForInsert.login);
			dbManager.saveToBd(argsForInsert.ticket,argsForInsert.key,userId);
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
