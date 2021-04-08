package com.jonas.interviews;

import java.util.Random;

import org.junit.Test;

public class UniformDistribution {
	/*
	 * Given a function which produces a random integer in the range 1 to 5,
	 * write a function which produces a random integer in the range 1 to 7.
	 */
	public int rand7() {
		int i;
		do {
			i = 5 * (rand5() - 1) + rand5(); // i is now uniformly random between 1 and 25
		} while (i > 21);
		// i is now uniformly random between 1 and 21
		return i % 7 + 1; // result is now uniformly random between 1 and 7
	}

	public int rand5() {
		return new Random().nextInt(5) + 1;
	}
	
	@Test
	public void testRandom7() {
		int[] counts = new int[8];
		for (int i = 0; i < 100000; i++) {
			int r = rand7();
			counts[r]++;
		}
		for (int i = 1; i < counts.length; i++) {
			System.out.println(i + ": " +  ((double) counts[i]/100000));
		}
		
	}
}
