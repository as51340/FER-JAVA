package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Provides all necessary methods for one shell command
 * @author Andi Škrgat
 * @version 1.0
 */
public interface ShellCommand {

	/**
	 * Executes command.
	 * @param env interface Environment for one shell command 
	 * @param arguments all arguments that user entered to console
	 * @returns ShellStatus after executing command
	 */
	ShellStatus executeCommand(Environment env, String arguments);
	
	/**
	 * @returns name of command
	 */
	String getCommandName();
	
	/**
	 * @returns description of command. This method is usually used together with method for help. 
	 */
	List<String> getCommandDescription();
}
