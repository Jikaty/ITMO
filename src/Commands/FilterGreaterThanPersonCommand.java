package Commands;

public class FilterGreaterThanPersonCommand implements Command {
	@Override
	public String getName() {
		return "filter_greater_key";
	}

	@Override
	public String describeCommand(){
		return " - выводит элементы, значение поля person которых больше заданного";
	}
}
