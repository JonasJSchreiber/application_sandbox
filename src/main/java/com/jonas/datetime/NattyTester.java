package com.jonas.datetime;

import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.junit.Before;
import org.junit.Test;

import com.joestelmach.natty.DateGroup;
import com.joestelmach.natty.Parser;

public class NattyTester {

	int test = 0;
	Parser parser;
	public static Properties loggingProperties = new Properties();
	
	@Before
	public void setup() {
		PropertyConfigurator.configure("./conf/logging.properties");
		parser = new Parser();
	}
	
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
			((Iterable<DateGroup>) parser.parse(s)).forEach(
					group -> group.getDates().forEach(
					d -> System.out.println(test + ": " + d.toString())));
			
//			List<DateGroup> groups = parser.parse(s);
//			for(DateGroup group:groups) {
//				List<Date> dates = group.getDates();
//				for (Date d : dates) {
//					System.out.println(test + ": " + d.toString());	
//				}
//			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}