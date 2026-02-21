package Commands;

import Service.TicketManager;

/**
 * The type Save command.
 */
public class SaveCommand implements Command{
	/**
	 * The Tm.
	 */
	TicketManager tm = TicketManager.getInstance();

	@Override
	public void executeCommand(){
		tm.saveToFile("Data.xml");
	}

	@Override
	public String describeCommand(){
		return " - save collection to a file";
	}
}
