package server.Commands;

import server.Service.TicketManager;

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
		tm.saveToFile("Data.xml");
		return "Saved";
	}

	@Override
	public String describeCommand(){
		return " - save collection to a file";
	}
}
