package com.jonas.interviews;

public class Palindromes {
	
	public int findIndexToRemove(String s) {
		for (int i = 0; i < s.length()/2; i++ ) {
			if (s.charAt(i) != s.charAt(s.length() - 1 - i)) {
				if (s.charAt(i + 1) == s.charAt(s.length() - 1 - i)) {
					return i;
				} else {
					return (s.length() - 1 - i);
				}
			}
		}
		return -1;
	}
	
	public static void findBiggestPalindrome2(String s) {
	    for (int i = s.length(); i > 0; i--) {
	        for (int j = 0; j < s.length() - i + 1; j++) {
	            if (isPalindrome(s.substring(j, j+i))) {
	            	System.out.println("true");
	                System.out.println(s.substring(j, j+i));
	                return;
	            }
	        }
	    }
	}

	public static boolean isPalindrome(String s) {
		System.out.println("Evaluating: " + s);
	    for (int i = 0; i < s.length()/2; i++) {
	        if (s.charAt(i) != s.charAt(s.length() - i - 1)) {
	            return false;
	        }
	    }
	    return true;
	}


	public static void main(String[] args) {
	    findBiggestPalindrome2("hello bob olle");
	}

}
