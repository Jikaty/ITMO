package Commands;


import Service.CreateTicket;
import Service.TicketManager;


/**
 * The type Insert command.
 */
public class InsertCommand implements  Command{
	private final TicketManager tm = TicketManager.getInstance();
	private Integer key;
	private final CreateTicket createTicket = new CreateTicket();

	@Override
	public void setKey(Integer key) {
		this.key = key;
	}

	@Override
	public void executeCommand(){
		if(tm.getTicketCollection().containsKey(key)){
			System.out.println("Ticket with this key already created");
		}else {
			tm.ticketRegister(this.key,createTicket.getTicket());
			System.out.println("Complete");
		}
	}

	@Override
	public String describeCommand(){
		return " - insert new element with your key";
	}

	@Override
	public boolean needsArgument(){
		return true;
	}
}
