package Commands;

import Service.TicketManager;

/**
 * The type Clear command.
 */
public class ClearCommand implements Command {

	/**
	 * The Tm.
	 */
	TicketManager tm = TicketManager.getInstance();

	@Override
	public void executeCommand(){
		tm.getTicketCollection().clear();
		System.out.println("Ticket collection has been cleared");
	}

	@Override
	public String describeCommand(){
		return " - clear collection";
	}
}
