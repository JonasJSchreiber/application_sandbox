package com.jonas.datetime;

import org.junit.Test;
import org.pojava.datetime.DateTime;

public class POJavaTester {
	int test = 0;
	
	@Test
	public void testDatetime() {
		long start = System.nanoTime();
		parseDate("26-Feb-2020 9:43:17 pm");
		parseDate("2001.07.04 AD at 12:08:56 PDT");
		parseDate("Wed, Jul 4, '01");
		parseDate("12:08 PM");
		parseDate("12 o'clock PM, Pacific Daylight Time");
		parseDate("0:08 PM, PDT");
		parseDate("02001.July.04 AD 12:08 PM");
		parseDate("Wed, 4 Jul 2001 12:08:56 -0700");
		parseDate("010704120856-0700");
		parseDate("2001-07-04T12:08:56.235-0700");
		parseDate("2001-07-04T12:08:56.235-07:00");
		parseDate("9/24/2014 9:08PM");
		parseDate("9/10/2014 9:08PM");
		parseDate("9-10-2014 9:08PM");
		parseDate("9-10-14 9:08 PM");
		parseDate("9-10-14 9:08 P");
		System.out.println("Test took " + (System.nanoTime() - start) + " ns");
	}
	
	public void parseDate(String s) {
		++test;
		try {
			DateTime dt = new DateTime(s);
			System.out.println(test + ": " + dt.toDate().toString());
		} catch (Exception e) {
//			e.printStackTrace();
		}
	}
	
}
