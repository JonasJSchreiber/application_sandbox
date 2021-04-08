package com.jonas.datetime;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class DateUtils {

	public static void main(String[] args) {
		String startDate = "20160320114500PDT";
		startDate = DateUtils.convertTimeZone(startDate.replaceAll("\\d", "").toUpperCase(),
				"yyyyMMddHHmmssz", startDate, SupportedTimeZones.EST, "yyyyMMddHHmmss");
		System.out.println(startDate);
		// if (!DateUtils.isValidFormat("yyyyMMddHHmmssz", startDate)) {
		//
		// } else {
		// Date d = DateUtils.toDate("yyyyMMddHHmmssz", startDate);
		// Date d2 = d.from(instant)
		// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssz");
		// String s = sdf.format(d);
		// System.out.println(s);
		// SimpleDateFormat sdf2 = new SimpleDateFormat("z");
		// String s2 = sdf2.format(new Date());
		// System.out.println(s2);
		// String a = DateUtils.convertTimeZone("UTC", "yyyyMMddHHmmssz", "20160320114500PDT",
		// SupportedTimeZones.CST, "yyyyMMddHHmmss");
		// System.out.println(a);
		//
		// }

	}

	@Test
	public void testConverTimeZone() {
		String a = DateUtils.convertTimeZone("UTC", "yyyyMMddHHmmss", "20160105120101",
				SupportedTimeZones.EST, "yyyyMMddHHmmss");
		assertEquals(a, "20160105070101");
	}

	public static String formatToStandardDate(String splitIdentifier, String dateInString) {
		/*
		 * This will work if the dateInString is month/day/year format
		 */
		String[] stringArray = dateInString.split(splitIdentifier);
		if (stringArray.length == 3) {
			String month = stringArray[0];
			String day = stringArray[1];
			String year = stringArray[2];

			if (!StringUtils.isEmpty(month) && !StringUtils.isEmpty(day)
					&& !StringUtils.isEmpty(year)) {
				if (month.length() < 2) {
					month = "0" + month;

				}
				if (day.length() < 2) {
					day = "0" + day;
				}
				return month + splitIdentifier + day + splitIdentifier + year;
			}
		}
		return dateInString;
	}

	public static Date toDate(String format, String dateInString) {
		if (isValidFormat(format, dateInString)) {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			Date date = null;
			try {
				date = formatter.parse(dateInString);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			return date;
		} else {
			throw new RuntimeException("invalid date format");
		}

	}

	public static String toString(String format, Date date) {
		String dateString = null;
		SimpleDateFormat sdfr = new SimpleDateFormat(format);
		try {
			dateString = sdfr.format(date);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return dateString;
	}

	public static boolean isValidFormat(String format, String value) {
		Date date = null;
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			date = sdf.parse(value);
			if (!value.equals(sdf.format(date))) {
				date = null;
			}
		} catch (ParseException ex) {
			ex.printStackTrace();
		}
		return date != null;
	}

	public static String convertTimeZone(String sourceTimezone, String sourceDateFormat,
			String sourceDate, SupportedTimeZones targetTimezone, String targetDateformat) {

		ZoneId sourceZoneId = TimeZone.getTimeZone(sourceTimezone).toZoneId();
		ZoneId targetZoneId = TimeZone.getTimeZone(targetTimezone.getValue()).toZoneId();
		ZonedDateTime sourceZoneDateTime = LocalDateTime
				.parse(sourceDate, DateTimeFormatter.ofPattern(sourceDateFormat))
				.atZone(sourceZoneId);
		ZonedDateTime targetZoneDateTime = sourceZoneDateTime.withZoneSameInstant(targetZoneId);
		return DateTimeFormatter.ofPattern(targetDateformat).format(targetZoneDateTime);
	}

	public static boolean exists(String timeZone) {
		for (SupportedTimeZones supportedTimeZone : SupportedTimeZones.values()) {
			if (supportedTimeZone.toString().equals(timeZone)) {
				return true;
			}
		}
		return false;
	}

}
