package com.jonas.interviews;

import org.junit.Test;

/**
 * Implement division without using the divide operator
 */

public class QuickDivision {
	
	public static String quickDivide(int num, int denom) {
		int quotBits = 1; // start with a single bit quotient
		int quot = 0;

		// get number of quotient bits
		while ((denom << quotBits) < num)
			// bitshift left mean multiply by two. (while (2 to the quotBits * denom < num))
			quotBits++;

		// You've overshot it- back off by one.
		quotBits--;

		while (quotBits >= 0) {
			if (num >= (denom << quotBits)) {
				// add to quotient and subtract from numerator
				quot += (1 << quotBits);
				num -= (denom << quotBits);
			}
			quotBits--;
		}

		// numerator is holding remainder
		return String.format("%1$d R %2$d", quot, num);
	}
	
	@Test
	public void testQuickDivide() {
		int num = 272;
		int div = 13;
		System.out.println("Reult: " + quickDivide(num, div));
	}

}
