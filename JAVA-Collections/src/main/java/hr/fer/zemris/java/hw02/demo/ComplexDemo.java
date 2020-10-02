package hr.fer.zemris.java.hw02.demo;

import hr.fer.zemris.java.hw02.ComplexNumber;

public class ComplexDemo {

	public static void main(String[] args) {
		ComplexNumber c1 = new ComplexNumber(-2, -2);
		ComplexNumber c2 = ComplexNumber.parse("2.5-3i");
		ComplexNumber c3 = c1.add(ComplexNumber.fromMagnitudeAndAngle(2, 1.57)).div(c2).power(3).root(2)[1];
		System.out.println(c3);
		//System.out.println(ComplexNumber.parse("-2.71-3.15i"));
		//System.out.println("3.51");
		//System.out.println("-3.17");
	}

}
