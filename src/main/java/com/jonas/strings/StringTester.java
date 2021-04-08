package com.jonas.strings;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

import com.google.common.base.Optional;

public class StringTester {

	@Test
	public void test() {
		String s = "ahax ";
		try {
			s.replace(null, "asdf");
			System.out.println(s);
		} catch (NullPointerException n) {
			n.printStackTrace();
		}
		try {
			s.replace("asdf", null);
			System.out.println(s);
		} catch (NullPointerException n) {
			n.printStackTrace();
		}
		System.out.println(s);

	}

	@Test
	public void testOptionals() {
		String a = null;
		String b = null;
		String c = "allo";
		String d = Optional.fromNullable(a).or(Optional.fromNullable(b).or(c));
		System.out.println(d);
	}

	protected static String getStringByPattern(String s, String pattern) {
		String result = "";
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(s);
		if (m.find()) {
			int index = m.start();
			int end = m.end();
			result = s.substring(index, end);
		}
		return result;
	}

	@Test
	public void testPrintFields() {
		String s = "\"2669286\",\"KAREN CONNORS\",\"KAREN\",\"CONNORS\","
				+ "\"2017-11-29 01:00:00 PM\",\"6032754061\",\"6032754061\",\"\","
				+ "\"KARENCONNORS@MYFAIRPOINT.NET\",\"F/UP 15\\\"\",\"JOHN WELTER\","
				+ "\"WF      \",\"english\",\"WELTER\",\"20\",\"24\",\"\",\"\","
				+ "\"*AWELTER\",\"35\",\"\",\"\",\"Y\",\"Y\",\"Y\",\"\",\"\","
				+ "\"\",\"A\",\"\",\"ADD\"";
		String[] fields = s.split("(\\\\\\\"\\\",\\\"|,)");
		Arrays.asList(fields).forEach(System.out::println);

		/**
		 * "2669286" "KAREN CONNORS" "KAREN" "CONNORS" "2017-11-29 01:00:00 PM" "6032754061"
		 * "6032754061" "" "KARENCONNORS@MYFAIRPOINT.NET" "F/UP 15\"" "JOHN WELTER" "WF " "english"
		 * "WELTER" "20" "24" "" "" "*AWELTER" "35" "" "" "Y" "Y" "Y" "" "" "" "A" "" "ADD"
		 */
	}

	@Test
	public void testNull() {
		List<String> L = Arrays.asList("", null);
		String x = null;
		String y = "";
		String z = "hello world";
		if (L.contains(x)) {
			System.out.println(L.toString() + " contains: (" + x + ")");
		}
		if (L.contains(y)) {
			System.out.println(L.toString() + " contains: (" + y + ")");
		}
		if (L.contains(z)) {
			System.out.println(L.toString() + " contains: (" + z + ")");
		}
	}
}
