package server.Commands;

import java.io.FileNotFoundException;

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
	String executeCommand() throws FileNotFoundException;

	default void setArgs(Object args){}
	default Object getArgs(){return null;}
	/**
	 * Describe command string.
	 *
	 * @return the string
	 */
	String describeCommand();
	/**
	 * Needs argument boolean.
	 *
	 * @return the boolean
	 */
	default boolean needsArgument(){
		return false;
	}
	default boolean needMoreThenOneArgument(){
		return false;
	}
}
