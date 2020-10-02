package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.ShellUtil;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command opens given file and writes its content to console together.
 * @author Andi Škrgat
 * @version 1.0
 */
public class CatShellCommand implements ShellCommand{

	/**
	 * Method opens file given from console and writes its content to console. If second argument is provided, it is used as charset name to
	 * interpret chars from bytes.
	 * @param env interface Environment for this shell command 
	 * @param arguments all arguments that user entered to console. First is mandatory- path to some file and second is optional - platform charset
	 * that should be used
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
			env.writeln("Invalid number of arguments for cat command");
			return ShellStatus.CONTINUE;
		}
		File file = null;
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
		if(file.isDirectory() == true) {
			env.writeln("Cannot print content of directory");
			return ShellStatus.CONTINUE;
		}
		BufferedReader is = null;
		try {
			if(list.size() == 1) {
				is = new BufferedReader(new FileReader(file, Charset.defaultCharset()));
			} else if(list.size() == 2) { 
				is = new BufferedReader(new FileReader(file,  Charset.forName(list.get(1))));
			}
			while(true) {
				String line = is.readLine();
				if(line == null) {
					break;
				}
				env.writeln(line);
			}
		} catch(IOException ex) {
			env.writeln("Error happened while reading content of file");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "cat";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<>();
		list.add("Command cat takes one or two arguments. The first argument is path to some file and is mandatory.");
		list.add("The second argument is charset name that should be used to interpret chars from bytes.");
		list.add("If not provided, a default platform charset should be used (see java.nio.charset.Charset class for details).");
		list.add("This command opens given file and writes its content to console.");
		return Collections.unmodifiableList(list);
	}

}
