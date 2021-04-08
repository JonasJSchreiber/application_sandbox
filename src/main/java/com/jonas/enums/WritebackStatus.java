package com.jonas.enums;

public enum WritebackStatus {
	Confirmed("C"), Cancelled("X"), Acknowledged("A");
	private String value;

	public String getValue() {
		return value;
	}

	private WritebackStatus(String value) {
		this.value = value;
	}
}