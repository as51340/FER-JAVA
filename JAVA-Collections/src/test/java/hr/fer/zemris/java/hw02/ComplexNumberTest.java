package hr.fer.zemris.java.hw02;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class ComplexNumberTest {

	@Test
	public void constructorTest() {
		ComplexNumber cn = new ComplexNumber(2,3);
		assertEquals("2.0+3.0i", cn.toString());
	}
	
	@Test
	public void getRealTest() {
		ComplexNumber cn = new ComplexNumber(1,2);
		assertEquals(1,cn.getReal(),1e-7);
	}
	
	@Test
	public void getImaginaryTest() {
		ComplexNumber cn = new ComplexNumber(-2, 31);
		assertEquals(31, cn.getImaginary(), 1e-7);
	}
	
	@Test
	public void getMagnitudeTest() {
		ComplexNumber cn = new ComplexNumber(4, 3);
		assertEquals(5, cn.getMagnitude(),1e-7);
	}
	
	@Test
	public void getAngleTest1() {
		ComplexNumber cn = new ComplexNumber(1,Math.sqrt(3));
		assertEquals(Math.PI / 3, cn.getAngle(), 1e-7);
	}
	
	@Test
	public void getAngleTest2() {
		ComplexNumber cn = new ComplexNumber(3,Math.sqrt(3));
		assertEquals(Math.PI / 6, cn.getAngle(), 1e-7);
	}
	
	@Test
	public void getAngleTest3() {
		ComplexNumber cn = new ComplexNumber(0,2);
		assertEquals(Math.PI / 2, cn.getAngle(), 1e-7);
	}
	
	@Test
	public void getAngleTest4() {
		ComplexNumber cn = new ComplexNumber(-3,Math.sqrt(3));
		assertEquals(5 * Math.PI / 6, cn.getAngle(), 1e-7);
	}

	@Test
	public void getAngleTest5() {
		ComplexNumber cn = new ComplexNumber(-1,-1);
		assertEquals(5 * Math.PI / 4, cn.getAngle(), 1e-7);
	}
	
	@Test
	public void getAngleTest6() {
		ComplexNumber cn = new ComplexNumber(Math.sqrt(3), -1);
		assertEquals(11 * Math.PI / 6, cn.getAngle(), 1e-7);
	}

	
	@Test
	public void fromRealTest() {
		assertEquals("0.0", ComplexNumber.fromReal(0).toString());
	}
	
	@Test
	public void fromImaginaryTest1() {
		assertEquals("0.0", ComplexNumber.fromImaginary(0).toString());
	}
	
	@Test
	public void fromImaginaryTest2() {
		assertEquals("1.0i", ComplexNumber.fromImaginary(1).toString());
	}
	
	@Test
	public void fromMagnitudeAndAngleTest1() {
		assertEquals(1.0,ComplexNumber.fromMagnitudeAndAngle(1, Math.PI / 2).getImaginary(), 1e-7);
	}
	
	@Test
	public void parseTest1() {
		assertEquals("3.51", ComplexNumber.parse("3.51").toString());
	}
	
	@Test
	public void parseTest2() {
		assertEquals("-3.51", ComplexNumber.parse("-3.51").toString());
	}
	
	@Test
	public void parseTest3() {
		assertEquals("-3.51i", ComplexNumber.parse("-3.51i").toString());
	}
	
	@Test
	public void parseTest4() {
		assertEquals("1.0i", ComplexNumber.parse("i").toString());
	}
	
	@Test
	public void parseTest5() {
		assertEquals("-1.0i", ComplexNumber.parse("-i").toString());
	}
	
	@Test
	public void parseTest6() {
		assertEquals("1.0", ComplexNumber.parse("1").toString());
	}
	
	@Test
	public void parseTest7() {
		assertEquals("-2.71-3.15i", ComplexNumber.parse("-2.71-3.15i").toString());
	}
	
	@Test
	public void parseTest8() {
		assertEquals("31.0+24.0i", ComplexNumber.parse("31+24i").toString());
	}
	
	@Test
	public void parseTest9() {
		assertEquals("-1.0-1.0i", ComplexNumber.parse("-1-i").toString());
	}
	
	@Test
	public void parseTest10() {
		assertEquals("1.0+1.0i", ComplexNumber.parse("1+i").toString());
	}
	
	@Test
	public void parseTest11() {
		assertEquals("271.0", ComplexNumber.parse("+271").toString());
	}
	
	@Test
	public void parseTest12() {
		assertEquals("1.0i", ComplexNumber.parse("+i").toString());
	}
	
	@Test
	public void addTest() {
		ComplexNumber a1 = new ComplexNumber(2, 3);
		ComplexNumber a2 = new ComplexNumber(1.2,4);
		ComplexNumber a3 = a1.add(a2);
		assertEquals(3.2, a3.getReal());
		assertEquals(7.0,a3.getImaginary());
	}
	
	@Test
	public void subTest() {
		ComplexNumber a1 = new ComplexNumber(2, 3);
		ComplexNumber a2 = new ComplexNumber(0,0);
		ComplexNumber a3 = a2.sub(a1);
		assertEquals(-2.0, a3.getReal());
		assertEquals(-3.0,a3.getImaginary());
	}
	
	@Test
	public void mulTest1() {
		ComplexNumber a1 = new ComplexNumber(4,1);
		ComplexNumber a2 = new ComplexNumber(2,4);
		ComplexNumber a3 = a1.mul(a2);
		assertEquals(4.0, a3.getReal());
		assertEquals(18.0,a3.getImaginary());
	}
	
	@Test
	public void mulTest2() {
		ComplexNumber a1 = new ComplexNumber(4,1);
		ComplexNumber a2 = new ComplexNumber(0,0);
		ComplexNumber a3 = a1.mul(a2);
		assertEquals(0.0, a3.getReal());
		assertEquals(0.0,a3.getImaginary());
	}
	
	@Test
	public void divTest1() {
		ComplexNumber a1 = new ComplexNumber(2,4);
		ComplexNumber a2 = new ComplexNumber(1,-3);
		ComplexNumber a3 = a1.div(a2);
		assertEquals(-1.0, a3.getReal());
		assertEquals(1.0,a3.getImaginary());
	}
	
	@Test
	public void divTest2() {
		ComplexNumber a1 = new ComplexNumber(4,1);
		ComplexNumber a2 = new ComplexNumber(0,0);
		assertThrows(IllegalArgumentException.class, () -> {
			ComplexNumber a3 = a1.div(a2);
		});	
	}
	
	@Test
	public void toStringTest1() {
		ComplexNumber a1 = new ComplexNumber(4,1);
		assertEquals(4.0, a1.getReal(), 1e-7);
		assertEquals(1.0,a1.getImaginary(), 1e-7);
	}
	
	@Test
	public void powerTest1() {
		ComplexNumber a1 = ComplexNumber.fromMagnitudeAndAngle(1, Math.PI / 6);
		assertEquals(0, a1.power(3).getReal(),1e-7);
		assertEquals(1,a1.power(3).getImaginary(),1e-7);
	}
	
	@Test
	public void powerTest2() {
		ComplexNumber a1 = ComplexNumber.fromMagnitudeAndAngle(1, Math.PI / 3);
		assertEquals(- 1.0/ 2.0,a1.power(2).getReal(),1e-7);
		assertEquals(Math.sqrt(3) / 2,a1.power(2).getImaginary(), 1e-7 );
	}
	
	@Test
	public void powerTest3() {
		ComplexNumber a1 = ComplexNumber.fromMagnitudeAndAngle(2, Math.PI / 4);
		assertEquals(-16,a1.power(4).getReal(), 1e-7);
		assertEquals(0, a1.power(4).getImaginary(),1e-7);
	}
	
	@Test
	public void rootTest1() {
		ComplexNumber a = ComplexNumber.fromMagnitudeAndAngle(4, Math.PI / 3);
		assertEquals(Math.sqrt(3), a.root(2)[0].getReal(), 1e-7);
		assertEquals(1, a.root(2)[0].getImaginary(), 1e-7);
		assertEquals(-Math.sqrt(3), a.root(2)[1].getReal(), 1e-7);
		assertEquals(-1, a.root(2)[1].getImaginary(), 1e-7);
	}
	
	@Test
	public void rootTest2() {
		ComplexNumber a = ComplexNumber.fromMagnitudeAndAngle(1, Math.PI / 2);
		assertEquals(Math.sqrt(3) / 2, a.root(3)[0].getReal(), 1e-7);
		assertEquals((double) 1 / 2, a.root(3)[0].getImaginary(), 1e-7);
		assertEquals(-Math.sqrt(3) / 2, a.root(3)[1].getReal(), 1e-7);
		assertEquals((double)1 / 2, a.root(3)[1].getImaginary(), 1e-7);
		assertEquals(0, a.root(3)[2].getReal(), 1e-7);
		assertEquals(-1, a.root(3)[2].getImaginary(), 1e-7);
	}
	
	
	
}
