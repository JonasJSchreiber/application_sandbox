package com.jons.streams;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

public class StreamSortTest {

	@Test
	public void test() {
		List<String> customFiles = Arrays.asList("700710_100_G.wav", "700710_10_G.wav",
				"700710_10_G.wav", "700710_1_G.wav",
				"700710_20_G.wav");
		List<String> sorted = customFiles.stream()
				.sorted((s1, s2) -> s1.compareTo(s2)).collect(Collectors.toList());
		System.out.println("Unsorted: ");
		customFiles.stream().forEach(System.out::println);
		System.out.println("\nSorted: ");
		sorted.stream().forEach(System.out::println);

		Collections.sort(customFiles, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				try {
					return Integer.parseInt(o1.substring(o1.indexOf("_") + 1,
							o1.indexOf("_", o1.indexOf("_") + 1)))
							- Integer.parseInt(o2.substring(o2.indexOf("_") + 1,
									o2.indexOf("_", o2.indexOf("_") + 1)));
				} catch (Exception e) {
					return o1.compareTo(o2);
				}
			}
		});

		System.out.println("\nSorted: ");
		customFiles.stream().forEach(System.out::println);
	}

}
