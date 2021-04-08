package com.jonas.ebook;

import java.io.BufferedReader;
import java.io.FileOutputStream;
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
import java.util.zip.ZipOutputStream;

import org.owasp.html.Handler;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.HtmlSanitizer;
import org.owasp.html.HtmlStreamRenderer;

public class FrequencyCountsEdit {

	public static String EBOOK = "C:/PerfLogs/1.epub";

	public static final String FILENAME_PATTERN = ".*\\.xhtml";

	public static String CHAPTER_IDENTIFIER_PATTERN = "<p class=\"pn\">";

	public static String SUBCHAPTER_IDENTIFIER_PATTERN = "<p class=\"sborn\">";

	public static String[] CHARACTERS = {"Aelin", "Chaol", "Dorian", "Manon", "Rowan", "Elide",
			"Lorcan", "Aedion", "Nesryn", "Yrene", "Lysandra"};

	public static final String SPECIAL_CHARACTERS = "“‘’”";

	public static boolean WRITE_TO_FILE = false;

	public static Map<Integer, List<String>> characterChapters = new HashMap<Integer, List<String>>();

	public static HashMap<String, Integer> getCharacterMap() {
		HashMap<String, Integer> characterMap = new HashMap<String, Integer>();
		for (String s : CHARACTERS) {
			characterMap.put(s, 0);
		}
		return characterMap;
	}

	public static List<Entry<String, Integer>> sortHashMapByValue(HashMap<String, Integer> map) {
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
			public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
				return (o2.getValue()).compareTo(o1.getValue());
			}
		});
		return list;
	}

	public static List<Entry<Integer, String>> sortHashMapByKey(HashMap<Integer, String> map) {
		List<Entry<Integer, String>> list = new ArrayList<Entry<Integer, String>>(map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<Integer, String>>() {
			public int compare(Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2) {
				return (o1.getKey()).compareTo(o2.getKey());
			}
		});
		return list;
	}

	public static StringBuilder specialProcessing(String str, int chapter) {
		String[] entries;
		if (SUBCHAPTER_IDENTIFIER_PATTERN.trim().length() > 0) {
			entries = str.split(SUBCHAPTER_IDENTIFIER_PATTERN);
		} else {
			entries = new String[]{str};
		}
		StringBuilder result = new StringBuilder();
		List<String> characters = new ArrayList<String>();
		for (String s : entries) {
			s = sanitize(s);
			HashMap<String, Integer> characterCounts = getCharacterMap();

			List<String> words = Arrays.asList(s.split(" "));
			for (String w : words) {
				w = w.replaceAll("[^A-Za-z]*", "");
				if (characterCounts.containsKey(w)) {
					characterCounts.put(w, characterCounts.get(w) + 1);
				}
			}

			List<Entry<String, Integer>> characterFrequencies = sortHashMapByValue(characterCounts);
			String povCharacter = characterFrequencies.get(0).getKey();
			characters.add(povCharacter);

			if (WRITE_TO_FILE) {
				povCharacter = " <p class=\"calibre_5\"><span class=\"calibre2\"><span class=\"bold\">"
						+ povCharacter + "</span></span></p>";
				if (!s.contains(
						"&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span></p>")) {
					s = povCharacter + s;
				} else {
					s = povCharacter + s;
				}
			}
			result.append(s);
		}
		characterChapters.put(chapter, characters);
		return result;
	}

	public static String sanitize(String src) {
		StringBuilder sb = new StringBuilder();
		HtmlSanitizer.Policy policy = new HtmlPolicyBuilder()
				.build(HtmlStreamRenderer.create(sb, new Handler<String>() {
					public void handle(String x) {
					}
				}));
		HtmlSanitizer.sanitize(src, policy);
		return sb.toString();
	}

	public static void main(String[] args) throws IOException {

		String outfile = EBOOK.replace(".epub", "-edited.epub");
		ZipFile zipFile = new ZipFile(EBOOK);
		int chapter = 0;
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		FileOutputStream fout = new FileOutputStream(outfile);
		ZipOutputStream zout = new ZipOutputStream(fout);
		byte[] buffer = new byte[512];
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			ZipEntry newEntry = new ZipEntry(entry.getName());
			InputStream in = zipFile.getInputStream(entry);
			StringBuilder sb = new StringBuilder();
			if (entry.getName().matches(FILENAME_PATTERN)) {
				BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
				String s;
				while ((s = br.readLine()) != null) {
					sb.append(s.replaceAll(SPECIAL_CHARACTERS, " ")).append("\r\n\r\n");
				}
				if (sb.toString().contains(CHAPTER_IDENTIFIER_PATTERN)
						&& !entry.getName().contains("part")) {
					Integer chapterNum = 0;
					try {
						chapterNum = Integer
							.parseInt(entry.getName().replace("OEBPS/Text/chapter", "")
									.replace(".xhtml", ""));
					} catch (NumberFormatException e) {
						e.printStackTrace();
						chapterNum = chapter;
					}
					sb = specialProcessing(sb.toString(), chapterNum);
					chapter++;
				}

				br.close();
			}
			if (WRITE_TO_FILE) {
				zout.putNextEntry(newEntry);
				byte[] data = sb.toString().getBytes();
				zout.write(data, 0, data.length);
			} else {
				newEntry.setCompressedSize(-1);
				zout.putNextEntry(newEntry);
				while (0 < in.available()) {
					int read = in.read(buffer);
					zout.write(buffer, 0, read);
				}
			}
			in.close();
			zout.closeEntry();
		}

		zout.close();
		zipFile.close();
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (Map.Entry<Integer, List<String>> entry : characterChapters.entrySet()) {
			map.put(entry.getKey(), entry.getValue().toString());
		}
		List<Entry<Integer, String>> characterChapters = sortHashMapByKey(map);
		for (Entry<Integer, String> entry : characterChapters) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
		System.out.println("finished processing");
	}
}
