import Commands.ExecuteScriptCommand;
import Service.ScriptAndPeapleScanner;

public class Main {
	public static void main(String[] args) {
		ScriptAndPeapleScanner sc = new ScriptAndPeapleScanner();
		ExecuteScriptCommand s = new ExecuteScriptCommand();
		if(!s.getExecuteScript()){
			sc.scanFromConsole();
		}else {
			sc.scanFromFile();
		}
	}
}

