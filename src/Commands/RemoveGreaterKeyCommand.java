package Commands;

import Service.TicketManager;


import java.util.ArrayList;
import java.util.List;

public class RemoveGreaterKeyCommand implements Command {
	private Integer key;
	private final TicketManager tm =  TicketManager.getInstance();

	@Override
	public void executeCommand(){
		List<Integer> keys = new ArrayList<>(tm.getTicketCollection().keySet());
		keys.sort(null);
		if(tm.getCollectionSize()>0){
			for(int i=keys.size()-1; i>=0; i--){
				if(keys.get(i) > key){
					tm.getTicketCollection().remove(keys.get(i));
				} else {
					System.out.println("Removed is finished");
					return;
				}
			}
			System.out.println("Removed is finished");
		} else {
			System.out.println("Тут нихуя нет");
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
		return "remove_greater_key";
	}

	@Override
	public String describeCommand(){
		return " - удаляет из коллекции все элементы, ключ которых превышает заданный";
	}
}
