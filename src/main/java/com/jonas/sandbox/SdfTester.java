package com.jonas.sandbox;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SdfTester {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String datetime = "2014-12-16 10:30:00.000";
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
		try {
			date = sdf.parse(datetime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(date.toString());

	}

}
