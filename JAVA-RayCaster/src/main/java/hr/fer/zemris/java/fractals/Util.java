package hr.fer.zemris.java.fractals;

import hr.fer.zemris.math.Complex;

/**
 * Class with method for generating complex number from input entered by user
 * @author Andi Škrgat
 * @version 1.0
 */
public class Util {
	
	/**
	 * @param input, user's input
	 * @returns generated complex number from input or throws IllegalArgumentException if input is not regular
	 * @throws IllegalArgumentException if input is not regular
	 */
	@SuppressWarnings("deprecation")
	public static Complex getComplex(String input) {
		if(input.isEmpty() == true) {
			throw new IllegalArgumentException("Input must not be empty.");
		}
		char[] arr = input.toCharArray();
		int i = 0;
		int length = arr.length;
		input = input.trim();
		if(i >= length) {
			throw new IllegalArgumentException("Only spaces in input.");
		}
		double re = 0, im = 0;
		boolean reGenerated = false;
		if(Character.isDigit(arr[i]) == true) {
			StringBuilder number = new StringBuilder();
			number.append(arr[i++]);
			if(i < length && arr[i] == '.') {
				number.append(".");
				i++;
				while(i < length && Character.isDigit(arr[i]) == true) {
					number.append(arr[i++]);
				}
			}
			try {
				re = Double.parseDouble(number.toString());
			} catch(NumberFormatException ex) {
				throw new IllegalArgumentException("Decimal point error");
			}
			reGenerated = true;
		} else if(arr[i] == '-') {
			i++;
			if(i < length) {
				if(Character.isDigit(arr[i]) == true) {
					StringBuilder number = new StringBuilder();
					number.append(arr[i++]);
					if(i < length && arr[i] == '.') {
						number.append(".");
						i++;
						while(i < length && Character.isDigit(arr[i]) == true) {
							number.append(arr[i++]);
						}
					}
					try {
						re = -Double.parseDouble(number.toString());
					} catch(NumberFormatException ex) {
						throw new IllegalArgumentException("Decimal point error");
					}
					reGenerated = true;
				} else if(arr[i] == 'i') { //imaginary found
					i++;
					if(i < length) {
						if(Character.isDigit(arr[i]) == true) { //if digit after i
							StringBuilder number = new StringBuilder();
							number.append(arr[i++]);
							if(i < length && arr[i] == '.') {
								number.append(".");
								i++;
								while(i < length && Character.isDigit(arr[i]) == true) {
									number.append(arr[i++]);
								}
							}
							try {
								im = -Double.parseDouble(number.toString());
							} catch(NumberFormatException ex) {
								throw new IllegalArgumentException("Decimal point error");
							}
							return new Complex(re, im);
						} else {  //if not digit and something else is then error
							throw new IllegalArgumentException("Not regular character behind i");
						}
					} else { //if nothing begind i = 1
						im = -1;
						return new Complex(re, im);
					}
				} else {
					throw new IllegalArgumentException("Illegal input");
				}
			} else {
				throw new IllegalArgumentException("Minus without number.");
			}
		}
		if(i >= length) { //only real part founded
			return new Complex(re, im);
		}
		
		while(i < length && Character.isSpace(arr[i]) == true) { //skip blanks between real part and operator
			i++;
		}
		if(i >= length) {
			return new Complex(re, im);
		}
		boolean minusImg = false;
		if(reGenerated == true) {
			if(arr[i] == '+' || arr[i] == '-') {
				if(arr[i] == '-') {
					minusImg = true;
				}
				i++;
			} else {
				throw new IllegalArgumentException("Operator not regular.");
			}
		}
		
		
		while(i < length && Character.isSpace(arr[i]) == true) { //skip blanks between operator and imaginary part
			i++;
		}
		
		if(arr[i] != 'i') {
			throw new IllegalArgumentException("No imaginary part.");
		}
		i++;
		if(i < length) {
			if(Character.isDigit(arr[i]) == true) { //if digit after i
				StringBuilder number = new StringBuilder();
				number.append(arr[i++]);
				if(i < length && arr[i] == '.') {
					number.append(".");
					i++;
					while(i < length && Character.isDigit(arr[i]) == true) {
						number.append(arr[i++]);
					}
				}
				try {
					im = Double.parseDouble(number.toString());
				} catch(NumberFormatException ex) {
					throw new IllegalArgumentException("Decimal point error");
				}
				if(minusImg == true) {
					im *= -1;
				}
				return new Complex(re, im);
			} else {  //if not digit and something else is then error
				throw new IllegalArgumentException("Not regular character behind i");
			}
		} else { //if nothing begin i = 1
			im = 1;
			if(minusImg == true) {
				im *= -1;
			}
			return new Complex(re, im);
		}
	}	

}
