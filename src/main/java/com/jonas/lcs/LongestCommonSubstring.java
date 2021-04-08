package com.jonas.lcs;

public class LongestCommonSubstring {
	
	public static String longestSubstr(String s1, String s2) throws AssertionError {
		if (s1 == null || s1.isEmpty() || s2 == null || s2.isEmpty()) {
			throw new AssertionError("You need to call this function with non empty strings");
		}
		
		int maxLen = 0;
		String longest = "";
		int fl = s1.length();
		int sl = s2.length();
		int[][] table = new int[fl + 1][sl + 1];

		for (int s = 0; s <= sl; s++) {
			table[0][s] = 0;
		}
		
		for (int f = 0; f <= fl; f++) {
			table[f][0] = 0;
		}

		for (int i = 1; i <= fl; i++) {
			for (int j = 1; j <= sl; j++) {
				if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
					if (i == 1 || j == 1) {
						table[i][j] = 1;
					} else {
						table[i][j] = table[i - 1][j - 1] + 1;
					}
					if (table[i][j] > maxLen) {
						maxLen = table[i][j];
						longest = s1.substring(i - maxLen, i);
					}
				}
			}
		}
		printTable(table, s1, s2);
		return longest;
	}

	public static void main(String[] args) {
		try {
			System.out.println("\n" + longestSubstr("aaaaaababa", "ababaaa"));
		} catch (AssertionError e) {
			System.out.println(e.getMessage());
		}
	}
	
	public static void printTable(int[][] table, String s1, String s2) {
		System.out.print("  # ");
		for (int i = 0; i < s2.length(); i++) {
			System.out.print(s2.charAt(i) + " ");
		}
		System.out.println();
		for (int i = 0; i < table.length; i++) {
			if (i > 0) {
				System.out.print(s1.charAt(i - 1) + " ");	
			} else {
				System.out.print("# ");
			}
			for (int j = 0; j < table[0].length; j++) {
				System.out.print(table[i][j] + " ");
			}
			System.out.println();
		}
	}
}
