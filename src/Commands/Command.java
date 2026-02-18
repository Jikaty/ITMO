package Commands;

public interface Command {
	default String getName(){
		String commandName = this.getClass().getSimpleName();
		commandName = commandName.replace("Command", "").toLowerCase();
		return commandName;
	}
	default void executeCommand(){

	}
	String describeCommand();

	default void setKey(Integer key){
	}

	default boolean needsArgument(){
		return false;
	}
}
