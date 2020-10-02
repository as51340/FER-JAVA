package hr.fer.zemris.lsystems.impl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;

public class GenerateTest {

	
	@Test
	public void cochGenerate() {
		LSystemBuilder builder = new LSystemBuilderImpl();
		builder.setAxiom("F");
		builder.registerProduction('F', "F+F--F+F");
		LSystem impl = builder.build();
		assertEquals("F+F--F+F", impl.generate(1));
		assertEquals("F", impl.generate(0));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", impl.generate(2));
		assertEquals("F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F+F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F+F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F", impl.generate(3));
	}
	
	public void sierpinskiGenerate() {
		LSystemBuilder builder = new LSystemBuilderImpl();
		builder.setAxiom("R");
		LSystem impl = builder.build();
		builder.registerProduction('R', "L-R-L");
		builder.registerProduction('L', "R+L+R");
		assertEquals("R", impl.generate(0));
		assertEquals("L-R-L", impl.generate(1));
		assertEquals("L-R-L+R+L+R+L-R-L+R+L+R-L-R-L-R+L+R-L-R-L+R+L+R+L-R-L", impl.generate(2));
	}

}
