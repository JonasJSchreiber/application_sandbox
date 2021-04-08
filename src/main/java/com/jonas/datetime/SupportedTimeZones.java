package com.jonas.datetime;

public enum SupportedTimeZones {

	UTC("UTC"), EST("EST5EDT"), CST("CST6CDT"), MST("MST7MDT"), PST("PST8PDT");

	private String value;

	public String getValue() {
		return value;
	}

	private SupportedTimeZones(String value) {
		this.value = value;
	}

}
