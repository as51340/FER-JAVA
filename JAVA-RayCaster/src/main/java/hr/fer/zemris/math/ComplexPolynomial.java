package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class used to model polynoms of complex number. Depending on how many factors were provided by user this polynom will be polynom of nth degree.
 * Class provides methods for getting order of polynom, computing polynomial value at given point, derivation and for multiplying.
 * @author Andi Škrgat
 * @version 1.0
 */
public class ComplexPolynomial {

	/**
	 * All factors of this polynom stored in list
	 */
	private List<Complex> factors;
	
	/**
	 * Constructor initializes factors of this polynom
	 * @param factors of polynom provided by user
	 */
	public ComplexPolynomial(Complex ...factors) {
		this.factors = new ArrayList<>();
		for(Complex c: factors) {
			this.factors.add(c);
		}
		Collections.reverse(this.factors);
	}
	
	/**
	 * @returns order of polynom
	 */
	public short order() {
		return (short) (factors.size() -1);
	}
	
	/**
	 * Computes new polynomial this * p
	 * @param p second polynom
	 * @returns computed polynom
	 */
	public ComplexPolynomial multiply(ComplexPolynomial p) {
		int i = order();
		int j = p.order();
		Complex[] arr = new Complex[i + j +1];
		for(int k = 0; k < i + j +1; k++) {
			arr[k] = new Complex(0, 0);
		}
		for(Complex c1: factors) {
			int b = j;
			for(Complex c2: p.factors) {
				arr[i+b] = arr[i + b].add(c1.multiply(c2));
				b--;
			}
			i--;
		};
		return new ComplexPolynomial(arr);
	}
	
	/**
	 * Computes first derivative of this polynomial.
	 * @returns derivative
	 */
	public ComplexPolynomial derive() {
		int order = order();
		Complex[] arr = new Complex[order];
		int i = order-1;
		for(Complex c: factors) {
			if(order == 0) {
				break;
			}
			arr[i] = new Complex(order, 0).multiply(c);
			i--;
			order--;
		}
		return new ComplexPolynomial(arr);
	}
	
	/**
	 * @param z complex-given point
	 * @returns new complex-computed polynomial value at given point z
	 */
	public Complex apply(Complex z) {
		Complex sol = new Complex(0,0);
		int i = order();
		for(Complex c : factors) {
			if(i == 0) {
				sol = sol.add(c);
			} else {
				sol = sol.add(z.power(i).multiply(c));
			}
			i--;
		}
		return sol;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		int i = order();
		for(Complex c: factors) {
			if(i == 0) {
				sb.append(c);
			} else {
				sb.append(c.toString() + "*z^" + i + "+");
			}
			i--;
		}
		return sb.toString();
	}
}
