package Commands;


import Model.Ticket;
import Service.TicketManager;

/**
 * The type Info command.
 */
public class InfoCommand implements Command {
	/**
	 * The Tm.
	 */
	TicketManager tm = TicketManager.getInstance();
	@Override
	public String describeCommand(){
		return " - show collection information";
	}
	@Override
	public void executeCommand(){
		try {
			System.out.println(tm.getCollectionSize());
			System.out.println(tm.getInitTime());
			System.out.println(tm.getCollectionTime());
			System.out.println(tm.getCollectionHashCode());
		}catch(NullPointerException e){
			System.out.println("Collection haven't data");
		}
	}
}
