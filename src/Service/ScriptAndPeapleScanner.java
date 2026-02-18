package Service;

import Commands.Command;
import lombok.Getter;
import lombok.Setter;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


@Getter
public class ScriptAndPeapleScanner {

private final Scanner scannerFromConsole = new Scanner(System.in);
private final CommandManager commandManager = new CommandManager();
private String commandName;
private Integer key;
@Setter
private static boolean stopFlag = false;

	public void scanFromConsole() {
		while(!stopFlag) {
			String line = scannerFromConsole.nextLine();
			String[] commandAndKey = line.trim().split("\\s+");
			this.commandName = commandAndKey[0];
			Command cmd = commandManager.getCommand(commandName);
			if (cmd == null) {
				System.out.println("Нет такой команды");
				continue;
			}
			if(cmd.needsArgument() && commandAndKey.length != 2) {
				System.out.println("Сука где второй аргумент");
				continue;
			}
			if (commandAndKey.length == 2) {
				try {
					cmd.setKey(Integer.parseInt(commandAndKey[1]));
				} catch (NumberFormatException e) {
					System.out.println("Второе нихуя не число");
					continue;
				}
			} else if (commandAndKey.length > 2) {
				System.out.println("Ну у меня макс 2 значения в коммандах, так что сорри");
				continue;
			}
			cmd.executeCommand();
		}
	}



	public void scanFromFile() {
		try{
			Scanner scannerFromFile = new Scanner(new File("Sample.txt"));
			while (scannerFromFile.hasNextLine()) {
				String line = scannerFromFile.nextLine();
				String[] commandAndKey = line.trim().split("\\s+");
				this.key = null;
				if (commandAndKey.length == 2) {
					try{
						this.key  = Integer.parseInt(commandAndKey[1]);
						this.commandName = commandAndKey[0];
					} catch (NumberFormatException e) {
						System.out.println("sad");
					}
				} else if (commandAndKey.length == 1) {
					this.commandName  = commandAndKey[0];
				} else {
					System.out.println("Ну у меня макс 2 значения в коммандах, так что сорри");
				}
			}
			System.out.println("Файл полностью обработан");

		}
		catch(FileNotFoundException e){
			System.out.println("Нет такого файла, давай другой");
		}
	}

}
