package hr.fer.zemris.java.hw06.shell.commands;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * Command charsets takes no arguments and lists names of supported charsets on this platform. A single charset name 
 * is written per line
 * @author Andi Škrgat
 * @version 1.0
 */
public class CharsetsShellCommand implements ShellCommand{

	/**
	 * Lists all available charsets that can be used on this platform.
	 * @param env interface Environment for this shell command 
	 * @param arguments all arguments that user entered to console. In this case there shouldn't be any argument entered
	 * @returns ShellStatus.CONTINUE
	 */
	@Override
	public ShellStatus executeCommand(Environment env, String arguments) {
		List<String> list;
		try {
			list = ShellUtil.split(arguments);
		} catch(ShellIOException ex) {
			env.writeln(ex.getMessage());
			return ShellStatus.CONTINUE;
		}
		if(list.size() != 0) {
			env.writeln("Invalid number of arguments for charsets command");
			return ShellStatus.CONTINUE;
		}
		for(String entry: Charset.availableCharsets().keySet()) {
			env.writeln(entry);
		}
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return new String("charsets");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("This command is used for obtaining all available charsets from this platform.");
		list.add("Command accepts no arguments.");
		list.add("A single charset name is written per line.");
		return Collections.unmodifiableList(list);
	}
}
