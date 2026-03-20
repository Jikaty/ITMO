package Commands;

import Service.TicketManager;

/**
 * The type Show command.
 */
public class ShowCommand implements Command{
	/**
	 * The Tm.
	 */
	TicketManager tm = TicketManager.getInstance();
	@Override
	public void executeCommand(){
		System.out.println(tm.getTicketCollection().toString());
	}

	@Override
	public String describeCommand(){
		return " - prints all elements of the collection to the standard output stream in string representation";
	}
}
