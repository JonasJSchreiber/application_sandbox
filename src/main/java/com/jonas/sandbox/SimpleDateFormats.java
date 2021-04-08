package com.jonas.sandbox;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class SimpleDateFormats {

	public static void main(String[] args) {
		String[] formats = {"M/d/yyyy h:mm a","MM/dd/yyyy HH:mm","yyyy-mm-dd HH:mm:ss","yyyy MMMMM dd hh:mm aaa","yyyy.MM.dd G 'at' HH:mm:ss.SSS z","EEE, MMM d, ''yy h:mm a"};
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.DAY_OF_MONTH, 8);
		Date d = cal.getTime();
		for (String s : formats) {
			SimpleDateFormat sdf = new SimpleDateFormat(s);
			
			
			System.out.println(sdf.format(d) + "\t" + s);
		}
	}
}
