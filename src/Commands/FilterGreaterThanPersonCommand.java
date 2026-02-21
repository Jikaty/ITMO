package Commands;

import Model.Ticket;
import Service.CreateTicket;
import Service.TicketManager;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Filter greater than person command.
 */
public class FilterGreaterThanPersonCommand implements Command {
	private Integer key;
	private final TicketManager tm = TicketManager.getInstance();

	@Override
	public void executeCommand(){
		List<Integer> hashMapKeys = new ArrayList<>(tm.getTicketCollection().keySet());
		for(Integer oneOfHashMapKey : hashMapKeys){
			 if(tm.getTicketPower(tm.getTicketCollection().get(oneOfHashMapKey)) > key){
				 System.out.println(tm.getTicketCollection().get(oneOfHashMapKey));
			 }
		}
	}

	@Override
	public boolean needsArgument(){
		return true;
	}

	@Override
	public void setKey(Integer key) {
		this.key = key;
	}

	@Override
	public String getName() {
		return "filter_greater_than_person";
	}

	@Override
	public String describeCommand(){
		return " - displays elements whose person field value is greater than the specified value";
	}
}
