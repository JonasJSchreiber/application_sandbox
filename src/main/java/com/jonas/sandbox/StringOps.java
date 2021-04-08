package com.jonas.sandbox;

import org.junit.Test;

@SuppressWarnings("unused")
public class StringOps {
	private static String SMS_IDENTIFIER = "text";
	private static String EMAIL_IDENTIFIER = "(e-mail|email)";
	private static String CALL_IDENTIFIER = "[^(text)|e-mail|email)]";
	private static String BILL_REMINDER_APPT_MATCH_PATTERN = ".*$.*";
	private static String BILL_REMINDER_TIME_PATTERN = "[0-9]{1,2}:[0-9]{2} .M";

	public static void main(String[] args) {

		String x = "\"1051553\"‚\"BENNETT,YVETTE\"‚\"YVETTE\"‚\"BENNETT\"‚\"2016-03-24 1515\"‚\"(830)386-0851\"‚\"\"‚\"(830)379-2411\"‚\"\"‚\"REV AND H.O. TREATMENT\"‚\"\"‚\"PHYSICAL THERAPY\"‚\"\"‚\"\"‚\"PT\"‚\"PT.REV+HO\"‚\"M000053777\"‚\"\"‚\"\"‚\"\"‚\"\"‚\"\"‚\"\"‚\"\"‚\"\"‚\"\"‚\"\"‚\"\"‚\"BOOKED\"‚\"\"";
		String firstName = "John";
		String fullName = "John Johnson";
		String lastName = "";
		String[] names = fullName.split("[, ]");
		for (String n : names) {
			if (!n.equals(firstName)) {
				lastName += " " + n;
			}
		}
		lastName = lastName.trim().replace(",", "");

		System.out.println("Last name is: " + lastName);

		firstName = "John";
		fullName = "Johnson, John";
		lastName = "";
		names = fullName.split("[, ]");
		for (String n : names) {
			if (!n.equals(firstName)) {
				lastName += " " + n;
			}
		}
		lastName = lastName.trim().replace(",", "");

		System.out.println("Last name is: " + lastName);

		firstName = "John";
		fullName = "Jacob Johnson, John";
		lastName = "";
		names = fullName.split("[, ]");
		for (String n : names) {
			if (!n.equals(firstName)) {
				lastName += " " + n;
			}
		}
		lastName = lastName.trim().replace(",", "");

		System.out.println("Last name is: " + lastName);

		firstName = "";
		fullName = "Jacob , John";
		lastName = "";
		names = fullName.split("[, ]");
		for (String n : names) {
			if (!n.equals(firstName)) {
				lastName += " " + n;
			}
		}
		lastName = lastName.trim().replace(",", "");

		System.out.println("Last name is: " + lastName);

		firstName = "John";
		fullName = "John Sinclair John Johnson";
		lastName = "";
		names = fullName.split("[, ]");
		for (String n : names) {
			if (!n.equals(firstName)) {
				lastName += " " + n;
			}
		}
		lastName = lastName.trim().replace(",", "");

		System.out.println("Last name is: " + lastName);
	}

	public static String sanitizeEmailAddress(String address) {
		String ePattern = "[ !#$%&'*+/=?^,_`{|}~-]";
		return address.replaceAll(ePattern, ".").replaceAll("\\.+", ".") + "@talksoftonline.com";
	}

	public static String getFromAddress(String from) {
		if (from.matches(".*@(aol|yahoo)\\.com")) {
			from = "no-reply@talksoftonline.com";
		}

		return from;
	}

	@Test
	public void testRegexReplacement() {
		System.out.println(makeRegexSafe("hel\"lo\\ my name is jonas"));
	}

	public String makeRegexSafe(String s) {
		return s.replaceAll("\"", "").replace("\\", "\\\\");
	}

	@Test
	public void testRegex() {
		String match = "[^-]*";
		String s = "Lindsay Eckman CNM";

		System.out.println(s.matches(match) ? "Matches" : "Doesn't Match");
	}
}
