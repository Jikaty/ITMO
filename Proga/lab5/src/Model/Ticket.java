package Model;


import lombok.*;

import java.time.ZonedDateTime;

/**
 * The type Ticket.
 */
@AllArgsConstructor
@ToString
@Setter
@Getter
@NoArgsConstructor
public class Ticket {
	private int id;
	private String name;
	private Coordinates coordinates;
	private ZonedDateTime creationDate;
	private long price;
	private TicketType type;
	private Person person;
}

