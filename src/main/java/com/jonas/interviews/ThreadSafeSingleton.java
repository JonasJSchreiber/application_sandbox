package com.jonas.interviews;

public class ThreadSafeSingleton {
	
	private static final Object instance = new Object();
	 
	protected ThreadSafeSingleton() {	}
 
	// Runtime initialization
	// By defualt ThreadSafe
	public static Object getInstance() {
		return instance;
	}
}
