package com.jonas.bloomberg;

public class BloombergInterview {

	public static void findBiggestPalindromeWRONG(String s) {
		for (int i = s.length(); i > 0; i--) {
			for (int j = 0; j < s.length() - i; j++) {
				if (isPalindrome(s.substring(j, i - j))) {
					System.out.println(s.substring(j, i - j));
				}
			}
		}
	}

	public static void findBiggestPalindrome2(String s) {
		for (int i = s.length(); i > 0; i--) {
			for (int j = 0; j <= s.length() - i; j++) {
				if (isPalindrome(s.substring(j, j + i))) {
					System.out.println(s.substring(j, j + i));
					return;
				}
			}
		}
	}

	public static boolean isPalindrome(String s) {
		for (int i = 0; i < s.length() / 2; i++) {
			if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		// System.out.println(isPalindrome("boob"));
		findBiggestPalindrome2("hello bob olle");
	}
}