package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Disabled;

import org.junit.jupiter.api.Test;

import hr.fer.zemris.math.Vector2D;

public class VectorTest {

	
	@Test
	public void test1() {
		Vector2D v1 = new Vector2D(1, 1);
		v1.rotate(Math.PI / 4);
		assertEquals(0, v1.getX(), 1e-7);
		assertEquals(Math.sqrt(2), v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test2() {
		Vector2D v1 = new Vector2D(1, 1);
		v1.rotate(Math.PI / 2);
		assertEquals(-1, v1.getX(), 1e-7);
		assertEquals(1, v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test3() {
		Vector2D v1 = new Vector2D(0 ,0);
		v1.rotate(Math.PI / 4);
		assertEquals(0, v1.getX(), 1e-7);
		assertEquals(0, v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test4() {
		Vector2D v1 = new Vector2D(1 ,1);
		v1.rotate(Math.PI);
		assertEquals(-1, v1.getX(), 1e-7);
		assertEquals(-1, v1.getY(), 1e-7);
		v1.rotate(Math.PI / 2);
		assertEquals(1, v1.getX(), 1e-7);
		assertEquals(-1, v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test5() {
		Vector2D v1 = new Vector2D(3 , Math.sqrt(3));
		v1.rotate(Math.PI / 6);
		assertEquals(Math.sqrt(3), v1.getX(), 1e-7);
		assertEquals(3, v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test6() {
		Vector2D v1 = new Vector2D(3 , Math.sqrt(3));
		v1.rotate(Math.PI / 3);
		assertEquals(0, v1.getX(), 1e-7);
		assertEquals(2 * Math.sqrt(3), v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test7() {
		Vector2D v1 = new Vector2D(3 , Math.sqrt(3));
		v1.rotate(5 * Math.PI / 6);
		assertEquals(-2 * Math.sqrt(3), v1.getX(), 1e-7);
		assertEquals(0, v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test8() {
		Vector2D v1 = new Vector2D(0 , 2);
		v1.rotate(Math.PI / 6);
		assertEquals(-1 , v1.getX(), 1e-7);
		assertEquals(Math.sqrt(3), v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test9() {
		Vector2D v1 = new Vector2D(0 , 2);
		v1.rotate(Math.PI / 2);
		assertEquals(-2 , v1.getX(), 1e-7);
		assertEquals(0, v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test10() {
		Vector2D v1 = new Vector2D(-1 , 1);
		v1.rotate(Math.PI / 12);
		assertEquals(-Math.sqrt(6) / 2 , v1.getX(), 1e-7);
		assertEquals(Math.sqrt(2) / 2, v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test11() {
		Vector2D v1 = new Vector2D(-Math.sqrt(3) , 3);
		v1.rotate(Math.PI / 3);
		assertEquals(-2*Math.sqrt(3) , v1.getX(), 1e-7);
		assertEquals(0, v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test12() {
		Vector2D v1 = new Vector2D(-Math.sqrt(3) , 3);
		v1.rotate(-Math.PI / 6);
		assertEquals(0, v1.getX(), 1e-7);
		assertEquals(2*Math.sqrt(3), v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test13() {
		Vector2D v1 = new Vector2D(-Math.sqrt(3) , 3);
		v1.rotate(5 * Math.PI / 6);
		assertEquals(0, v1.getX(), 1e-7);
		assertEquals(- 2* Math.sqrt(3), v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test14() {
		Vector2D v1 = new Vector2D(-3, -3);
		v1.rotate(Math.PI / 4);
		assertEquals(0, v1.getX(), 1e-7);
		assertEquals(- 3 * Math.sqrt(2), v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test15() {
		Vector2D v1 = new Vector2D(-3, -3);
		v1.rotate(Math.PI / 2);
		assertEquals(3, v1.getX(), 1e-7);
		assertEquals(- 3, v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test16() {
		Vector2D v1 = new Vector2D(-3, -Math.sqrt(3));
		v1.rotate(Math.PI / 3);
		assertEquals(0, v1.getX(), 1e-7);
		assertEquals(- 2 * Math.sqrt(3), v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test17() {
		Vector2D v1 = new Vector2D(0,-2);
		v1.rotate(Math.PI / 4);
		assertEquals(Math.sqrt(2), v1.getX(), 1e-7);
		assertEquals(-Math.sqrt(2),v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test18() {
		Vector2D v1 = new Vector2D(0,-2);
		v1.rotate(2 * Math.PI);
		assertEquals(0, v1.getX(), 1e-7);
		assertEquals(-2,v1.getY(), 1e-7);
	}
	
	
	@Test
	public void test19() {
		Vector2D v1 = new Vector2D(0,-2);
		v1.rotate(3 * Math.PI);
		assertEquals(0, v1.getX(), 1e-7);
		assertEquals(2,v1.getY(), 1e-7);
	}
	
	@Test
	public void test20() {
		Vector2D v1 = new Vector2D(0,-2);
		v1.rotate(6 * Math.PI);
		assertEquals(0, v1.getX(), 1e-7);
		assertEquals(-2,v1.getY(), 1e-7);
	}
	
	@Test
	public void test21() {
		Vector2D v1 = new Vector2D(0,-2);
		v1.translate(new Vector2D(2, 4));
		assertEquals(2, v1.getX());
		assertEquals(2, v1.getY());
	}
	
	@Test
	public void test22() {
		Vector2D v1 = new Vector2D(-1,-2);
		v1.scale(0);
		assertEquals(0, v1.getX());
		assertEquals(0, v1.getY());
	}
	
	@Test
	public void test23() {
		Vector2D v1 = new Vector2D(-1,-2);
		v1.scale(-1);
		assertEquals(1, v1.getX());
		assertEquals(2, v1.getY());
	}
	
	@Test
	public void test24() {
		Vector2D v1 = new Vector2D(-1,-2);
		Vector2D offset = new Vector2D(2, 4);
		v1.translate(offset);
		assertEquals(1, v1.getX());
		assertEquals(2, v1.getY());
	}
}
