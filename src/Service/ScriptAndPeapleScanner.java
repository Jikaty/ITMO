package Service;

import Commands.Command;
import Commands.ExitCommand;
import lombok.Getter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@Getter
public class ScriptAndPeapleScanner {
private final Scanner scannerFromConsole = new Scanner(System.in);
private CommandManager commandManager = new CommandManager();
private String commandName;
private Integer key;
private final boolean stopFlag = false;

	public void scanFromConsole() {
		ExitCommand sc =  new ExitCommand();
		while(!stopFlag) {
			String line = scannerFromConsole.nextLine();
			String[] commandAndKey = line.trim().split("\\s+");
			if (commandAndKey.length == 2) {
				try {
					this.key = Integer.parseInt(commandAndKey[1]);
					this.commandName = commandAndKey[0];
					Command cmd = commandManager.getCommand(commandName);
					if (cmd != null) {
						cmd.executeCommand();
					} else {
						System.out.println("Нет такой команды");
					}
				} catch (NumberFormatException e) {
					System.out.println("Второе нихуя не число");
				}
			} else if (commandAndKey.length == 1) {
				this.commandName = commandAndKey[0];
				Command cmd = commandManager.getCommand(this.commandName);
				if (cmd != null) {
					cmd.executeCommand();
				} else{
					System.out.println("Нет такой команды");
				}
			} else {
				System.out.println("Ну у меня макс 2 значения в коммандах, так что сорри");
			}
		}
	}

	public void scanFromFile() {
		try{
			Scanner scannerFromFile = new Scanner(new File("Sample.txt"));
			if(scannerFromFile.hasNextLine()) {
				String line = scannerFromFile.nextLine();
				String[] commandAndKey = line.trim().split("\\s+");
				this.key = null;
				if (commandAndKey.length == 2) {
					try{
						this.key  = Integer.parseInt(commandAndKey[1]);
						this.commandName = commandAndKey[0];
					} catch (NumberFormatException e) {

					}
				} else if (commandAndKey.length == 1) {
					this.commandName  = commandAndKey[0];
				} else {
					System.out.println("Ну у меня макс 2 значения в коммандах, так что сорри");
				}
			}else{
				System.out.println("Файл полностью обработан");
			}
		}
		catch(FileNotFoundException e){
			System.out.println("Нет такого файла, давай другой");
		}
	}
}
