package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Abstraction that will be passed to each defined command. The each implemented command communicates with user
 * only through this interface.
 * @author Andi Škrgat
 * @version 1.0
 */
public interface Environment {
	
	/**
	 * Method used for reading from console
	 * @returns String-one line from console
	 * @throws ShellIOException is thrown if reading fails
	 */
	String readLine() throws ShellIOException;
	
	/**
	 * Method used for writing to console
	 * @param text String that will be written
	 * @throws ShellIOException is thrown if writing fails
	 */
	void write(String text) throws ShellIOException;
	
	/**
	 * Writes one line to console
	 * @param text String that will be written
	 * @throws ShellIOException if thrown if writing fails
	 */
	void writeln(String text) throws ShellIOException;
	
	/**
	 * @returns all commands that are part of one shell
	 */
	SortedMap<String, ShellCommand> commands();
	
	/**
	 * @returns multiline symbol for the shell
	 */
	Character getMultilineSymbol();
	
	/**
	 * Sets multiline symbol to the given value
	 * @param symbol new multiline symbol
	 */
	void setMultilineSymbol(Character symbol);
	
	/**
	 * @returns prompt symbol for the shell
	 */
	Character getPromptSymbol();
	
	/**
	 * Sets prompt symbol of shell to the given value
	 * @param symbol new prompt symbol
	 */
	void setPromptSymbol(Character symbol);
	
	/**
	 * @returns moreline symbol for the shell
	 */
	Character getMorelinesSymbol();
	
	/**
	 * Sets moreline symbol of shell to the given value
	 * @param symbol new moreline symbol
	 */
	void setMorelinesSymbol(Character symbol);
}
