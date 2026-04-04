package server.Commands;

import server.Service.TicketManager;

/**
 * The type Clear command.
 */
public class ClearCommand implements Command {
	/**
	 * The Tm.
	 */
	TicketManager tm = TicketManager.getInstance();

	@Override
	public String executeCommand(){
		tm.getTicketCollection().clear();
		return ("Ticket collection has been cleared");
	}

	@Override
	public String describeCommand(){
		return " - clear collection";
	}
}
