package Commands;

import Service.TicketManager;

public class RemoveKeyCommand implements Command{
	private Integer key;
	TicketManager tm = TicketManager.getInstance();

	@Override
	public void executeCommand(){
		if(tm.getTicketCollection().containsKey(key)){
			tm.getTicketCollection().remove(key);
			System.out.println("Ticket removed successfully");
		} else {
			System.out.println("Ticket Not Found");
		}
	}



	@Override
	public String getName() {
		return "remove_key";
	}

	@Override
	public String describeCommand(){
		return " - удаляет элемент из коллекции по его ключу";
	}

	@Override
	public void setKey(Integer key){
		this.key = key;
	}

	@Override
	public boolean needsArgument(){
		return true;
	}
}
