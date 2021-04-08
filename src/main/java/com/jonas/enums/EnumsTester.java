package com.jonas.enums;

import org.junit.Test;

public class EnumsTester {

	@Test
	public void test() {
		ResponseMapping r = new ResponseMapping();
		r.setApplicable(true);

		// Confirms
		r.setWritebackStatus(WritebackStatus.Confirmed);

		System.out.println(r.getWritebackStatus().getValue());
	}
}
