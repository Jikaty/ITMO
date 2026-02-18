package Model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

@ToString
@Setter
@Getter
public class Ticket {
	private int id;
	private String name;
	private Coordinates coordinates;
	private ZonedDateTime creationDate;
	private long price;
	private TicketType type;
	private Person person;

	public Ticket(int id,String name, Coordinates coordinates, ZonedDateTime creationDate, long price, TicketType type, Person person) {
		this.id = id;
		this.name = name;
		this.coordinates = coordinates;
		this.creationDate = creationDate;
		this.price = price;
		this.type = type;
		this.person = person;
	}
}
