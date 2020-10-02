package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class ComplexRootedPolynomialTest {

	
	@Test
	public void test1() { //output of polynoms
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
		assertEquals("(2.0+i0.0)*(z-(1.0+i0.0))*(z-(-1.0+i0.0))*(z-(0.0+i1.0))*(z-(0.0-i1.0))", crp.toString());
	}
	
	
	@Test
	public void test2() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
		Complex applied = crp.apply(new Complex(2,1));
		assertEquals(-16, applied.getRe(),1e-7);
		assertEquals(48, applied.getIm(),1e-7);
	}
	
	
	@Test
	public void test3() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
		Complex applied = crp.apply(new Complex(0,0));
		assertEquals(-2, applied.getRe(),1e-7);
		assertEquals(0, applied.getIm(),1e-7);
	}
	
	
	@Test
	public void test4() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
		int index = crp.indexOfClosestRootFor(new Complex(0, 0), 2);
		assertEquals(0, index);
	}
	
	
	@Test
	public void test5() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), new Complex(2, 0), Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
		int index = crp.indexOfClosestRootFor(new Complex(0, 0), 2);
		assertEquals(1, index);
	}
	
	
	@Test
	public void test6() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
		ComplexPolynomial cp = crp.toComplexPolynom();
		assertEquals("(2.0+i0.0)*z^4+(0.0+i0.0)*z^3+(0.0+i0.0)*z^2+(0.0+i0.0)*z^1+(-2.0+i0.0)", cp.toString());
		cp = cp.derive();
		assertEquals("(8.0+i0.0)*z^3+(0.0+i0.0)*z^2+(0.0+i0.0)*z^1+(0.0+i0.0)", cp.toString());
	}
	
	@Test
	public void test7() {
		ComplexRootedPolynomial crp = new ComplexRootedPolynomial(
				new Complex(2,0), Complex.ONE, Complex.ONE_NEG, Complex.IM, Complex.IM_NEG
				);
		int index = crp.indexOfClosestRootFor(new Complex(0, 0), 0.5);
		assertEquals(-1, index);
	}
}
