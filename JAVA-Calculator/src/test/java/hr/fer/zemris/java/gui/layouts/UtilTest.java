package hr.fer.zemris.java.gui.layouts;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class UtilTest {

	@Test
	void test1() {
		String text = "2, 3";
		RCPosition pos = Util.parse(text);
		assertEquals(2, pos.getRow());
		assertEquals(3, pos.getColumn());
	}
	
	@Test
	void test2() {
		String text = "2,   3   ";
		RCPosition pos = Util.parse(text);
		assertEquals(2, pos.getRow());
		assertEquals(3, pos.getColumn());
	}	
	
	@Test
	void test3() {
		String text = "  2 ,   3  ";
		RCPosition pos = Util.parse(text);
		assertEquals(2, pos.getRow());
		assertEquals(3, pos.getColumn());
	}
	
	@Test
	void test4() {
		String text = "2, ,3";
		assertThrows(IllegalArgumentException.class, () -> {
			RCPosition pos = Util.parse(text);	
		});
	}
	
	@Test
	void test5() {
		String text = "2a, 3";
		assertThrows(IllegalArgumentException.class, () -> {
			RCPosition pos = Util.parse(text);	
		});
	}
	
	@Test
	void test6() {
		String text = "2 3";
		assertThrows(IllegalArgumentException.class, () -> {
			RCPosition pos = Util.parse(text);	
		});
	}
	
	@Test
	void test7() {
		String text = "23";
		assertThrows(IllegalArgumentException.class, () -> {
			RCPosition pos = Util.parse(text);	
		});
	}

}
