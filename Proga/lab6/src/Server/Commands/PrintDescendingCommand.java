package server.Commands;


import server.Service.TicketManager;

import java.util.Comparator;


/**
 * The type Print descending command.
 */
public class PrintDescendingCommand implements Command {
	private final TicketManager tm = TicketManager.getInstance();

	@Override
	public String executeCommand(){
		StringBuilder builder = new StringBuilder();
		if(tm.getCollectionSize() > 1){
			tm.getTicketCollection().keySet().stream().sorted(Comparator.reverseOrder())
					.forEach(key -> {builder.append(key+" "+tm.getTicketCollection().get(key)).append("\n");});
		} else {
			builder.append("Collection haven't enough element for sort").append("\n");
		}
		return builder.toString();
	}

	@Override
	public String getName() {
		return "print_descending";
	}
	@Override
	public String describeCommand(){
		return " - displays the elements of the collection in descending order";
	}
}
