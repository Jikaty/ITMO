package itmo.lab7.server.Commands;

import itmo.lab7.server.Service.ArgsForCommands;
import itmo.lab7.server.Service.DBManager;
import itmo.lab7.server.Service.TicketManager;

import java.util.List;

/**
 * The type Clear command.
 */
public class ClearCommand implements Command {
	TicketManager tm = TicketManager.getInstance();
	private ArgsForCommands argsForClear;
	DBManager dbManager = DBManager.getInstance();

	@Override
	public void setArgs(Object args){
		argsForClear = (ArgsForCommands) args;
	}

	@Override
	public String executeCommand(){
		int userID = dbManager.getUserId(argsForClear.login);
		List<Integer> userIds = dbManager.getUserTicketIds(userID);
		if (userIds.isEmpty()){
			return "No users tickets found";
		}
		int deletedInDb = dbManager.clearTickets(userID);
		if (deletedInDb >= 0) {
			for (Object idFromDb : userIds) {
				Integer key = Integer.valueOf(idFromDb.toString());
				tm.ticketRemove(key);
			}
			return "Collection was cleared successfully";
		}
		return ("Smt wrong");
	}

	@Override
	public String describeCommand(){
		return " - clear collection";
	}
}
