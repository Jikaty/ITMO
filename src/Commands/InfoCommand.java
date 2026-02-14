package Commands;


import Model.Ticket;
import Service.TicketManager;

public class InfoCommand implements Command {
	TicketManager tm = TicketManager.getInstance();
	@Override
	public String describeCommand(){
		return " - выводит информацию о коллекции";
	}
	@Override
	public void executeCommand(){
		try {
			System.out.println(tm.getCollectionSize());
			System.out.println(tm.getInitTime());
			System.out.println(tm.getCollectionTime());
			System.out.println(tm.getCollectionHashCode());
		}catch(NullPointerException e){
			System.out.println("Нет данных в коллекции");
		}
	}
}
