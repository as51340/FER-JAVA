package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import hr.fer.zemris.java.hw06.shell.ShellUtil;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

/**
 * Command produces hex output of given file.
 * @author Andi Škrgat
 * @version 1.0
 */
public class HexdumpShellCommand implements ShellCommand{

	/**
	 * Produces hex-output of given file.
	 * @param env interface Environment for this shell command 
	 * @param arguments all arguments that user entered to console. Only one argument is expected here, and that is
	 * pathname.
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
			env.writeln("Invalid number of arguments for hexdump command");
			return ShellStatus.CONTINUE;
		}
		File file = null;
		try {
			file = new File(list.get(0));
		} catch(NullPointerException ex) {
			env.writeln("Null passed as name of file");
		}
		try {
			InputStream is = new BufferedInputStream(Files.newInputStream(file.toPath()));
			int i = 0;
			while(is.available() > 0) {
				StringBuilder sb = new StringBuilder();
				StringBuilder endLine = new StringBuilder();
				sb.append(String.format("%08X: ", i * 16));
				boolean finished = false;
				boolean inside = false;
				int k = -1;
				for(int j = 0; j < 16; j++) {
					int b = is.read();
					if(b == -1) {
						finished = true;
						k = j;
						break;
					}
					if(finished == false) {
						sb.append(String.format("%02X", b));
						if(b < 32 || b > 127) {
							endLine.append("."); 
						} else {
							endLine.append((char) b);
						}
					}
					if(j == 7) {
						sb.append("|");
						inside = true;
					} else {
						sb.append((char) 32);
					}
				}
				if(k != -1) {
					if(inside == false) {
						sb.append(" ".repeat(2));
					}
					for(; k < 7; k++) {
						sb.append(" ".repeat(3));
					}
					if(inside == false) {
						sb.append("|");
					}
					for(; k < 16; k++) {
						sb.append(" ".repeat(3));
						if(k == 14 && inside == false) {
							break;
						}
					}
				}
				sb.append("| ");
				sb.append(endLine.toString());
				env.writeln(sb.toString());
				i++;
			}
					
		} catch(IOException ex) {
			env.writeln("Reading failed while copying files");
			return ShellStatus.CONTINUE;
		}
		
		return ShellStatus.CONTINUE;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "hexdump";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Command produces hex-output from given file. Command exptects a single argument, file name and ");
		list.add("prints only a standard subsert of characters. For all other characters '.' is printed instead. ");
		return Collections.unmodifiableList(list);
	}

}
