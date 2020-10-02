package hr.fer.zemris.math;

import java.util.ArrayList;
import java.util.List;

/**
 * Class that represents complex number
 * @author Andi Škrgat
 * @version 1.0
 */
public class Complex {
	
	/**
	 * Null vector
	 */
	public static final Complex ZERO = new Complex(0,0);
	
	/**
	 * Unit vector in direction of x-axis
	 */
	public static final Complex ONE = new Complex(1,0);
	
	/**
	 * Unit vector in negative direction of x-axis
	 */
	public static final Complex ONE_NEG = new Complex(-1,0);
	
	/**
	 * Unit vector in direction of y-axis
	 */
	public static final Complex IM = new Complex(0,1);
	
	/**
	 * Unit vector in negative direction of y-axis
	 */
	public static final Complex IM_NEG = new Complex(0,-1);
	
	/**
	 * Real and imaginary part of complex number
	 */
	private double re, im;
	
	/**
	 * Empty default constructor
	 */
	
	/**
	 * Constructor that initializes real and imaginary part of complex number
	 * @param re real part
	 * @param im imaginary part
	 */
	public Complex(double re, double im) {
		this.re = re;
		this.im = im;
	}
	
	/**
	 * @returns module of complex number
	 */
	public double module() {
		double d = Math.sqrt(re * re + im*im);
		return d;
	}
	
	/**
	 * @param c second vector-multiplicand
	 * @returns new complex number multiplied with c 
	 */
	public Complex multiply(Complex c) {
		return new Complex(re * c.re - im * c.im, re*c.im + im*c.re);
	}
	
	
	/**
	 * @param c complex number-denominator 
	 * @returns new complex number divided with c
	 */
	public Complex divide(Complex c) {
		if(c.re == 0 && c.im == 0) {
			throw new IllegalArgumentException("Cannot divide with zero!");
		}
		double mod = c.re*c.re + c.im*c.im;
		return new Complex((re* c.re + im*c.im) / mod, 
				(c.re * im - re * c.im) / mod );
	}
	
	/**
	 * @param c second complex number-addend
	 * @returns new complex number after performed addition
	 */
	public Complex add(Complex c) {
		return new Complex(re + c.re, im + c.im);
	}
	
	/**
	 * @param c second complex number-subtrahend
	 * @returns new complex number after subtracting 2 complex numbers
	 */
	public Complex sub(Complex c) {
		return new Complex(re - c.re, im - c.im);
	}
	
	/**
	 * @returns negated complex number
	 */
	public Complex negate() {
		return new Complex(-re, -im);
	}
	
	/**
	 * @param n always greater than zero
	 * @returns new complex number after raising this complex number to a power n
	 * @throws IllegalArgumentException if n < 0
	 */
	public Complex power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("Power factor should be non-negative.");
		}
		double pow = Math.pow(module(), n);
		double angle = getAngle();
		return new Complex(pow * Math.cos(n * angle), pow * Math.sin(n * angle));
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(");
		sb.append(re);
		if(im > 0 || Math.abs(im) < 1e-7) {
			sb.append("+");
		} else {
			sb.append("-");
		}
		sb.append("i");
		sb.append(Math.abs(im) + ")");
		return sb.toString();
	}
	
	/**
	 * Roots are calculated using De Moivre's formula
	 * @param nth root
	 * @returns n-roots in list for this vector
	 * @throws IllegalArgumentException if n <= 0
	 */
	public List<Complex> root(int n) {
		if(n <= 0) {
			throw new IllegalArgumentException("It's only possible to calculate n-roots where n is positive.");
		}
		List<Complex> list = new ArrayList<>();
		double mod = module();
		double angle = getAngle();
		mod = Math.pow(mod, 1 / n);
		for(int k = 0; k < n; k++) {
			list.add(new Complex(mod * Math.cos((angle + k*Math.PI * 2) / n), mod * Math.sin((angle + k * 2 * Math.PI) / n)));
		}
		return list;
	}
	
	/**
	 * Private method used for getting angle of complex number
	 * @returns angle in radians
	 */
	private double getAngle() {
		double angle;
		if(Math.abs(re) < 1e-7) {
			if(Math.abs(im) < 1e-7) {
				angle = 0;
			} else if(im > 0) {
				angle = Math.PI / 2;
			} else {
				angle = 1.5 * Math.PI;
			}
		} else if(Math.abs(im) < 1e-7) {
				if(re > 0) {
					angle = 0;
				} else {
					angle = Math.PI;
				}
		} else {
			angle = Math.atan(im / re);
			if(re < 0 && im > 0) {
				angle += Math.toRadians(180);
			}
			else if(re < 0 && im < 0) {
				angle += Math.toRadians(180);
			}
			else if(re > 0 && im < 0) {
				angle += Math.toRadians(360);
			}
		}
		return angle;
	}
	
	/**
	 * @returns real part
	 */
	public double getRe() {
		if(Math.abs(this.re) < 1e-7) {
			return 0;
		}
		return this.re;
	}
	
	/**
	 * @returns imaginary part
	 */
	public double getIm() {
		if(Math.abs(this.im) < 1e-7) {
			return 0;
		}
		return this.im;
	}


}
