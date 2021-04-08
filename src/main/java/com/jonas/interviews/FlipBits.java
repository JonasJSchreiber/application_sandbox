package com.jonas.interviews;

import org.junit.Test;

/*
 * Write a function that flips the bits inside a byte.
 */

public class FlipBits {

	public byte flipBits(Byte a) {
		int b = a ^ 255;
		return (byte) b;
	}
	
	@Test
	public void testFlipBits() {
		int a = 170;
		System.out.println(flipBits((byte) a));
	}
}
