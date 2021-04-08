package com.jonas.regex;

import static org.junit.Assert.assertTrue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Test;

public class RegexTester {

	@Test
	public void test() {
		String s = ".*:.*\\^.*\\^.*";
		String subject = "UbiquityID:ClientID^TenantID^ServiceUser";
		assertTrue(subject.matches(s));
		s = "[^\\s]+[ ]+[^\\s]+.*";
		subject = "hello  yes";
		assertTrue(subject.matches(s));
	}

	@Test
	public void testQuestionMark() {
		String ACCOUNT_MATCH_WAV_PATTERN = "(SMSG_)?\\d{6}_.*";
		assertTrue("SMSG_701020_G.wav".matches(ACCOUNT_MATCH_WAV_PATTERN));
		assertTrue("701020_1_G.wav".matches(ACCOUNT_MATCH_WAV_PATTERN));
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
	public void testNegation() {
		String match = "04[^(SF|03|01)]";
		String test = "04AF";
		System.out.println(test.matches(match));
	}

	@Test
	public void testUnderscores() {
		String body = "_______________________\nhello world";
		body = body.replaceAll("[_]{2,}", "");
		System.out.println(body);
	}
}
