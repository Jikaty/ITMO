package Commands;

public class RemoveLowerKeyCommand implements Command {
	@Override
	public String getName() {
		return "remove_lower_key";
	}
	@Override
	public String describeCommand(){
		return " - удаляет из коллекции все элементы, ключ которых меньше, чем заданный";
	}
}
