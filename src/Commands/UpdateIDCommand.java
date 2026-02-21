package Commands;

import Service.CreateTicket;
import Service.TicketManager;

/**
 * The type Update id command.
 */
public class UpdateIDCommand implements Command {
	private Integer key;
	private final TicketManager tm  = TicketManager.getInstance();
	private final CreateTicket updateTicket = new CreateTicket();

	@Override
	public void executeCommand(){
		if(tm.getTicketCollection().containsKey(key)){
			tm.ticketRegister(key,updateTicket.getTicket());
		} else{
			System.out.println("Ticket with this key not found");
		}

	}

	@Override
	public void setKey(Integer key) {
		this.key = key;
	}

	@Override
	public boolean needsArgument() {
		return true;
	}

	@Override
	public String getName() {
		return "update";
	}

	@Override
	public String describeCommand(){
		return " - updates the value of a collection element whose id is equal to the specified value";
	}
}
