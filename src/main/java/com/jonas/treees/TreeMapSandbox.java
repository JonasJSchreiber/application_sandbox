package com.jonas.treees;

import java.util.Map;
import java.util.TreeMap;

import org.junit.Test;

public class TreeMapSandbox {
	
	@Test
	public void testTreeMap() {
		try {
			Map<String, String> tm = new TreeMap<String, String>();
			tm.put("hello", "world");
			tm.put("alley", "oop");
			for (Map.Entry<String, String> e : tm.entrySet()) {
				System.out.println("Key: " + e.getKey() + " Value: " + e.getValue());
			}
			tm.put(null, null);
			for (Map.Entry<String, String> e : tm.entrySet()) {
				System.out.println("Key: " + e.getKey() + " Value: " + e.getValue());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
