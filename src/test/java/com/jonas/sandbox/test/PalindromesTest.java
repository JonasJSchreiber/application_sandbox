package com.jonas.sandbox.test;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jonas.sandbox.Palindromes;

public class PalindromesTest {
	Palindromes palindromes;
	
	@Before
	public void setup() {
		palindromes = new Palindromes();
	}
	
	@After 
	public void teardown() {
		
	}
	
	@Test
	public void testReverse() {
		try {
			System.out.println(palindromes.findIndexToRemove("baba"));
			System.out.println(palindromes.findIndexToRemove("babab"));
			System.out.println(palindromes.findIndexToRemove("bab"));
			System.out.println(palindromes.findIndexToRemove("ba"));
			System.out.println(palindromes.findIndexToRemove("bb"));
			
			System.out.println(palindromes.findIndexToRemove("cacacacac"));
			System.out.println(palindromes.findIndexToRemove("cacsaacac"));
			System.out.println(palindromes.findIndexToRemove("cacasacac"));
			System.out.println(palindromes.findIndexToRemove("cacaascac"));
			
			System.out.println(palindromes.findIndexToRemove("cacsacacac"));
			System.out.println(palindromes.findIndexToRemove("cacascacac"));
			System.out.println(palindromes.findIndexToRemove("cacacsacac"));
			System.out.println(palindromes.findIndexToRemove("cacacascac"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			fail();
		}
	}
}
