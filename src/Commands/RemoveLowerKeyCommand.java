package Commands;

import Service.TicketManager;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Remove lower key command.
 */
public class RemoveLowerKeyCommand implements Command {
	private Integer key;
	private final TicketManager tm =  TicketManager.getInstance();

	@Override
	public void executeCommand(){
		List<Integer> keys = new ArrayList<>(tm.getTicketCollection().keySet());
		keys.sort(null);
		if(tm.getCollectionSize()>0){
			for (Integer integer : keys)
				if (integer < key) {
					tm.getTicketCollection().remove(integer);
				} else {
					System.out.println("Removed is finished");
					return;
				}
			System.out.println("Removed is finished");
		} else {
			System.out.println("Collection is empty");
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
		return "remove_lower_key";
	}

	@Override
	public String describeCommand(){
		return " - delete all collection elements whose key more than specified";
	}
}
