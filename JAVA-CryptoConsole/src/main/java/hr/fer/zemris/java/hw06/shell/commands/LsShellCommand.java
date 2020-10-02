package hr.fer.zemris.java.hw06.shell.commands;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellIOException;
import hr.fer.zemris.java.hw06.shell.ShellStatus;
import hr.fer.zemris.java.hw06.shell.ShellUtil;

/**
 * Command ls takes a single argument – directory – and writes a directory listing (not recursive). 
 * @author Andi Škrgat
 * @version 1.0
 */
public class LsShellCommand implements ShellCommand{

	/**
	 * Writes a directory listing with informations about each object. See command description for better understanding.
	 * @param env interface Environment for this shell command 
	 * @param arguments all arguments that user entered to console
	 * @returns ShellStatus after executing command. It should always return CONTINUE.
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
			env.writeln("Invalid number of arguments for ls command");
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
		for (File fileChild: file.listFiles()) {
			StringBuilder sb = new StringBuilder();
			if(fileChild.isDirectory() == true) {
				sb.append('d');
			} else {
				sb.append('-');
			}
			if(fileChild.canRead() == true) {
				sb.append('r');
			} else {
				sb.append('-');
			}
			if(fileChild.canWrite() == true) {
				sb.append('w');
			} else {
				sb.append('-');
			}
			if(fileChild.canExecute() == true) {
				sb.append('x');
			} else {
				sb.append('-');
			}
			if(fileChild.isDirectory() == true) {
				sb.append(String.format("%10s", folderSize(fileChild)));
			} else {
				sb.append(String.format("%10s", fileChild.length()));
			}
			sb.append(' ');
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BasicFileAttributeView faView = Files.getFileAttributeView(
			fileChild.toPath(), BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
			);
			BasicFileAttributes attributes;
			try {
				attributes = faView.readAttributes();
			} catch (IOException e) {
				env.writeln("Failed getting attributes");
				return ShellStatus.CONTINUE;
			}
			FileTime fileTime = attributes.creationTime();
			String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
			sb.append(formattedDateTime + ' ');
			sb.append(fileChild.getName());
			env.writeln(sb.toString());
		}
		return ShellStatus.CONTINUE;
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getCommandName() {
		return "ls";
	}

	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<String> getCommandDescription() {
		List<String> list = new ArrayList<String>();
		list.add("Command ls takes a single argument – directory – and writes a directory listing (not recursive).");
		list.add("The output consists of 4 columns. First column indicates if current object is directory (d), readable (r)"); 
		list.add("writable (w) and executable (x). Second column contains object size in bytes that is right aligned and "); 
		list.add("occupies 10 characters. Follows file creation date/time and finally file name.");
		return Collections.unmodifiableList(list);
	}
	
	/**
	 * Private method used for getting size of folder
	 * @param folder source folder
	 * @returns size of folder in bytes
	 */
	private long folderSize(File folder) {
		long size = 0;
	    for(File file: folder.listFiles()) {
	    	if(file.isFile() == true) {
	    		size += file.length();
	    	} else {
	    		size += folderSize(file);
	    	}
	    }
	    return size;
	}
	

}
