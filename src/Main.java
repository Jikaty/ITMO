
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
		String fileName = System.getenv("COMMAND_FILE");
		tm.loadFromFile(fileName);
		Runtime.getRuntime().addShutdownHook(new Thread(() ->{
			tm.saveToFile(fileName);
		}));
		if(args.length !=0 && args[args.length-1].endsWith(".txt")) {
			String fileScriptName = args[args.length-1];
			sc.setFilenameFromPerson(fileScriptName);
			sc.scanFromFile();
			System.exit(0);
		}
		sc.scanFromConsole();
	}
}

