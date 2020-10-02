package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ComplexPolynomialTest {

	
	@Test
	public void test1() {
		ComplexPolynomial poly = new ComplexPolynomial(new Complex(-2, 0), new Complex(0,0), new Complex(0, 0), new Complex(0, 0), new Complex(2, 0));
		assertEquals("(2.0+i0.0)*z^4+(0.0+i0.0)*z^3+(0.0+i0.0)*z^2+(0.0+i0.0)*z^1+(-2.0+i0.0)", poly.toString());
	}
	
	
	@Test
	public void test2() {
		ComplexPolynomial poly = new ComplexPolynomial(new Complex(-2, 0), new Complex(0,0), new Complex(0, 0), new Complex(0, 0), new Complex(2, 0));
		ComplexPolynomial der = poly.derive();
		assertEquals("(8.0+i0.0)*z^3+(0.0+i0.0)*z^2+(0.0+i0.0)*z^1+(0.0+i0.0)", der.toString());
	}
	
	
	@Test
	public void test3() {
		ComplexPolynomial poly = new ComplexPolynomial(new Complex(1, 3), new Complex(1,2), new Complex(2, 1));
		Complex der = poly.apply(new Complex(1,1));
		assertEquals(-2, der.getRe(), 1e-7);
		assertEquals(10, der.getIm(), 1e-7);
	}
	
	@Test
	public void test4() {
		ComplexPolynomial poly1 = new ComplexPolynomial(new Complex(4, 5), new Complex(3,-2), new Complex(1, -1), new Complex(1, 2));
		ComplexPolynomial poly2 = new ComplexPolynomial(new Complex(1,1), new Complex(0,0), new Complex(-1,-1));
		ComplexPolynomial sol = poly1.multiply(poly2);
		assertEquals("(1.0-i3.0)*z^5+(-2.0+i0.0)*z^4+(-6.0+i2.0)*z^3+(3.0-i9.0)*z^2+(5.0+i1.0)*z^1+(-1.0+i9.0)", sol.toString());
	}

}
