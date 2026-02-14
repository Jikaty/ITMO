package Commands;

public class ShowCommand implements Command{
	@Override
	public String describeCommand(){
		return " -  выводит в стандартный поток вывода все элементы коллекции в строковом представлении";
	}
}
