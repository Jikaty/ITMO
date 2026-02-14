package Commands;

public class ExitCommand implements Command{
	@Override
	public void executeCommand() {
		System.out.println("Конец работы");
		System.exit(0);
	}
	@Override
	public String describeCommand(){
		return " - завершить программу без сохранения";
	}
}
