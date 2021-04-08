package com.jonas.sandbox;

import org.junit.Test;

public class ReplaceAllSandbox {
	@Test
	public void testReplaceAll() {
		String toReplace = "CALLBACK_NUMBER}";
		String replacement = "555-1212";
		String body = "Hello {CALLBACK_NUMBER}, this is your captain speaking";
		System.out.println(body.replaceAll(toReplace, replacement));
	}
}
