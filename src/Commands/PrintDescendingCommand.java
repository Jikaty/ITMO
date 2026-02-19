package Commands;


import Service.TicketManager;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class PrintDescendingCommand implements Command {
	private final TicketManager tm = TicketManager.getInstance();

	@Override
	public void executeCommand(){
		if(tm.getCollectionSize() > 1){
			List<Integer> hashMapKeys =  new ArrayList<>(tm.getTicketCollection().keySet());
			hashMapKeys.sort(Comparator.reverseOrder());
			for(Integer key : hashMapKeys){
				System.out.print(key + " ");
				System.out.println(tm.getTicketCollection().get(key));
			}
		} else {
			System.out.println("Тут сортировать то нехуй");
		}
	}

	@Override
	public String getName() {
		return "print_descending";
	}
	@Override
	public String describeCommand(){
		return " - выводит элементы коллекции в порядке убывания";
	}
}
