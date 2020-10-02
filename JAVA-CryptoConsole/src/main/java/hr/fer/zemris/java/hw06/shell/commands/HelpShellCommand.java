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
 * Command for getting names of all available commands in shell or for getting usage instructions for specific method
 * @author Andi Škrgat
 * @version 1.0
 */
public class HelpShellCommand implements ShellCommand{

	
	/**
	 * Prints names of all available commands if user didn't enter any argument or description of specific command
	 * if there was one argument entered 
	 * @param env interface Environment for this shell command 
	 * @param arguments all arguments that user entered to console. It is expected to be entered 0 or 1 arguments.
	 * @returns ShellStatus after executing command. If everything went as expected CONTINUE is always returned.
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
		if(list.size() > 1) {
			env.writeln("Invalid number of arguments for help command");
			return ShellStatus.CONTINUE;
		}
		if(list.size() == 1) {
			String commandName = list.get(0);
			if(env.commands().containsKey(commandName) == false) {
				env.writeln("Unknown command");
				return ShellStatus.CONTINUE;
			}
			List<String> usage = env.commands().get(commandName).getCommandDescription();
			for(String line :usage) {
				env.writeln(line);
			}
		} else {
			for(String commandName:env.commands().keySet()) {
				env.writeln(commandName);
			}
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "help";
	}

	/**
	 * @returns description of what command is capable of and how to use it
	 */
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("If method is called without arguments then commands outputs names of all available commands. ");
		list.add("If argument is provided then it explains user how to use entered command");
		return Collections.unmodifiableList(list);
	}

}
