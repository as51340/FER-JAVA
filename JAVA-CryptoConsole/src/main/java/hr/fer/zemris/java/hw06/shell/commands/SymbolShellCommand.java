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
 * Class used for getting current specific symbols(PROMPT, MORELINE, MULTILINE) and for changing those 
 * symbols.
 * @author Andi Škrgat
 * @version 1.0
 */
public class SymbolShellCommand implements ShellCommand{

	/**
	 * @param env interface Environment for this shell command 
	 * @param arguments all arguments that user entered to console. If one argument is entered then current symbol 
	 * is printed. If two, then specific symbol will be updated.
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
		if(list.size() < 1 || list.size() > 2) {
			env.writeln("Invalid number of arguments for symbol command");
			return ShellStatus.CONTINUE;
		}
		if(list.size() == 1) {
			if(list.get(0).equals("PROMPT") == true) {
				env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
			} else if(list.get(0).equals("MORELINES") == true) {
				env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
			}  else if(list.get(0).equals("MULTILINE") == true) {
				env.writeln("Symbol for MULTILINE is '" + env.getMultilineSymbol() + "'");
			} else {
				env.writeln("Invalid name of symbol");
			}
		} else if(list.size() == 2) {
			if(list.get(1).length() > 1) {
				env.writeln("Invalid new specific symbol");
				return ShellStatus.CONTINUE;
			}
			if(list.get(0).equals("PROMPT") == true) {
				env.writeln("Symbol for PROMPT changed from '" + env.getPromptSymbol() + "' to '" + list.get(1) +"'" );
				env.setPromptSymbol(list.get(1).toCharArray()[0]);
			} else if(list.get(0).equals("MORELINES") == true) {
				env.writeln("Symbol for MORELINES changed from  '" + env.getMorelinesSymbol() + "' to '" + list.get(1) + "'");
				env.setMorelinesSymbol(list.get(1).toCharArray()[0]);
			}  else if(list.get(0).equals("MULTILINE") == true) {
				env.writeln("Symbol for MULTILINE changed from  '" + env.getMorelinesSymbol() + "' to '" + list.get(1) + "'");
				env.setMultilineSymbol(list.get(1).toCharArray()[0]);
			} else {
				env.writeln("Invalid name of symbol");
			}
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "symbol";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command is used with one or two arguments");
		list.add("If one argument is entered, then current specifically named symbol is printed. ");
		list.add("If user entered two arguments then this command is used for changing that symbol");
		return Collections.unmodifiableList(list);
	}

}
