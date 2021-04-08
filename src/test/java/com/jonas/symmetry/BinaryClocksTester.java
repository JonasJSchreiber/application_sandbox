package com.jonas.symmetry;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BinaryClocksTester {

	BinaryClocks b;
	
	@Before
	public void setup() {
		b = new BinaryClocks();
	}
	
	@After
	public void teardown() {
		
	}
	
	@Test 
	public void testGetBinaryClock() {
		String time = "1259";
		int[][] binaryClock = b.getBinaryClock(time);
		b.printBinaryClock(binaryClock);
	}
	
	@Test 
	public void testStripEmptyRowsAndColumns() {
		String time = "0408";
		int[][] binaryClock = b.getBinaryClock(time);
		int[][] stripped = b.stripEmptyRowsAndColumns(binaryClock);
		b.printBinaryClock(stripped);
	}
	
	@Test
	public void testNorthSouth() {
		String time = "0330";
		assertTrue(b.testNorthSouth(b.getBinaryClock(time)));
		time = "0331";
		assertFalse(b.testNorthSouth(b.getBinaryClock(time)));
	}
	
	@Test
	public void testEastWest() {
		String time = "0330";
		assertTrue(b.testEastWest(b.getBinaryClock(time)));
		time = "0331";
		assertFalse(b.testEastWest(b.getBinaryClock(time)));
	}
	
	@Test
	public void testNorthWest() {
		String time = "1249";
		assertTrue(b.testNorthWest(b.getBinaryClock(time)));
		time = "1244";
		assertFalse(b.testNorthWest(b.getBinaryClock(time)));
	}
	
	@Test
	public void testNorthEast() {
		String time = "9421";
		assertTrue(b.testNorthEast(b.getBinaryClock(time)));
		time = "1244";
		assertFalse(b.testNorthEast(b.getBinaryClock(time)));
	}
	
	@Test
	public void testSymmetry() {
		ArrayList<String> times = new ArrayList<String>();
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 6; j++) {
				for (int k = 0; k < 10; k++) {
					times.add("0" + i + "" + j + "" + k);
				}
			}
		}
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 6; j++) {
				for (int k = 0; k < 10; k++) {
					times.add("1" + i + "" + j + "" + k);
				}
			}
		}
		times.remove(0); //0000 is not a valid time
		
		int last = 0;
		String lastTime = "0000";
		for (String s : times) {
//			System.out.println("Testing: " + s);
			boolean[] symmetrical = b.testSymmetry(s);
			int multiplier = 1;
			int current = 0;
			for (int i = 0; i < symmetrical.length; i++) {
				if (symmetrical[i]) {
					current += multiplier;
				}
				multiplier *= 2;
			}
			if ((current == 4 && last == 8) || (current == 8 && last == 4)) {
				System.out.println("Found one!\nCurrent Time: " + s + ", last time: " + lastTime);
			}
			lastTime = s;
			last = current;
		}
	}
	
	@Test 
	public void testMirrorClock() {
		int[][] binaryClock = b.getBinaryClock("0016");
		b.printBinaryClock(binaryClock);
		binaryClock = b.mirrorClock(binaryClock);
		System.out.println("\nMirrored:");
		b.printBinaryClock(binaryClock);
	}
	
	@Test
	public void testGetSingleSymmetry() {
		int[][] binaryClock = b.getBinaryClock("0505");
		b.printBinaryClock(binaryClock);
		
		boolean[] symmetries = b.testSymmetry("0505");
		for (boolean s : symmetries) {
			System.out.println(s);
		}
	}
	
	@Test
	public void testGetSingleSymmetry1() {
		int[][] binaryClock = b.getBinaryClock("0032");
		b.printBinaryClock(binaryClock);
		
		boolean[] symmetries = b.testSymmetry("0032");
		for (boolean s : symmetries) {
			System.out.println(s);
		}
	}
}
