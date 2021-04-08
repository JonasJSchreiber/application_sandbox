package com.jonas.amazon;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Practice {

	public static String longestCommonSubstring(String s1, String s2) {
		int m = s1.length();
		int n = s2.length();

		int max = 0;
		int endI = 0;

		int[][] dp = new int[m][n];

		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				if (s1.charAt(i) == s2.charAt(j)) {
					if (i == 0 || j == 0) {
						dp[i][j] = 1;
					} else {
						dp[i][j] = dp[i - 1][j - 1] + 1;
					}

					if (max < dp[i][j]) {
						max = dp[i][j];
						endI = i;
					}
				}

			}
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 1; i <= max; i++) {
			sb.append(s1.charAt(endI - max + i));
		}
		return sb.toString();
	}

	@Test
	public void test() {
		String s1 = "sdbfanana";
		String s2 = "pffanama";
		String expected = "fana";
		String actual = longestCommonSubstring(s1, s2);
		assertEquals(expected, actual);
	}
}
