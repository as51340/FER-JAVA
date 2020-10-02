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
 * Tree command prints a tree of all files starting from given pathname representing directory
 * @author Andi Škrgat
 * @version 1.0
 */
public class TreeShellCommand implements ShellCommand{

	/**
	 * Print a tree of all files starting from entered directory  
	 * @param env interface Environment for this shell command 
	 * @param arguments all arguments that user entered to console. Only one argument should be entered and that is 
	 * directoy from where its tree structure will be printed
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
			env.writeln("Invalid number of arguments for tree command");
			return ShellStatus.CONTINUE;
		}
		File file;
		try {
			file = new File(list.get(0));
		}  catch(NullPointerException ex) {
			env.writeln("Null passed as name of file");
			return ShellStatus.CONTINUE;
		}
		if(file.exists() == false) {
			env.writeln("Such file doesn't exist");
			return ShellStatus.CONTINUE;
		}
		if(file.isDirectory() == false) {
			env.writeln("Given file is not directory");
			return ShellStatus.CONTINUE;
		}
		printTree(env, file, 0);
		
		return ShellStatus.CONTINUE;
	}
	
	/**
	 * Intern method used for recursive printing
	 * @param env Environment for shell
	 * @param file File from printing starts
	 * @param level of file in tree. For root level is 0
	 */
	private void printTree(Environment env, File file, int level) {
		StringBuilder sb = new StringBuilder();
		sb.append(" ".repeat(level * 2) + file.getName());
		env.writeln(sb.toString());
		if(file != null && file.isDirectory() == true) {
			for (File fileChild: file.listFiles()) {
				if(fileChild != null) {
					printTree(env, fileChild, level + 1);
				}
				
			}
		}
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "tree";
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("The tree command expects a single argument: directory name and prints a tree (each directory level shifts"); 
		list.add("output two charatcers to the right). ");
		return Collections.unmodifiableList(list);
	}

	
}
