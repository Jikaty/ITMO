package Service;

import Commands.Command;
import Commands.TypeOfArgument;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
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
	private static String filenameFromPerson;
	private static List<String> fileList = new ArrayList<>();

	private final CommandManager commandManager = CommandManager.getInstance();

	public void setFilenameFromPerson(String filenameFromPerson) {
		ScriptAndPeapleScanner.filenameFromPerson = filenameFromPerson;
	}

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
			if ((commandAndKey.length == 2) ) {
				TypeOfArgument tp = (TypeOfArgument) commandManager.getCommand(commandName);
				if(tp.typeOfArgument().equals("Integer")){
					try {
						cmd.setKey(Integer.parseInt(commandAndKey[1]));
					} catch (NumberFormatException e) {
						System.out.println("Second argument must be an integer");
						continue;
					}
				}
				if(tp.typeOfArgument().equals("String")){
					filenameFromPerson = commandAndKey[1];
					if(fileList.contains(filenameFromPerson)) {
						System.out.println("Cyclic dependency in the file");
						return;
					}

					fileList.add(filenameFromPerson);
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
				if ((commandAndKey.length == 2) ) {
					TypeOfArgument tp = (TypeOfArgument) commandManager.getCommand(commandName);
					if(tp.typeOfArgument().equals("Integer")){
						try {
							cmd.setKey(Integer.parseInt(commandAndKey[1]));
						} catch (NumberFormatException e) {
							System.out.println("Second argument must be an integer");
							continue;
						}
					}
					if(tp.typeOfArgument().equals("String")){
						filenameFromPerson = commandAndKey[1];
						if(fileList.contains(filenameFromPerson)) {
							System.out.println("Cyclic dependency in the file");
							return;
						}

						fileList.add(filenameFromPerson);
					}
				}
				cmd.executeCommand();
			}
		}
		catch(Exception e) {

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
				String file = filenameFromPerson;
				scannerFromFile = new Scanner(new File(file));
			} catch(Exception e) {
				System.out.println("Haven't file or permission");
			}
			return scannerFromFile;
		} else {
			return scannerFromFile;
		}
	}
}
