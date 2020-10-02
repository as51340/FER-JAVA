package hr.fer.zemris.java.hw02;
import java.lang.Math;


/**
 * Class that supports working with complex numbers. It supports operations like adding, subtraction, multiplication, division, exponentiation and finding root of complex number(s). 
 * @author Andi Škrgat
 * @version 1.0
 */

public class ComplexNumber {
	
	private double real, imaginary, magnitude, angle;
	
	/**
	 * @param real part of complex number
	 * @param imaginary part of complex number
	 */
	public ComplexNumber(double real, double imaginary) {
		this.real = real;
		this.imaginary = imaginary;
		this.magnitude = Math.sqrt(real * real + imaginary*imaginary);
		if(real == 0) {
			if(real == 0 && imaginary > 0) {
				angle = Math.PI / 2;
			}
			else if(real == 0 && imaginary < 0) {
				angle = 3 * Math.PI / 2;
			}
			else {
				angle = 0;
			}
		}else {
			this.angle = Math.atan(imaginary / real);
			if(real < 0 && imaginary > 0) {
				angle += Math.toRadians(180);
			}
			else if(real < 0 && imaginary < 0) {
				angle += Math.toRadians(180);
			}
			else if(real > 0 && imaginary < 0) {
				angle += Math.toRadians(360);
			}
		}
	}
	
	/**
	 * Method generates new complex number from real part
	 * @param real part of Complex number
	 * @return ComplexNumber
	 */
	public static ComplexNumber fromReal(double real) {
		return new ComplexNumber(real,0);
	}
	
	/**
	 * Method generates new complex number from imaginary part
	 * @param imaginary part of complex number
	 * @return new ComplexNumber
	 */
	public static ComplexNumber fromImaginary(double imaginary) {
		return new ComplexNumber(0, imaginary);
	}
	
	/**
	 * Method generates new complex number by accepting magnitude and angle of the complex number.
	 * @param magnitude gives the distance of the number from the origin in the complex plane 
	 * @param angle is the angle the number forms with the positive Real axis in radians
	 * @return new ComplexNumber
	 */
	public static ComplexNumber fromMagnitudeAndAngle(double magnitude, double angle) {
		return new ComplexNumber(magnitude * Math.cos(angle), magnitude * Math.sin(angle));
	}
	
	/**
	 * Method parses string to get real and imaginary part of complex number. Leading signs can be accepted.
	 * @param s string that we want to represent as complex number
	 * @return new complex number 
	 */
	public static ComplexNumber parse(String s) {
		String mod;
		boolean flag = true;
		if(s.charAt(0) == '+' || s.charAt(0) == '-') {
			mod = s.substring(1);
		}else {
			mod = s;
		}
		
		String[] arr = null;
		double real = 0, imaginary = 0;
		if(mod.contains("+") == true) {
			arr = mod.split("\\+");
			
		}
		else if(mod.contains("-") == true) {
			flag = false;
			arr = mod.split("-");
			
		}
		else {
			
			arr = new String[1];
			arr[0] = mod;
		}
		if(arr.length == 1) {
			
			if(arr[0].contains("i") == true) {
				
				if(arr[0].equals("i") == true) {
					imaginary = 1;
					if(s.charAt(0) == '-') {
						imaginary *= -1;
					}
				}
				else {
					
					try {
						imaginary = Double.parseDouble(mod.substring(0, mod.length() -1));
						if(s.charAt(0) == '-') {
							imaginary *= -1;
						}
					}
					catch(NumberFormatException ex) {
						System.out.println("Complex number in wrong format");
						System.exit(0);
					}
				}
				
			}
			else {
				
				try {
					real = Double.parseDouble(mod);
					if(s.charAt(0) == '-') {
						real*= -1;
					}
				}
				catch(NumberFormatException ex) {
					System.out.println("Complex number in wrong format");
					System.exit(0);
				}
				

			}
		}
		else {
			
			try {
				real = Double.parseDouble(arr[0]);
				if(s.charAt(0) == '-') {
					real*= -1;
				}
				if(arr[1].length() == 1) {
					imaginary = 1;
				}
				else {
					imaginary = Double.parseDouble(arr[1].substring(0, arr[1].length() -1));
					
				}
				if(flag == false) {
					imaginary *= -1;
				}
				
			} catch(NumberFormatException ex) {
				System.out.println("Complex number in wrong format");
				System.exit(0);
			}
		}
		return new ComplexNumber(real, imaginary);
	}
	
	
	
	
	/**
	 * @returns real part of complexNumber
	 */
	public double getReal() {
		return real;
	}

	/**
	 * @returns imaginary part of complexNumber
	 */
	public double getImaginary() {
		return imaginary;
	}
	
	/**
	 * @returns magnitude of complexNumber
	 */
	public double getMagnitude() {
		return magnitude;
	}

	/**
	 * @returns angle of complexNumber in radians
	 */
	public double getAngle() {
		return angle;
	}
	
	/**
	 * Method performs sum of 2 complex numbers.
	 * @param c complex number that we are adding to one already existing
	 * @return new ComplexNumber
	 */
	public ComplexNumber add(ComplexNumber c) {
		return new ComplexNumber(this.real + c.real, this.imaginary + c.imaginary);
	}
	
	/**
	 * Method performs subtraction of 2 complex numbers.
	 * @param c complex number subtrahend
	 * @return new ComplexNumber
	 */
	public ComplexNumber sub(ComplexNumber c) {
		return new ComplexNumber(this.real - c.real, this.imaginary - c.imaginary);
	}
	
	/**
	 * Method performs multiplication of 2 complex numbers.
	 * @param c complex number multiplicand
	 * @return new ComplexNumber
	 */
	public ComplexNumber mul(ComplexNumber c) {
		return new ComplexNumber(this.real*c.real - this.imaginary*c.imaginary, this.real*c.imaginary + this.imaginary*c.real);	
	}
	
	/**
	 * Method performs division of 2 complex numbers.
	 * @param c complex number-denominator 
	 * @return new ComplexNumber
	 */
	public ComplexNumber div(ComplexNumber c) {
		if(c.real == 0 && c.imaginary == 0) {
			throw new IllegalArgumentException("Cannot divide with zero!");
		}
		return new ComplexNumber((this.real* c.real + this.imaginary*c.imaginary) / (c.real*c.real + c.imaginary*c.imaginary), (this.imaginary*c.real - this.real*c.imaginary) / (c.real*c.real + c.imaginary*c.imaginary));
	}
	/**
	 * Method is used for exponentiation complex number.
	 * @param n exponent 
	 * @return new ComplexNumber
	 */
	public ComplexNumber power(int n) {
		if(n < 0) {
			throw new IllegalArgumentException("Exponent should be >= 0");
		}
		return new ComplexNumber(Math.cos(n*this.angle)*Math.pow(magnitude, n), Math.sin(n*angle) * Math.pow(magnitude, n));
	}
	
	/**
	 * Method returns all complex numbers calculated by De Moivre's formula.
	 * @param nth root
	 * @return array of roots
	 */
	public ComplexNumber[] root(int n) {
		if (n <= 0 ) {
			throw new IllegalArgumentException("Rooting factor should be greater than 0");
		}
		double root = Math.pow(magnitude, (double) 1/(double)n);
		ComplexNumber[] arr = new ComplexNumber[n];
		for(int k = 0; k < n; k++) {
			arr[k] = new ComplexNumber(root * (Math.cos((angle + 2*Math.PI*k) / n)), root * (Math.sin((angle + 2*Math.PI*k)/ n)));
		}
		return arr;
	}
	
	/**
	 * @returns representation of complex number as string
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		
		if(real != 0) {
			sb.append(real);
			if(imaginary != 0) {
				if(imaginary > 0 ) {
					sb.append("+");
				}
				sb.append(imaginary +"i");
			}
		} else {
			
			sb.append(imaginary);
			if(imaginary!= 0) {
				sb.append("i");
			}
		}
		return sb.toString();
	}
}
	
	
	
	
	
	
	

	
	

