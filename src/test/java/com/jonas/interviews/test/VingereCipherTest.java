package com.jonas.interviews.test;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jonas.interviews.VingereCipher;

public class VingereCipherTest {
	VingereCipher cipher = new VingereCipher();
	
	@Before
	public void setup() {
	}
	
	@After 
	public void teardown() {
		
	}
	
	@SuppressWarnings("static-access")
	@Test
	public void testSpeed() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			cipher.myEncrypt("inputText", "mykey");
		}
		System.out.println("Processing took " + (System.currentTimeMillis() - start) + "ms");
		
		start = System.currentTimeMillis();
		for (int i = 0; i < 1000000; i++) {
			cipher.encrypt("inputText", "mykey");
		}
		System.out.println("Processing took " + (System.currentTimeMillis() - start) + "ms");
	}
}
