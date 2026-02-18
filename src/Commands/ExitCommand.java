package Commands;

import Service.ScriptAndPeapleScanner;

public class ExitCommand implements Command{
//	@Override
//	public void executeCommand() {
//		System.out.println("Конец работы");
//		System.exit(0);
//	}
	@Override
	public void executeCommand() {
		System.out.println("Конец работы");
		ScriptAndPeapleScanner.setStopFlag(true);
	}
	@Override
	public String describeCommand(){
		return " - завершить программу без сохранения";
	}

}
