package com.jonas.sandbox.test;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

public class BasicSandbox {

	@Test
	public void testNums() {
		long x = 1000;
		String s = " limit " + (int) (.1 * x);
		System.out.println(s);
	}

	@Test
	public void testRandoms() {
		String body = "Hello 1234 Hi!";
		if (body != null && body.length() > 157) {
			body = body.substring(0, 157);
		}
		body += " " + RandomStringUtils.randomAlphabetic(2);
		System.out.println(body);
	}
}
