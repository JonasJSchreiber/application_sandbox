package com.jonas.interviews.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jonas.interviews.BinaryRep;

public class BinaryRepTest {
	
	BinaryRep binaryRep = new BinaryRep();
	
	@Before
	public void setup() {
	}
	
	@After 
	public void teardown() {
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testSpeed() {
		int input = 1073741824;
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			binaryRep.integerToBinary(input);
		}
		System.out.println("Processing took " + (System.currentTimeMillis() - start) + "ms");
		
		start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			binaryRep.bitwiseIntegerToBinary(input);
		}
		System.out.println("Processing took " + (System.currentTimeMillis() - start) + "ms");
		
		start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			Integer.toBinaryString(input);
		}
		System.out.println("Processing took " + (System.currentTimeMillis() - start) + "ms");
		
		start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			Integer.toString(input, 2);
		}
		System.out.println("Processing took " + (System.currentTimeMillis() - start) + "ms");
	}
}