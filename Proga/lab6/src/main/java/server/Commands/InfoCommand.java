package server.Commands;


import server.Service.TicketManager;

/**
 * The type Info command.
 */
public class InfoCommand implements Command {
	/**
	 * The Tm.
	 */
	TicketManager tm = TicketManager.getInstance();
	@Override
	public String describeCommand(){
		return " - show collection information";
	}
	@Override
	public String executeCommand(){
		StringBuilder builder = new StringBuilder();
		try {
			builder.append(tm.getCollectionSize()).append("\n")
					.append(tm.getInitTime()).append("\n")
					.append(tm.getCollectionTime()).append("\n")
					.append(tm.getCollectionHashCode()).append("\n");
		}catch(NullPointerException e){
			builder.append("Collection haven't data").append("\n");
		}
		return builder.toString();
	}
}
