package com.jonas.letterpress;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SpellingBee {

	public static char keyLetter = 'W';
	public static String inputLetters = "WANTERY";
	public static String dictionaryPath = "./data/letterpresswords.txt";

	public Set<String> dict = new HashSet<String>();

	public static void main(String[] args) {
		SpellingBee sb = new SpellingBee();

		try {
			sb.initializeDict();
			List<String> words = sb.getWords();
			Collections.sort(words, new StringDiversityComparator());
			for (String s : words) {
				System.out.println(s);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static class StringDiversityComparator implements java.util.Comparator<String> {
		public int compare(String s1, String s2) {
			// based on diversity of string
			Set<Character> s1set = new HashSet<Character>();
			Set<Character> s2set = new HashSet<Character>();
			for (char c : s1.toLowerCase().toCharArray()) {
				s1set.add(c);
			}
			for (char c : s2.toLowerCase().toCharArray()) {
				s2set.add(c);
			}
			if (s1set.size() == s2set.size()) {
				return s1.length() - s2.length();
			} else {
				return s1set.size() - s2set.size();
			}
		}
	}

	public void initializeDict() throws IOException {
		File file = new File(dictionaryPath);
		System.out.println(file.getAbsolutePath());
		BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		String str;

		// go entry by entry in scrabblewords.tx
		while ((str = br.readLine()) != null) {
			if (str.length() >= 5) {
				dict.add(str);
			}
		}
		br.close();
	}

	public List<String> getWords() {
		List<String> matches = new ArrayList<String>();
		// for word in dictionary
		// if the keyletter is in word
		// and if each letter is contained in the input
		// add to matches

		for (String s : dict) {
			if (s.indexOf(keyLetter) >= 0) {
				boolean add = true;
				for (char c : s.toCharArray()) {
					if (inputLetters.indexOf(c) < 0) {
						add = false;
						break;
					}
				}
				if (add) {
					matches.add(s);
				}
			}
		}
		return matches;
	}

}
