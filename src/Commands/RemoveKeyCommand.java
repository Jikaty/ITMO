package Commands;

public class RemoveKeyCommand implements Command{
	@Override
	public String getName() {
		return "remove_key";
	}
	@Override
	public String describeCommand(){
		return " - удаляет элемент из коллекции по его ключу";
	}
}
