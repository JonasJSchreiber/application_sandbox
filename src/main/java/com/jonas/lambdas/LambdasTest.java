package com.jonas.lambdas;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class LambdasTest {

	@Test
	public void test() {
		String target = "";
		String daysBefore = "14,7,2";
		if (daysBefore.contains(",")) {
			List<Integer> daysBeforeList = new ArrayList<>();
			Arrays.asList(daysBefore.split(","))
					.forEach(i -> daysBeforeList.add(Integer.parseInt((String) i)));
			target = Collections.max(daysBeforeList).toString();
		} else {
			target = daysBefore;
		}
		assertEquals("14", target);
	}
}
