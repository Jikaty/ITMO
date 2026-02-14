package Commands;

public class MinByTypeCommand implements  Command{
	@Override
	public String getName() {
		return "min_by_type";
	}
	@Override
	public String describeCommand(){
		return " - выводит любой объект из коллекции, значение поля type которого является минимальным";
	}
}
