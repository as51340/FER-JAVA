package hr.fer.zemris.java.hw06.shell.commands;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * Performs copying given file to the destination. Source file cannot be directory and it's allowed that destination is directory.
 * If destination file already exists user is asked permission for overwrite it.
 * @author Andi Škrgat
 * @version 1.0
 */
public class CopyShellCommand implements ShellCommand{

	/**
	 * Copies original file to the destination(file or folder)
	 * @param env interface Environment for this shell command 
	 * @param arguments all arguments that user entered to console. Two arguments are expected here to be entered, 
	 * first the original file and the second destination file. 
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
		if(list.size() != 2) {
			env.writeln("Invalid number of arguments for copy command");
			return ShellStatus.CONTINUE;
		}
		File sourceFile, dest;
		try {
			sourceFile = new File(list.get(0));
		}  catch(NullPointerException ex) {
			env.writeln("Null passed as name of source file");
			return ShellStatus.CONTINUE;
		}
		try {
			dest = new File(list.get(1));
		}  catch(NullPointerException ex) {
			env.writeln("Null passed as name of destination file");
			return ShellStatus.CONTINUE;
		}
		if(sourceFile.exists() == false) {
			env.writeln("Source file doesn't exist");
			return ShellStatus.CONTINUE;
		}
		if(sourceFile.isDirectory() == true) {
			env.writeln("Source file must not be directory");
			return ShellStatus.CONTINUE;
		}
		if(dest.exists() == true) {
			if(dest.isDirectory() == true) {
				dest = new File(list.get(1) + "\\" + sourceFile.getName());
				if(dest.exists() == true) {
					env.writeln("File with given pathname already exists in given directory. Overwrite it? Answer YES for overwriting, anything "
							+ "else for canceling");
					String answer = env.readLine();
					if(answer.toUpperCase().equals("YES") == false) {
						env.writeln("Copying canceled");
						return ShellStatus.CONTINUE; 
					} else {
						try {
							dest.createNewFile();
						} catch (IOException e) {
							env.writeln("File creation failed");
							return ShellStatus.CONTINUE;
						}
					}
				}
			} else {
				env.write("File with given pathname already exists. Overwrite it? Answer YES for overwriting, anything "
						+ "else for canceling");
				String answer = env.readLine();
				if(answer.toUpperCase().equals("YES") == false) {
					env.writeln("Copying canceled");
					return ShellStatus.CONTINUE; 
				}
			}
		} else {
			if(dest.toString().contains(".") == false) {
				if (dest.mkdirs() == false) {
					env.writeln("Failed to create directory!");
					return ShellStatus.CONTINUE; 
		        }
				dest = new File(dest + "\\" + sourceFile.getName());
				try {
					dest.createNewFile();
				} catch (IOException e) {
					env.writeln("File creation failed");
					return ShellStatus.CONTINUE;
				}
			} else {
				String last = dest.toString();
				int index = last.indexOf(dest.getName());
				File dirFile = new File(last.substring(0, index));
				if(dirFile.exists() == false) {
					if (dirFile.mkdirs() == false) {
						env.writeln("Failed to create directory!");
						return ShellStatus.CONTINUE; 
			        }
				}
				try {
					dest.createNewFile();
				} catch (IOException e) {
					env.writeln("File creation failed");
					return ShellStatus.CONTINUE;
				}
			}
		}
		try {
			InputStream br = new BufferedInputStream(new FileInputStream(sourceFile));
			OutputStream bw = new BufferedOutputStream(new FileOutputStream(dest));
			byte[] buff = new byte[1024];
			while(true) {
				int num = br.read(buff);
				if(num < 1) {
					break;
				}
				bw.write(buff, 0, num);
			}
			br.close();
			bw.close();
		} catch(IOException ex) {
			env.writeln("Reading or writing failed while copying files");
			return ShellStatus.CONTINUE;
		}
		return ShellStatus.CONTINUE;	
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "copy";
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("The copy command expects two arguments: source file name and destination file name (i.e. paths and names).");
		list.add("If destination file exists, user is asked is it allowed to overwrite it. Copy command "); 
		list.add("works only with files (no directories). If the second argument is directory, it will be assumed that user wants to"); 
		list.add("copy the original file into that directory using the original file name. ");
		return Collections.unmodifiableList(list);
	}
	

}
