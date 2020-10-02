package hr.fer.zemris.java.blog.crypto;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Allows user to calculate hash for given input using SHA-256 algorithm
 * @author Andi Škrgat
 * @version 1.0
 */
public class Crypto {

	/**
	 * Performs password hashing
	 * @param input input that will be hashed
	 * @return hash for given input
	 */
	public static String checksha(String input) {
		byte[] inputArr = input.getBytes(Charset.forName("UTF-8"));
		String hashFinal = null;
		MessageDigest mes = null;
		try {
			mes = MessageDigest.getInstance("SHA-256");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		mes.update(inputArr);
		byte[] binHash = mes.digest();
		hashFinal = Util.byteToHex(binHash);
		return hashFinal;
	}
}
