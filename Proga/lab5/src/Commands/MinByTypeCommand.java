package Commands;

import Model.TicketType;
import Service.TicketManager;

import java.util.*;


/**
 * The type Min by type command.
 */
public class MinByTypeCommand implements  Command{
	/**
	 * The Tm.
	 */
	TicketManager tm = TicketManager.getInstance();


	@Override
	public void executeCommand(){
		if(tm.getCollectionSize()>0){
			List<Integer> hashMapKeys = new ArrayList<>(tm.getTicketCollection().keySet());
			List<Integer> listForSmallestType = new ArrayList<>();
			for (Integer hashMapKey : hashMapKeys) {
				listForSmallestType.add(tm.getTicketType(hashMapKey).getTicketTypeCode());
			}
			System.out.println("Smallest Type: "+TicketType.getTicketTypeByCode(Collections.min(listForSmallestType)));
		} else {
			System.out.println("Ticket collection is empty");
		}
	}

	@Override
	public String getName() {
		return "min_by_type";
	}
	@Override
	public String describeCommand(){
		return " - show random object from collection whose field type is minimized";
	}
}
