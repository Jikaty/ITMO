package Model;

import lombok.Getter;

@Getter
public enum TicketType {
	VIP(4),
	USUAL(3),
	BUDGETARY(2),
	CHEAP(1);
	private final int ticketTypeCode;

	TicketType(int ticketTypeCode) {
		this.ticketTypeCode = ticketTypeCode;
	}

	public static TicketType getTicketTypeByCode(Integer ticketTypeCode) {
		for (TicketType type : TicketType.values()) {
			if (type.ticketTypeCode == ticketTypeCode) {
				return type;
			}
		}
		throw new IllegalArgumentException("Неизвестный код: " + ticketTypeCode);
	}
}
