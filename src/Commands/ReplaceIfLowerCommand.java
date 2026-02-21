package Commands;

import Model.Ticket;
import Service.CreateTicket;
import Service.TicketManager;

/**
 * The type Replace if lower command.
 */
public class ReplaceIfLowerCommand implements Command {
	private Integer key;
	private final TicketManager tm = TicketManager.getInstance();
	private final CreateTicket createTicket = new CreateTicket();

	@Override
	public void executeCommand(){
		if(tm.getTicketCollection().containsKey(key)){
			Ticket ticket =createTicket.getTicket();
			if(tm.getTicketPower(tm.getTicketCollection().get(key)) > tm.getTicketPower(ticket)){
				tm.ticketRegister(key,ticket);
				System.out.println("Ticket has been updated");
			} else {
				System.out.println("New ticket better than old ticket");
			}
		} else System.out.println("Ticket not found");
	}


	@Override
	public void setKey(Integer key) {
		this.key = key;
	}

	@Override
	public boolean needsArgument(){
		return true;
	}

	@Override
	public String getName() {
		return "replace_if_lowe";
	}

	@Override
	public String describeCommand(){
		return " -  replaces the value by key if the new value is less than the old value";
	}
}
