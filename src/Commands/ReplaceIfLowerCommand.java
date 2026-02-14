package Commands;

public class ReplaceIfLowerCommand implements Command {
	@Override
	public String getName() {
		return "replace_if_lowe";
	}
	@Override
	public String describeCommand(){
		return " -  заменяет значение по ключу, если новое значение меньше старого";
	}
}
