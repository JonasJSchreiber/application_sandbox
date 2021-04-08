package com.jonas.ebook;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class FrequencyCounts {

	public static String EBOOK = "C:/1.epub";

	public static String CHAPTER_IDENTIFIER_PATTERN = "OEBPS/Text/chapter.*\\.x?html";

	public static String[] CHARACTERS = {"Teia", "Kip", "Gavin", "Karris", "Liv", "Gunner"};

	public static HashMap<String, Integer> getCharacterMap() {
		HashMap<String, Integer> characterMap = new HashMap<String, Integer>();
		for (String s : CHARACTERS) {
			characterMap.put(s, 0);
		}
		return characterMap;
	}

	public static void main(String[] args) throws IOException {

		ZipFile zipFile = new ZipFile(EBOOK);

		Enumeration<? extends ZipEntry> entries = zipFile.entries();

		Map<Integer, String> chapterChars = new HashMap<Integer, String>();

		while (entries.hasMoreElements()) {

			ZipEntry entry = entries.nextElement();

			if (entry.getName().matches(CHAPTER_IDENTIFIER_PATTERN)) {
				HashMap<String, Integer> characterCounts = getCharacterMap();
				InputStream stream = zipFile.getInputStream(entry);
				BufferedReader br = new BufferedReader(new InputStreamReader(stream));
				String s;
				while ((s = br.readLine()) != null) {
					List<String> words = Arrays.asList(s.replaceAll("[^A-Za-z]", " ").split(" "));
					for (String w : words) {
						if (characterCounts.containsKey(w)) {
							characterCounts.put(w, characterCounts.get(w) + 1);
						}
					}
				}
				br.close();
				stream.close();

				List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(
						characterCounts.entrySet());
				Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
					public int compare(Map.Entry<String, Integer> o1,
							Map.Entry<String, Integer> o2) {
						return (o2.getValue()).compareTo(o1.getValue());
					}
				});
				chapterChars.put(Integer.parseInt(entry.getName().replaceAll("[^0-9]", "")),
						list.get(0).getKey());
				// With Frequency Counts
				// (list.get(0).getKey() + ": " + list.get(0).getValue()));
			}
		}

		List<Entry<Integer, String>> sorted = sortHashMapByKey(chapterChars);

		for (Entry<Integer, String> e : sorted) {
			System.out.println("Chapter: " + e.getKey() + ": " + e.getValue());
		}

		zipFile.close();
	}

	public static List<Entry<Integer, String>> sortHashMapByKey(Map<Integer, String> map) {
		List<Entry<Integer, String>> list = new ArrayList<Entry<Integer, String>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, String>>() {
			public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
				return (o1.getKey()).compareTo(o2.getKey());
			}
		});
		return list;
	}
}
