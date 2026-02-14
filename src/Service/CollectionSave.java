package Service;

import Model.Ticket;

import java.util.Hashtable;

public class CollectionSave {
	Hashtable<Integer, Ticket> Tickets = new Hashtable<Integer, Ticket>();
	public void AddTicket(Ticket ticket, Integer key) {
		Tickets.put(key, ticket);
	}
	public Integer GetTicketKey(Integer key) {
		return key;
	}


}
