package hr.fer.zemris.java.hw06.crypto;

/**
 * Class that provides methods for converting hex string to byte array and vice versa
 * @author Andi Škrgat
 * @version 1.0
 */
public class Util {

	/**
	 * Converts hex string to byte[]
	 * @param input string representing hex string
	 * @return byte[] filled with bytes from hex string
	 * @throws IllegalArgumentException if input is odd-sized or if there are some invalid characters
	 */
	public static byte[] hexToByte(String input) {
		char[] arr = input.toCharArray();
		int length = arr.length;
		if(length % 2 == 1) {
			throw new IllegalArgumentException("Odd-sized input");
		}
		byte[] ret = new byte[length / 2];
		for (int i = 0; i < length; i = i+ 2) {
			int digit1 = Character.digit(arr[i], 16);
			int digit2 = Character.digit(arr[i+1],16);
			if(digit1 == -1 || digit2 == -1) {
				throw new IllegalArgumentException("Invalid characters");
			}
			ret[i / 2] = (byte) ((digit1 << 4) + digit2);
		}
		return ret;
	}
	
	/**
	 * Converts array of bytes to hex string and uses only lowercase letters for creating encoding 
	 * @param arr array of bytes
	 * @returns hex string
	 * @throws IllegalArgumentException if Character.forDigit can't convert current byte to 2 digits
	 */
	public static String byteToHex(byte[] arr) {
		char[] data = new char[2];
		int length = arr.length;
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < length; i++) {
			data[0] = Character.forDigit((arr[i] >> 4) & 0xF, 16);
			data[1] = Character.forDigit((arr[i] & 0xF), 16);
			if(data[0] == '\0' || data[1] == '\0') {
				throw new IllegalArgumentException("Illegal byte array");
			}
			sb.append(data[0]);
			sb.append(data[1]);
		}
		return sb.toString();
	}
	
}



