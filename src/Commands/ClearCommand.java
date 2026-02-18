package Commands;

import Service.TicketManager;

public class ClearCommand implements Command {

	TicketManager tm = TicketManager.getInstance();

	@Override
	public void executeCommand(){
		tm.getTicketCollection().clear();
		System.out.println("Ticket collection has been cleared");
	}

	@Override
	public String describeCommand(){
		return " - очистить коллекцию";
	}
}
