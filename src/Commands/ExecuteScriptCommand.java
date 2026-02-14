package Commands;


public class ExecuteScriptCommand implements Command {
	private boolean executeScript = false;

	public void setExecuteScript(boolean a){
		executeScript = a;
	}
	public boolean getExecuteScript(){
		return executeScript;
	}

	private void executeScript(){
		this.executeScript = true;
	}
	@Override
	public String getName() {
		return "execute_script";
	}
	@Override
	public String describeCommand(){
		return " - скрипт из файла";
	}
}
