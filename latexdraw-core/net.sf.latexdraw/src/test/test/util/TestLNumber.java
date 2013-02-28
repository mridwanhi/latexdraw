package test.util;

import junit.framework.TestCase;

import net.sf.latexdraw.util.LNumber;

import org.junit.Test;

public class TestLNumber extends TestCase {
	@Test
	public void testEqualsEqual() {
		assertTrue(LNumber.INSTANCE.equals(10, 0, 10));
		assertTrue(LNumber.INSTANCE.equals(-10, 0, 10));
	}


	@Test
	public void testEquals1() {
		double a = 1./3.;
		double b = 0.333333333333333;

		assertTrue(LNumber.INSTANCE.equals(a, b));
		b = 0.3299;
		assertFalse(LNumber.INSTANCE.equals(a, b));

		a = -1./3.;
		b = -0.333333333333333;

		assertTrue(LNumber.INSTANCE.equals(a, b));
		b = -0.3299;
		assertFalse(LNumber.INSTANCE.equals(a, b));
	}



	@Test
	public void testEquals2() {
		double a = 1./3.;
		double b = 0.333333333333333;

		assertTrue(LNumber.INSTANCE.equals(a, b, 0.00001));
		b = 0.3299;
		assertFalse(LNumber.INSTANCE.equals(a, b, 0.00000001));
		assertTrue(LNumber.INSTANCE.equals(a, b, 0.01));

		a = -1./3.;
		b = -0.333333333333333;

		assertTrue(LNumber.INSTANCE.equals(a, b, 0.00001));
		b = -0.3299;
		assertFalse(LNumber.INSTANCE.equals(a, b, 0.00000001));
		assertFalse(LNumber.INSTANCE.equals(a, b, -0.00000001));
		assertFalse(LNumber.INSTANCE.equals(a, b, 0.));
		assertTrue(LNumber.INSTANCE.equals(a, b, 0.01));
	}



	@Test
	public void testGetCutNumber1() {
		double v = 0.333333333333333;

		assertEquals(v, LNumber.INSTANCE.getCutNumber(v, 0.1));
		assertEquals(v, LNumber.INSTANCE.getCutNumber(v, 0.2));
		assertEquals(v, LNumber.INSTANCE.getCutNumber(v, 0.3));
		assertEquals(0., LNumber.INSTANCE.getCutNumber(v, 0.34));

		v = 0.000001;
		assertEquals(v, LNumber.INSTANCE.getCutNumber(v, 0.00000001));
		assertEquals(0., LNumber.INSTANCE.getCutNumber(v, 0.00001));

		v = 1E-10;
		assertEquals(0., LNumber.INSTANCE.getCutNumber(v, 0.00000001));
	}



	@Test
	public void testGetCutNumber2() {
		float v = 0.333333333333333f;

		assertEquals(v, LNumber.INSTANCE.getCutNumber(v, 0.1));
		assertEquals(v, LNumber.INSTANCE.getCutNumber(v, 0.2));
		assertEquals(v, LNumber.INSTANCE.getCutNumber(v, 0.3));
		assertEquals(0f, LNumber.INSTANCE.getCutNumber(v, 0.34));

		v = 0.000001f;
		assertEquals(v, LNumber.INSTANCE.getCutNumber(v, 0.00000001));
		assertEquals(0f, LNumber.INSTANCE.getCutNumber(v, 0.00001));

		v = 1E-10f;
		assertEquals(0f, LNumber.INSTANCE.getCutNumber(v, 0.00000001));
	}
}