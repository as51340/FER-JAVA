package hr.fer.zemris.java.hw06.shell.commands;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * Exits from shell
 * @author Andi Škrgat
 * @version 1.0
 */
public class ExitShellCommand implements ShellCommand{

	/**
	 * @param env interface Environment for this shell command 
	 * @param arguments all arguments that user entered to console. User shouldn't provide arguments.
	 * @returns ShellStatus after executing command. If command was run properly TERMINATE is returned.
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
			env.writeln("Invalid number of arguments for exit command");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.TERMINATE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "exit";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Exits from shell");
		return Collections.unmodifiableList(list);
	}

}
