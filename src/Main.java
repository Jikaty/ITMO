
import Service.ScriptAndPeapleScanner;
import Service.TicketManager;

/**
 * The type Main.
 */
public class Main {
	/**
	 * The entry point of application.
	 *
	 * @param args the input arguments
	 */
	public static void main(String[] args) {
		TicketManager tm = TicketManager.getInstance();
		ScriptAndPeapleScanner sc = new ScriptAndPeapleScanner();
		tm.loadFromFile("Data.xml");
		Runtime.getRuntime().addShutdownHook(new Thread(() ->{
			tm.saveToFile("Data.xml");
		}));
		sc.scanFromConsole();
	}
}

