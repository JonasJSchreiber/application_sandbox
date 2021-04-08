package com.jonas.sandbox;

public class PostPreIncrement {

	public static void main(String[] args) {
		System.out.println("Post-Increment");
		for(int i = 0; i < 5;) {
			System.out.println(i++);
		}
		
		System.out.println("\nPre-Increment");
		for(int i = 0; i < 5;) {
			System.out.println(++i);
		}
		
		System.out.println("\nPre-Increment");
		for(int i = 0; i < 5;) {
			System.out.println(i += 1);
		}
	}
}
