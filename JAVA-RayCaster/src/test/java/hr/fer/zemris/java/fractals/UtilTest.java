package hr.fer.zemris.java.fractals;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.math.Complex;

public class UtilTest {

	
	@Test
	public void test1() {
		String input ="1";
		Complex c = Util.getComplex(input);
		assertEquals(1, c.getRe());
		assertEquals(0, c.getIm());			
	}
	
	
	@Test
	public void test2() {
		String input ="-1 + i0";
		Complex c = Util.getComplex(input);
		assertEquals(-1, c.getRe());
		assertEquals(0, c.getIm());			
	}
	
	
	@Test
	public void test3() {
		String input ="i";
		Complex c = Util.getComplex(input);
		assertEquals(0, c.getRe());
		assertEquals(1, c.getIm());			
	}
	
	
	@Test
	public void test4() {
		String input ="0 - i1";
		Complex c = Util.getComplex(input);
		assertEquals(0, c.getRe());
		assertEquals(-1, c.getIm());			
	}
	
	
	@Test
	public void test5() {
		String input ="0";
		Complex c = Util.getComplex(input);
		assertEquals(0, c.getRe());
		assertEquals(0, c.getIm());			
	}
	
	
	@Test
	public void test6() {
		String input ="i0";
		Complex c = Util.getComplex(input);
		assertEquals(0, c.getRe());
		assertEquals(0, c.getIm());			
	}
	
	
	@Test
	public void test7() {
		String input ="0+i0";
		Complex c = Util.getComplex(input);
		assertEquals(0, c.getRe());
		assertEquals(0, c.getIm());			
	}
	
	
	@Test
	public void test8() {
		String input ="0-i0";
		Complex c = Util.getComplex(input);
		assertEquals(0, c.getRe());
		assertEquals(0, c.getIm());			
	}
	
	
	@Test
	public void test9() {
		String input ="-i";
		Complex c = Util.getComplex(input);
		assertEquals(0, c.getRe());
		assertEquals(-1, c.getIm());			
	}
	
	
	@Test
	public void test10() {
		String input ="1.5 + i3";
		Complex c = Util.getComplex(input);
		assertEquals(1.5, c.getRe());
		assertEquals(3, c.getIm());			
	}
	
	@Test
	public void test11() {
		String input ="-1.2 + i3";
		Complex c = Util.getComplex(input);
		assertEquals(-1.2, c.getRe());
		assertEquals(3, c.getIm());			
	}
	
	
	@Test
	public void test12() {
		String input ="-i3.56";
		Complex c = Util.getComplex(input);
		assertEquals(0, c.getRe());
		assertEquals(-3.56, c.getIm());			
	}
	
	@Test
	public void test13() {
		String input ="-1.2 + i3";
		Complex c = Util.getComplex(input);
		assertEquals(-1.2, c.getRe());
		assertEquals(3, c.getIm());			
	}
	
	@Test
	public void test14() {
		String input ="-1.2 - i3.5";
		Complex c = Util.getComplex(input);
		assertEquals(-1.2, c.getRe());
		assertEquals(-3.5, c.getIm());			
	}
	
	@Test
	public void test15() {
		String input ="-1 - i3.223";
		Complex c = Util.getComplex(input);
		assertEquals(-1, c.getRe());
		assertEquals(-3.223, c.getIm());			
	}
	
	@Test
	public void test16() {
		String input = "-0.5 - i0.866";
		Complex c = Util.getComplex(input);
		assertEquals(-0.5, c.getRe());
		assertEquals(-0.866, c.getIm());
	}
	
	
	
	

}
