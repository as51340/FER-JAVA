package hr.fer.zemris.java.gui.layouts;

/**
 * Util class with method for parsing strings to {@linkplain RCPosition}
 * @author Andi Škrgat
 * @version 1.0
 */
public class Util {
	
	/**
	 * Method that parses user's input and returns {@linkplain RCPosition}
	 * @param text user's input
	 * @returns instance of {@linkplain RCPosition}
	 */
	public static RCPosition parse(String text) {
		String[] arr = text.split(",");
		if(arr.length != 2) {
			throw new IllegalArgumentException("Text cannot be parsed!");
		}
		arr[0] = arr[0].trim();
		arr[1] = arr[1].trim();
		int row, column;
		try {
			row = Integer.parseInt(arr[0]);
			column = Integer.parseInt(arr[1]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Error with parsing properties of RCPosition");
		}
		
		return new RCPosition(row, column);
	}

}
