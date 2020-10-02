package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * Class with method for splitting arguments
 * @author Andi Škrgat
 * @version 1.0
 */
public class ShellUtil {
	
	/**
	 * Splits arguments and returns list of parsed inputs
	 * @param argument string one line represing whole input
	 * @returns List of all splitted arguments
	 */
	@SuppressWarnings("deprecation")
	public static List<String> split(String argument) {
		List<String> list = new ArrayList<String>();
		char[] data = argument.toCharArray();
		int length = data.length;
		boolean inside = false;
		int i = 0;
		while(i < length && Character.isSpace(data[i]) == true) { //skip blanks
			i++;
		}
		StringBuilder sb = new StringBuilder();
		while(i < length) {
			if(inside == false) {
				if(data[i] == '\"') {
					inside = true;
					i++;
				} else if(Character.isSpace(data[i]) == true) {
					list.add(sb.toString());
					while(i < length && Character.isSpace(data[i]) == true) { //skip blanks
						i++;
					}
					sb = new StringBuilder();	
				} else {
					sb.append(data[i++]);
				}
			} else {
				if(data[i] == '\\' && i < length - 1 && (data[i+1] == '\\' || data[i+1] == '\"')) {
					sb.append(data[i+1]); //escaping inside string
					i += 2;
				} else if(data[i] == '\"') {
					inside = false;
					i++;
					if(i < length && Character.isSpace(data[i]) == false) {
						throw new ShellIOException("Parsing error, invalid double-quote ending");
					}
				} else {
					sb.append(data[i++]);
				}
			}
		}
		if(inside == true) {
			throw new ShellIOException("Parsing error, no double-quote ending");
		} else if(sb.length() > 0){
			list.add(sb.toString());
		}
		return list;
}


}
