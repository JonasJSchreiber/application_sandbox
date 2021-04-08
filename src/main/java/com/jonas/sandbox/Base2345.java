package com.jonas.sandbox;

public class Base2345 {
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		System.out.println("Starting");
		for (int i = 4; i < 2147483647; i++) {
			if (isAllOnes(i)) {
				System.out.println(i);
			}
		}
		long passed = System.currentTimeMillis() - start;
		System.out.println("Finished. Took " + passed + " milliseconds.");
	}
	
	public static boolean isAllOnes(int n) {
		for (int i = 3; i < 6; i++) {
			if (!baseX(n, i).matches("[0-1]*")) {
				return false;
			}
		}
		return true;
	}
	
	public static String baseX(int n, int x) {
		return Integer.toString(n, x); 
	}
}
