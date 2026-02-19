package Service;

import Model.Ticket;
import Model.TicketType;
import lombok.ToString;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;


@ToString(exclude = "initTime" )
public class TicketManager {
	private final Map<Integer, Ticket> ticketsCollection = new Hashtable<>() ;
	private final LocalDateTime initTime = LocalDateTime.now();

	private TicketManager(){

	}

	private static class TicketManagerHolder {
		private static final TicketManager INSTANCE = new TicketManager();
	}

	public static TicketManager getInstance() {
		return TicketManagerHolder.INSTANCE;
	}

	public String getInitTime(){
		return initTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
	}

	public String getCollectionTime(){
		return ticketsCollection.getClass().getName();
	}

	public int getCollectionSize(){
		return ticketsCollection.size();
	}

	public int getCollectionHashCode(){
		return ticketsCollection.hashCode();
	}

	public void ticketRegister (int key,Ticket ticket){
		ticketsCollection.put(key,ticket);
	}

	public Map<Integer, Ticket> getTicketCollection(){
		return ticketsCollection;
	}

	public TicketType getTicketType(Integer key){
		return ticketsCollection.get(key).getType();
	}


}
