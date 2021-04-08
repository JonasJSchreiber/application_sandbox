package com.jonas.sandbox.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Test;

import com.jonas.sandbox.CoderByte;

public class CoderByteTest {
	
	CoderByte coderByte;
	
	@SuppressWarnings("unused")
	public void setup() {
		CoderByte coderByte = new CoderByte();
	}
	
	@After 
	public void teardown() {
		
	}
	
	@Test
	public void testReverse() {
		try {
			String str = "coderbyte";
			CoderByte coderByte = new CoderByte();
			String result = coderByte.FirstReverse(str);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testManipulate() {
		try {
			String str = "hello world";
			CoderByte coderByte = new CoderByte();
			String result = coderByte.Manipulate(str);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
	@Test
	public void testNNMinues1Over2() {
		try {
			CoderByte coderByte = new CoderByte();
			int result = coderByte.getnnminus1over2(12);
			System.out.println(result);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
}
