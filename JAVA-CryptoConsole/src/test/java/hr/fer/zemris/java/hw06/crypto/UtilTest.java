package hr.fer.zemris.java.hw06.crypto;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import hr.fer.zemris.java.hw06.crypto.Util;

/**
 * JUnit tests for Unit class methods
 * @author Andi Škrgat
 * @version 1.0
 */
public class UtilTest {
	
	@Disabled
	@Test
	public void test1() {
		String input = "01aE22";
		assertEquals(input.toLowerCase(), Util.byteToHex(Util.hexToByte(input)));
	}
	
	@Disabled
	@Test
	public void test2() {
		String input = "01aE22";
		byte[] arr = Util.hexToByte(input);
		assertEquals(1, arr[0]);
		assertEquals(-82, arr[1]);
		assertEquals(34, arr[2]);
	}
	
	@Disabled
	@Test
	public void test3() {
		String input = "2e7b3a91235ad72cb7e7f6a721f077faacfeafdea8f3785627a5245bea112598";
		assertEquals(input, Util.byteToHex(Util.hexToByte(input)));
	}
	
	@Disabled
	@Test
	public void test4() {
		String input = "d03d4424461e22a458c6c716395f07dd9cea2180a996e78349985eda78e8b800";
		assertEquals(input, Util.byteToHex(Util.hexToByte(input)));
	}
	
	@Disabled
	@Test
	public void test5() {
		String input = "d03d4424461e22a458c6c716395f07dd9cea2180a996e78349985eda78e8b800";
		assertEquals(input, Util.byteToHex(Util.hexToByte(input)));
	}
	
	@Disabled
	@Test
	public void test6() {
		String input ="0A1";
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hexToByte(input);
		});
	}
	
	@Disabled
	@Test
	public void test7() {
		String input ="0A1n";
		assertThrows(IllegalArgumentException.class, () -> {
			Util.hexToByte(input);
		});
	}
	
	@Disabled
	@Test
	public void test8() {
		byte[] arr = new byte[] {1, -82, 34};
		assertEquals("01ae22", Util.byteToHex(arr));
	}
	
}
	
	