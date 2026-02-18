package Commands;

import Service.TicketManager;

public class ShowCommand implements Command{
	TicketManager tm = TicketManager.getInstance();
	@Override
	public void executeCommand(){
		System.out.println(tm.toString());
	}

	@Override
	public String describeCommand(){
		return " -  выводит в стандартный поток вывода все элементы коллекции в строковом представлении";
	}
}
