package hr.fer.zemris.math;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class VectorTest {

	@Test
	public void test1() {
		Vector3 i = new Vector3(1,0,0);
		assertEquals(1, i.getX(), 1e-7);
		assertEquals(0, i.getY(), 1e-7);
		assertEquals(0, i.getZ(), 1e-7);
	}
	
	@Test
	public void test2() {
		Vector3 j = new Vector3(0,1,0);
		Vector3 i = new Vector3(1,0,0);
		Vector3 k = i.cross(j);
		assertEquals(0, k.getX(), 1e-7);
		assertEquals(0, k.getY(), 1e-7);
		assertEquals(1, k.getZ(), 1e-7);
	}
	
	@Test
	public void test3() {
		Vector3 j = new Vector3(0,1,0);
		Vector3 i = new Vector3(1,0,0);
		Vector3 k = i.cross(j);
		Vector3 l = k.add(j).scale(5);
		assertEquals(0, l.getX(), 1e-7);
		assertEquals(5, l.getY(), 1e-7);
		assertEquals(5, l.getZ(), 1e-7);
	}
	
	@Test
	public void test4() {
		Vector3 j = new Vector3(0,1,0);
		Vector3 i = new Vector3(1,0,0);
		Vector3 k = i.cross(j);
		Vector3 l = k.add(j).scale(5);
		assertEquals(7.0710678118654755, l.norm(), 1e-5);
	}
	
	@Test
	public void test5() {
		Vector3 j = new Vector3(0,1,0);
		Vector3 i = new Vector3(1,0,0);
		Vector3 k = i.cross(j);
		Vector3 l = k.add(j).scale(5);
		Vector3 m = l.normalized();
		assertEquals(0, m.getX(), 1e-7);
		assertEquals(Math.sqrt(2) / 2, m.getY(), 1e-7);
		assertEquals(Math.sqrt(2) / 2, m.getZ(), 1e-7);
	}
	
	@Test
	public void test6() {
		Vector3 j = new Vector3(0,1,0);
		Vector3 i = new Vector3(1,0,0);
		Vector3 k = i.cross(j);
		Vector3 l = k.add(j).scale(5);
		double dotted = l.dot(j);
		assertEquals(5, dotted, 1e-7);
	}
	
	@Test
	public void test7() {
		Vector3 j = new Vector3(0,1,0);
		Vector3 i = new Vector3(1,0,0);
		Vector3 k = i.cross(j);
		Vector3 l = k.add(j).scale(5);
		double angle = i.add(new Vector3(0,1,0)).cosAngle(l);
		assertEquals(0.499999999, angle, 1e-7);
	}

}
