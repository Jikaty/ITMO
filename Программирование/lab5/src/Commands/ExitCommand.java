package Commands;

import Service.ScriptAndPeapleScanner;
import Service.TicketManager;


/**
 * The type Exit command.
 */
public class ExitCommand implements Command{
	private TicketManager tm = TicketManager.getInstance();
	@Override
	public void executeCommand() {
		System.out.println("Work was finished without save");
		tm.setSaveFlag(false);
		ScriptAndPeapleScanner.setStopFlag(true);
	}
	@Override
	public String describeCommand(){
		return " - finish program without save";
	}

}
