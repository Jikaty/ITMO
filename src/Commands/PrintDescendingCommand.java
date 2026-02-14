package Commands;

public class PrintDescendingCommand implements Command {
	@Override
	public String getName() {
		return "print_descending";
	}
	@Override
	public String describeCommand(){
		return " - выводит элементы коллекции в порядке убывания";
	}
}
