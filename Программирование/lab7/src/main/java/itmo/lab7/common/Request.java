package itmo.lab7.common;

import lombok.Getter;
import lombok.Setter;import java.io.Serializable;

@Setter
@Getter
public class Request implements Serializable {
	private final String commandName;
	private final String stringArg;
	private final Object objectArg;
	private boolean isRegistration;
	private final String login;
	private final String password;

	public Request(String commandName, String stringArg, Object objectArg,  boolean isRegistration,  String login, String password) {
		this.commandName = commandName;
		this.stringArg = stringArg;
		this.objectArg = objectArg;
		this.isRegistration = isRegistration;
		this.login = login;
		this.password = password;
	}
}
