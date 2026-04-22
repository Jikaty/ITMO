package common;

import lombok.Getter;
import lombok.Setter;import java.io.Serializable;

@Setter
@Getter
public class Request implements Serializable {
	private final String commandName;
	private final String stringArg;
	private final Object objectArg;

	public Request(String commandName, String stringArg, Object objectArg) {
		this.commandName = commandName;
		this.stringArg = stringArg;
		this.objectArg = objectArg;
	}
}
