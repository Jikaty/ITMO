package model;


import lombok.*;

import java.io.Serializable;
import java.time.ZonedDateTime;

/**
 * The type Ticket.
 */
@AllArgsConstructor
@ToString
@Setter
@Getter
@NoArgsConstructor
public class Ticket implements Serializable {
	private int id;
	private String name;
	private Coordinates coordinates;
	private ZonedDateTime creationDate;
	private long price;
	private TicketType type;
	private Person person;

	public Ticket(String name, Coordinates coordinates, long price, TicketType type, Person person) {
		this.name = name;
		this.coordinates = coordinates;
		this.price = price;
		this.type = type;
		this.person = person;
	}
}

