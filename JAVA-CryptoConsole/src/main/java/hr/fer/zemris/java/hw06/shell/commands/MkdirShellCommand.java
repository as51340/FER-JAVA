package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * This command creates new directory structure from given parameter
 * @author Andi Škrgat
 * @version 1.0
 */
public class MkdirShellCommand implements ShellCommand{

	/**
	 * Creates new directory structure for given pathname. If already exists, user will be informed and shell can be used again.
	 * @param env interface Environment for this shell command 
	 * @param arguments all arguments that user entered. Here it should be only one parameter-pathname of new directory.
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
		if(list.size() != 1) {
			env.writeln("Invalid number of arguments for mkdir command");
			return ShellStatus.CONTINUE;
		}
		File file;
		try {
			file = new File(list.get(0));
		}  catch(NullPointerException ex) {
			env.writeln("Null passed as name of file");
			return ShellStatus.CONTINUE;
		}
		if(file.exists() == true && file.isDirectory() == true) {
			env.writeln("Directory already exists");
			return ShellStatus.CONTINUE;
		}
		if (file.mkdirs() == false) {
			env.writeln("Failed to create directory!");
        }
		
		return ShellStatus.CONTINUE;
	}


	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "mkdir";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Mkdir attempts to create a directory named pathname with all its subdirectories(if entered from user).");
		list.add("Only one parameter must be entered and that is pathname");
		list.add("In any other case, shell will signalize that arguments are as expected and will continue to");
		list.add("the next command.");
		return Collections.unmodifiableList(list);
		
	}
	

}
