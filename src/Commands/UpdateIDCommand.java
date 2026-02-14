package Commands;

public class UpdateIDCommand implements Command {
	@Override
	public String getName() {
		return "update";
	}
	@Override
	public String describeCommand(){
		return " -  обновляет значение элемента коллекции, id которого равен заданному";
	}
}
