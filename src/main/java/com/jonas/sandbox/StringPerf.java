package com.jonas.sandbox;

import java.util.HashMap;
import java.util.regex.Pattern;

public class StringPerf {
	
	public static HashMap<String, Pattern> precompiledPatterns;
	
	public static void main(String[] args) {
		precompiledPatterns = new HashMap<String, Pattern>();
		String regex = "BOROWSKY";
		String testPattern = "Borowsky";
		System.out.println(matches(testPattern, regex));
		Long start = System.nanoTime();
		for (int i = 0; i < 10000000; i++) {
			matches(testPattern, regex);
		}
		Long end = System.nanoTime();
		Long time = end-start;
		float  d = ((float) time/1000000000);
		System.out.println("Took: " + d + " seconds");
	}
	
	public static boolean matches(String input, String regex) {
        Pattern p = null;
        if (!precompiledPatterns.containsKey(regex)) {
            p = Pattern.compile(regex);
            precompiledPatterns.put(regex, p);
        } else {
            p = precompiledPatterns.get(regex);
        }
        return p.matcher(input.toUpperCase()).matches();
    }

}
