package server.Commands;

import model.TicketType;
import server.Service.TicketManager;


/**
 * The type Min by type command.
 */
public class MinByTypeCommand implements  Command{
	/**
	 * The Tm.
	 */
	TicketManager tm = TicketManager.getInstance();


	@Override
	public String executeCommand(){
		StringBuilder builder = new StringBuilder();
		tm.getTicketCollection().keySet().stream()
				.map(key -> tm.getTicketType(key).getTicketTypeCode())
				.min(Integer::compareTo)
				.map(TicketType::getTicketTypeByCode)
				.ifPresentOrElse(
						System.out::println,
						() -> builder.append("Ticket collection is empty").append("\n")
				);
		return builder.toString();
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
