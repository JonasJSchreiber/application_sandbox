package com.jonas.icims;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class JodaTimeTest {

	@Test
	public void test() {
		DateTime dt = new DateTime();
		// translate to London local time
		DateTime dtLondon = dt.withZone(DateTimeZone.forID("Europe/London"));
		System.out.println(dtLondon.toString());
		JodaTimePojo pojo = new JodaTimePojo();
		pojo.setTimeStamp(new DateTime(2020, 5, 20, 12, 25, 35, 45));
		try {
			System.out.println(pojo.toAccessLogJsonString());
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
}
