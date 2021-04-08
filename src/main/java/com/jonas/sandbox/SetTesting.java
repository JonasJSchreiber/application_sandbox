package com.jonas.sandbox;

import java.util.HashSet;
import java.util.Set;

public class SetTesting {
	public static void main(String[] args) {
		Set<String> values = new HashSet<String>();
		values.add("abc");
		values.add("def");
		for (String s : values) {
			System.out.println(s);
		}
		values.remove("ghi");
		for (String s : values) {
			System.out.println(s);
		}
		values.remove("abc");
		for (String s : values) {
			System.out.println(s);
		}
	}
}
