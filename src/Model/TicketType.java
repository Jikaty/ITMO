package Model;

import lombok.Getter;

/**
 * The enum Ticket type.
 */
@Getter
public enum TicketType {
	/**
	 * Vip ticket type.
	 */
	VIP(4),
	/**
	 * Usual ticket type.
	 */
	USUAL(3),
	/**
	 * Budgetary ticket type.
	 */
	BUDGETARY(2),
	/**
	 * Cheap ticket type.
	 */
	CHEAP(1);
	private final int ticketTypeCode;

	TicketType(int ticketTypeCode) {
		this.ticketTypeCode = ticketTypeCode;
	}

	/**
	 * Gets ticket type by code.
	 *
	 * @param ticketTypeCode the ticket type code
	 * @return the ticket type by code
	 */
	public static TicketType getTicketTypeByCode(Integer ticketTypeCode) {
		for (TicketType type : TicketType.values()) {
			if (type.ticketTypeCode == ticketTypeCode) {
				return type;
			}
		}
		throw new IllegalArgumentException("Ticket type code " + ticketTypeCode + " not found");
	}
}
