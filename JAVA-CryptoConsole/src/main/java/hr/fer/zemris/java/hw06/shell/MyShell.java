package hr.fer.zemris.java.hw06.shell;

import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

import hr.fer.zemris.java.hw06.shell.commands.CatShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CharsetsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.CopyShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.ExitShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HelpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.HexdumpShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.LsShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.MkdirShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.SymbolShellCommand;
import hr.fer.zemris.java.hw06.shell.commands.TreeShellCommand;

/**
 * Command line program that interacts with user. It accepts several methods: help, tree, cat, charset, ls, copy, mkdir, hexdump and symbol 
 * for changing MORELINESYMBOL and MULTILINESYMBOL
 * @author Andi Škrgat
 * @version 1.0
 */
public class MyShell {
	
	/**
	 * Symbol that has been sent to output whenever shell is ready to accept new command. Default value is set to >.
	 */
	private Character PROMPTSYMBOL = '>';
	
	/**
	 * Symbol that allows user to write multiline input. Default value is set to \
	 */
	private Character MORELINESYMBOL = '\\';
	
	/**
	 * This symbol is used to signalize user he asked for multiline input
	 */
	private Character MULTILINESYMBOL = '|';
	
	/**
	 * Map of all commands that shell can execute.
	 */
	SortedMap<String, ShellCommand> commands;
	
	public MyShell() {
		commands = new TreeMap<String, ShellCommand>();
		commands.put("charsets", new CharsetsShellCommand());
		commands.put("cat", new CatShellCommand());
		commands.put("ls", new LsShellCommand());
		commands.put("tree", new TreeShellCommand());
		commands.put("copy", new CopyShellCommand());
		commands.put("mkdir", new MkdirShellCommand());
		commands.put("hexdump", new HexdumpShellCommand());
		commands.put("exit", new ExitShellCommand());
		commands.put("help", new HelpShellCommand());
		commands.put("symbol", new SymbolShellCommand());
	}
	
	/**
	 * @returns new instance of concrete Environment
	 */
	private Environment getInstance() {
		return new EnvironmentImpl(this);
	}
	
	/**
	 * Main method from where program starts
	 * @param args, user doesn't accept any argument
	 */
	public static void main(String[] args) {
		MyShell shell = new MyShell();
		Environment env = shell.getInstance();
		env.writeln("Welcome to MyShell v 1.0");
		ShellStatus status = ShellStatus.CONTINUE;
		try {
			do {
				env.write(shell.PROMPTSYMBOL.toString() + " ");
				StringBuilder sb = new StringBuilder();
				boolean cont = false;
				while(true) {
					if(cont == true) {
						 env.write(env.getMultilineSymbol() + " ");
					}
					String line = env.readLine();
					if(line.length() > 0 && line.charAt(line.length() -1) == env.getMorelinesSymbol()) {
						line = line.substring(0, line.length() -1);
						cont = true;
						sb.append(line);
					} else {
						cont = false;
						sb.append(line);
						break;
					}
				}
				String input = sb.toString();
				//System.out.println(input);
				String commandName = shell.extractCommandName(input);
				String arguments = null;
				if(commandName == null) {
					continue;
				}
				if(env.commands().containsKey(commandName) == true) {
					arguments = shell.extractArguments(input, commandName);
				} else {
					env.writeln("Command name not known");
					continue;
				}
				ShellCommand command = env.commands().get(commandName);
				status = command.executeCommand(env, arguments);
			} while(status == ShellStatus.CONTINUE);
		} catch(ShellIOException ex) {
			env.writeln(ex.getMessage());
		}
		
	}
	
	/**
	 * Private method that is internally used for extracting command name from input
	 * @param input user's input
	 * @returns command name
	 */
	@SuppressWarnings("deprecation")
	private String extractCommandName(String input) {
		char[] data = input.toCharArray();
		int length = data.length;
		int i = 0;
		StringBuilder sb = new StringBuilder();
		while(i < length && Character.isSpace(data[i]) == true) {
			i++;
		}
		if(i >= data.length) {
			//System.out.println("Returned null");
			return null;
		}
		while(i <length && Character.isSpace(data[i]) == false) {
			sb.append(data[i++]);
		}
		return sb.toString();
	}
	
	/**
	 * Private method for extracting arguments from input if valid commandName was entered
	 * @param input user's input
	 * @param commandName name of command that user entered
	 * @returns String representing whole user's input, without command name, as one line
	 */
	@SuppressWarnings("deprecation")
	private String extractArguments(String input, String commandName) {
		char[] data = input.toCharArray();
		int length = data.length;
		int i = 0;
		while(i < length && Character.isSpace(data[i]) == true) {
			i++;
		}
		int nameLength = commandName.length();
		int j = i;
		for(; j <= i + nameLength; j++) {
		}
		if(j >= length) {
			return "";
		} 
		return input.substring(j);
	}
	
	
	
	/**
	 * Conrecte implementation of interface Environment in our shell
	 * @author Andi Škrgat
	 * @version 1.0
	 */
	private static class EnvironmentImpl implements Environment {

		/**
		 * Reference to the shell 
		 */
		MyShell shell;
		
		/**
		 * Scanner used for reading line from console
		 */
		private Scanner sc;
		
		/**
		 * Default contructor that accepts reference to the shell.
		 */
		public EnvironmentImpl(MyShell shell) {
			this.shell = shell;
			sc = new Scanner(System.in);
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public String readLine() throws ShellIOException {
			String line;
			line = sc.nextLine();
			return line;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void write(String text) throws ShellIOException {
			System.out.print(text);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void writeln(String text) throws ShellIOException {
			System.out.println(text);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public SortedMap<String, ShellCommand> commands() {
			return Collections.unmodifiableSortedMap(shell.commands);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Character getMultilineSymbol() {
			return shell.MULTILINESYMBOL;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setMultilineSymbol(Character symbol) {
			shell.MULTILINESYMBOL = symbol;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Character getPromptSymbol() {
			return shell.PROMPTSYMBOL;
		}
		
		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setPromptSymbol(Character symbol) {
			shell.PROMPTSYMBOL = symbol;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Character getMorelinesSymbol() {
			return shell.MORELINESYMBOL;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void setMorelinesSymbol(Character symbol) {
			shell.MORELINESYMBOL = symbol;
		}
		
	}

}
