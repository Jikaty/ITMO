package Service;

import Commands.Command;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.util.Scanner;

/**
 * The type Script and peaple scanner.
 */
@Setter
@Getter
public class ScriptAndPeapleScanner {

	private final Scanner scannerFromConsole = new Scanner(System.in);
	private String commandName;
	private boolean scannerFlag = false;
	@Setter
	private static boolean stopFlag = false;

	private final CommandManager commandManager = CommandManager.getInstance();


	/**
	 * Gets scanner flag.
	 *
	 * @return the scanner flag
	 */
	public boolean getScannerFlag() {
		return scannerFlag;
	}

	/**
	 * Scan from console.
	 */
	public void scanFromConsole() {
		while(!stopFlag) {
			CreateTicket.setScanner(scannerFromConsole, false);
			String line = scannerFromConsole.nextLine();
			String[] commandAndKey = line.trim().split("\\s+");
			this.commandName = commandAndKey[0];
			Command cmd = commandManager.getCommand(commandName);
			if (cmd == null) {
				System.out.println("We haven't this command");
				continue;
			}
			if (!cmd.needsArgument() && commandAndKey.length >=2) {
				System.out.println("Ur command consist only one word");
				continue;
			}
			if(cmd.needsArgument() && commandAndKey.length != 2) {
				System.out.println("Please use help command if u dont know which command we have");
				continue;
			}
			if (commandAndKey.length == 2) {
				try {
					cmd.setKey(Integer.parseInt(commandAndKey[1]));
				} catch (NumberFormatException e) {
					System.out.println("Second argument must be an integer");
					continue;
				}
			}
			cmd.executeCommand();
		}
	}


	/**
	 * Scan from file.
	 */
	public void scanFromFile() {
		try{
			Scanner fileScanner = createScannerFromFile();
			while (fileScanner.hasNextLine() && !stopFlag) {
				CreateTicket.setScanner(fileScanner, true);
				String line = fileScanner.nextLine();
				String[] commandAndKey = line.trim().split("\\s+");
				try {
					this.commandName = commandAndKey[0];
				} catch (Exception e) {
					continue;
				}
				Command cmd = commandManager.getCommand(commandName);
				if (cmd == null) {
					System.out.println("We haven't this command");
					continue;
				}
				if (!cmd.needsArgument() && commandAndKey.length >=2) {
					System.out.println("Ur command consist only one word");
					continue;
				}
				if(cmd.needsArgument() && commandAndKey.length != 2) {
					System.out.println("Please use help command if u dont know which command we have");
					continue;
				}
				if (commandAndKey.length == 2) {
					try {
						cmd.setKey(Integer.parseInt(commandAndKey[1]));
					} catch (NumberFormatException e) {
						System.out.println("Second argument must be an integer");
						continue;
					}
				}
				cmd.executeCommand();
			}
		}
		catch(Exception e) {
			System.out.println("That's all");
		}

	}

	/**
	 * The Scanner from file.
	 */
	Scanner scannerFromFile = null;

	/**
	 * Create scanner from file scanner.
	 *
	 * @return the scanner
	 */
	public Scanner createScannerFromFile() {
		if(scannerFromFile == null){
			try{
				String fileName = System.getenv("COMMAND_FILE");
				scannerFromFile = new Scanner(new File(fileName));
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
			return scannerFromFile;
		} else {
			return scannerFromFile;
		}
	}
}
