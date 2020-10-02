package hr.fer.zemris.java.hw06.crypto;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.Scanner;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

/**
 * Allows user to encrypt and decrypt given file using AES cryptoalgorithm and 128-bit encryption key or calculate 
 * and check the SHA-256 file digest.
 * @author Andi Škrgat
 * @version 1.0
 */
public class Crypto {

	/**
	 * Main method from where program starts
	 * @param args are name of method what will be called(encrypt, decrypt, chechsha) and needed names of file to run specific method. 
	 * If checksha is first argument then second one must be name of the file which hash we want to check. If encrypt is called then second argument is name of file that we want to encrypt and third output file. 
	 * If decrypt is called then second argument is encrypted file and third output file
	 * @throws IllegalArgumentException if arguments from console are not given as expected. 
	 */
	public static void main(String[] args) {
		if(args.length < 1) {
			throw new IllegalArgumentException("No argument provided");
		}
		if(args[0].equals("checksha") == true) {
			if(args.length != 2) {
				throw new IllegalArgumentException("Checksha can't be called with this arguments");
			} else {
				checksha(args[1]);
			}
		} else if(args[0].equals("encrypt") == true) {
			if(args.length != 3) {
				throw new IllegalArgumentException("Encrypt can't be called with this arguments");
			} else {
				cripting(args[1], args[2], true);
			}
		} else if(args[0].equals("decrypt") == true) {
			if(args.length != 3) {
				throw new IllegalArgumentException("Decrypt can't be called with this arguments");
			} else {
				cripting(args[1], args[2], false);
			}
		} else {
			throw new IllegalArgumentException("Wrong arguments provided");
		}
	}
	
	/**
	 * Method that is called either for encrypting or decrypting file given from source. We know which job has to be done by parameter flag. If flag is true then we perform encrypting, in other case
	 * we perform decrypting.
	 * @param source String name of source file
	 * @param target String name of target file
	 * @param flag boolean if flag is true then encrypting will be done, else decrypring
	 */
	public static void cripting(String source, String target, boolean flag) {
		System.out.printf("Please provide password as hex-encoded text (16 bytes, i.e. 32 hex-digits): \n> ");
		Scanner sc = new Scanner(System.in);
		String keyText = sc.nextLine();
		System.out.printf("Please provide initialization vector as hex-encoded text (32 hex-digits): \n> ");
		String ivText = sc.nextLine();
		sc.close();
		SecretKeySpec keySpec = new SecretKeySpec(Util.hexToByte(keyText), "AES");
		AlgorithmParameterSpec paramSpec = new IvParameterSpec(Util.hexToByte(ivText));
		Cipher cipher = null;
		try {
			cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		} catch (NoSuchAlgorithmException | NoSuchPaddingException e) {
			e.printStackTrace();
		}
		
		try {
			cipher.init(flag == true ? Cipher.ENCRYPT_MODE: Cipher.DECRYPT_MODE, keySpec, paramSpec);
		} catch (InvalidKeyException | InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		}
		
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new BufferedInputStream(Files.newInputStream(Paths.get(source)));
			os = new BufferedOutputStream(Files.newOutputStream(Paths.get(target)));
			byte[] arr = new byte[4096];
			int num;
			while(true) {
				num = is.read(arr);
				if(num < 1) {
					break;
				}
				os.write(cipher.update(arr, 0, num));
			}
			os.write(cipher.doFinal(	));
			if(flag == true) {
				System.out.println("Encryption completed. Generated file " + target + " based on file " + source + ".");
			} else {
				System.out.println("Decryption completed. Generated file " + target + " based on file " + source + ".");
			}
			
		} catch(IOException ex) {
			ex.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} finally {
			try {
				if(is != null) {
					is.close();
				}
				if(os != null) {
					os.close();
				}
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Checks if digest is the same as given from the user from console
	 * @param filename String name of source file whose digest will be checked with given
	 */
	public static void checksha(String filename) {
		System.out.println("Please provide expected sha-256 digest for " + filename + ":");
		System.out.printf("> ");
		Scanner sc = new Scanner(System.in);
		String expectedDigest = sc.nextLine();
		sc.close();
		InputStream is = null;
		try {
			is = new BufferedInputStream(Files.newInputStream(Paths.get(filename)));
			MessageDigest mes = null;
			try {
				mes = MessageDigest.getInstance("SHA-256");
			} catch (NoSuchAlgorithmException e) {
				e.printStackTrace();
			}
			byte[] arr
			= new byte[4096];
			int num;
			while(true) {
				num = is.read(arr);
				if(num < 1) {
					break;
				}
				mes.update(arr,0, num);
			}
			byte[] binHash = mes.digest();
			String hashFinal = Util.byteToHex(binHash);
			if(hashFinal.equals(expectedDigest) == true) {
				System.out.println("Digesting completed. Digest of "+ filename + " matches expected digest.");
			} else {
				System.out.println("Digesting completed. Digest of " + filename + " does not match the expected digest. Digest was: " + hashFinal);
			}
		} catch(IOException ex) {
			System.out.println("Error occured while getting file");
		} 
	}
}
