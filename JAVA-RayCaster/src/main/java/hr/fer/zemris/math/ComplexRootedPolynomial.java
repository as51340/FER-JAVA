package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class used to model polynoms of complex number. Polynom is expected to be in form:  z0*(z-z1)*(z-z2)*...*(z-zn) where z0 is constant and
 * z1..zn are all zeroes of polynom and are provided from user.
 * @author Andi Škrgat
 * @version 1.0
 */
public class ComplexRootedPolynomial {
	
	/**
	 * Constant z0 of this polynom
	 */
	private Complex constant;
	
	/**
	 * Roots of polynomial
	 */
	private List<Complex> roots;
	
	/**
	 * Constructor that initiliazes constant z0 and roots of this polynom
	 * @param constant z0
	 * @param roots z1...zn
	 */
	public ComplexRootedPolynomial(Complex constant, Complex ...roots) {
		this.constant = constant;
		this.roots = new ArrayList<>();
		for(Complex c: roots) {
			this.roots.add(c);
		}
	}
	
	/**
	 * Computes polynomial value at given point z
	 * @param z given point
	 * @returns computed value
	 */
	public Complex apply(Complex z) {
		Complex sol = constant;
		for(Complex root: roots) {
			sol = sol.multiply(z.sub(root));
		}
		return sol;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(constant.toString());
		for(Complex root: roots) {
			sb.append("*(z-" + root.toString() + ")");
		}
		return sb.toString();
	}
	
	/**
	 * Finds index of closest root for given complex number z that is within threshold. If there is no such root, returns -1.
	 * @param z given complex number
	 * @param threshold
	 * @returns index of closest root for given complex number z that is within threshold. If there is no such root, returns -1.
	 */
	public int indexOfClosestRootFor(Complex z, double threshold) {
		double min = threshold + 1;
		int minIndex = -1;
		int i = 0;
		for(Complex c: roots) {
			double distance = (c.getRe() - z.getRe()) * (c.getRe() - z.getRe()) + (c.getIm() - z.getIm()) * (c.getIm() - z.getIm()); 
			if(distance <= threshold * threshold && distance < min) {
				min = distance;
				minIndex = 	i;
			}
			i++;
		}
		return minIndex;
	}
	
	/**
	 * @returns representation off this polynom to ComplexPolynomial type
	 */
	public ComplexPolynomial toComplexPolynom() {
		ComplexPolynomial con = new ComplexPolynomial(constant);
		for(Complex c: roots) {
			ComplexPolynomial novi = new ComplexPolynomial(c.negate(), Complex.ONE);
			con = con.multiply(novi);
		}
		return con;
	}
	
	
	

}
