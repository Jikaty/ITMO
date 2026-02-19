package Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.ZonedDateTime;

@AllArgsConstructor
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
}
