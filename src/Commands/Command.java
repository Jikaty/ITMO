package Commands;

/**
 * The interface Command.
 */
public interface Command {
	/**
	 * Get name string.
	 *
	 * @return the string
	 */
	default String getName(){
		String commandName = this.getClass().getSimpleName();
		commandName = commandName.replace("Command", "").toLowerCase();
		return commandName;
	}

	/**
	 * Execute command.
	 */
	default void executeCommand(){

	}

	/**
	 * Describe command string.
	 *
	 * @return the string
	 */
	String describeCommand();

	/**
	 * Set key.
	 *
	 * @param key the key
	 */
	default void setKey(Integer key){
	}

	/**
	 * Needs argument boolean.
	 *
	 * @return the boolean
	 */
	default boolean needsArgument(){
		return false;
	}
}
