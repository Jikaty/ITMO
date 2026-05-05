package itmo.lab7.server.Commands;

import itmo.lab7.server.Service.TicketManager;

/**
 * The type Save command.
 */
public class SaveCommand implements Command{
	/**
	 * The Tm.
	 */
	TicketManager tm = TicketManager.getInstance();

	@Override
	public String executeCommand(){
		return "Saved";
	}

	@Override
	public String describeCommand(){
		return " - save collection to a file";
	}
}
