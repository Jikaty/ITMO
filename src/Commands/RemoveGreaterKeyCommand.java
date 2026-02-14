package Commands;

public class RemoveGreaterKeyCommand implements Command {
	@Override
	public String getName() {
		return "remove_greater_key";
	}
	@Override
	public String describeCommand(){
		return " - удаляет из коллекции все элементы, ключ которых превышает заданный";
	}
}
