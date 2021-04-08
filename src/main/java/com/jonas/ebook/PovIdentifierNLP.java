package com.jonas.ebook;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
//import java.math.RoundingMode;
//import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
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

import com.jonas.nlp.OpenNLP;

import opennlp.tools.coref.DiscourseEntity;
import opennlp.tools.coref.mention.MentionContext;

//import edu.stanford.nlp.dcoref.CorefChain;

public class PovIdentifierNLP {
	public static String EBOOK = "C:/1.epub";

	public static String CHAPTER_IDENTIFIER_PATTERN = "<p class=\"calibre_10\">";

	public static String SUBCHAPTER_IDENTIFIER_PATTERN = "<p class=\"calibre_12\">";

	public static String[] CHARACTERS = {"Althea", "Malta", "Keffria", "Tintaglia", "Ronica",
			"Jani", "Paragon", "Shreever", "Serilla", "Brashen", "Kennit", "Vivacia", "Wintrow",
			"Etta", "Reyn", "She Who Remembers"};

	public static String[] CANDIDATES_TO_IGNORE = {"The", "He", "She", "It", "They", "We", "My",
			"Your", "Her", "His", "Their", "Our"};

	public static boolean WRITE_TO_FILE = false;

	public static boolean DO_NLP = true;

	public static CorefChains CORE_NLP;

	public static OpenNLP OPEN_NLP;

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
			HashMap<String, Integer> characterCounts;
			String povCharacter = "";
			s = sanitize(s);
			characterCounts = new HashMap<String, Integer>();
			if (DO_NLP) {
				OPEN_NLP = new OpenNLP();
				// Map<Integer, CorefChain> graph = CORE_NLP.getCoreferences(preprocessText(s));
				final String[] sentences = OPEN_NLP.detectSentences(preprocessText(s));
				final DiscourseEntity[] entities = OPEN_NLP.findEntityMentions(sentences);
				// for(Map.Entry<Integer, CorefChain> entry : graph.entrySet()) {
				for (int i = 0; i < entities.length; i++) {
					final DiscourseEntity ent = entities[i];
					final Iterator<MentionContext> mentionGraph = ent.getMentions();

					// String representative =
					// entry.getValue().getRepresentativeMention().mentionSpan;
					// int mentions = entry.getValue().getMentionMap().size();
					String representative = null;
					int mentions = entities[i].getNumMentions();
					while (mentionGraph.hasNext() && representative == null) {
						final MentionContext mc = mentionGraph.next();
						if (mc.toString().matches("[A-Z].*")
								&& mc.toString().trim().length() >= 4) {
							representative = mc.toString().toString().replaceAll("[^A-Za-z ]", "")
									.trim();
							break;
						}
					}
					if (representative != null) {
						characterCounts.put(representative, mentions);
					}

					// if (representative.matches("[A-Z][a-z].*")) {
					// //Strategy: Pare down mention to capitalized words
					// // if characterCounts contains the mention, add to its value
					// // if the mention contains a character counts entry, add to its value
					// String[] reps = representative.split(" ");
					// representative = "";
					// for(int i = 0; i < reps.length; i++) {
					// if(!reps[i].matches("[A-Z].*")) {
					// break;
					// } else {
					// representative += reps[i] + " ";
					// }
					// }
					// representative = representative.trim();

					// if (Arrays.asList(CANDIDATES_TO_IGNORE).contains(representative)) {
					// continue;
					// }

					// if (characterCounts.size() == 0) {
					// characterCounts.put(representative, mentions);
					// } else if (characterCounts.containsKey(representative)) {
					// characterCounts.put(representative, characterCounts.get(representative) +
					// mentions);
					// } else {
					// boolean inserted = false;
					// for(Map.Entry<String, Integer> e : characterCounts.entrySet()) {
					// if (representative.contains(e.getKey())) {
					// characterCounts.put(representative, e.getValue() + mentions);
					// characterCounts.remove(e.getKey());
					// inserted = true;
					// break;
					// }
					// }
					// if (!inserted) {

					// }
					// }

					// System.out.println(representative + ": " + mentions + " mentions");
					// }

				}

			} else {
				characterCounts = getCharacterMap();
				List<String> words = Arrays.asList(s.split(" "));
				for (String w : words) {
					w = w.replaceAll("[^A-Za-z]*", "");
					if (characterCounts.containsKey(w)) {
						characterCounts.put(w, characterCounts.get(w) + 1);
					}
				}
			}

			List<Entry<String, Integer>> characterFrequencies = sortHashMapByValue(characterCounts);
			// double totalMentions = 0.0;
			// for(Map.Entry<String, Integer> entry : characterFrequencies) {
			// totalMentions += entry.getValue();
			// }
			// for(Map.Entry<String, Integer> entry : characterFrequencies) {
			// DecimalFormat df = new DecimalFormat("#.##");
			// df.setRoundingMode(RoundingMode.CEILING);
			// Double d = ((double) entry.getValue()/totalMentions) * 100;
			// if (d > 2) {
			// System.out.println(entry.getKey() + ": " + df.format(d) + "%");
			// }
			// }
			// for(int i = 0; i < 5; i++) {
			// System.out.println(characterFrequencies.get(i).getKey() + ": " +
			// characterFrequencies.get(i).getValue());
			// }
			if (characterFrequencies.size() > 0) {
				povCharacter = characterFrequencies.get(0).getKey();
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
					result.append(s);
				} else {
					result.append(str);
				}
			}

		}
		characterChapters.put(chapter, characters);
		System.out.println("Chapter " + chapter + ": " + characters.toString());
		return result;
	}

	public static String preprocessText(String str) {
		// str = str.replaceAll("\r\n", ". ");
		// str = str.replaceAll("\\. \\. ", ". ");
		str = str.replaceAll(" +", " ").trim();
		return str;
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
		FileOutputStream fout = null;
		ZipOutputStream zout = null;
		if (WRITE_TO_FILE) {
			fout = new FileOutputStream(outfile);
			zout = new ZipOutputStream(fout);
		}

		byte[] buffer = new byte[512];
		if (DO_NLP) {
			// PovIdentifierNLP.CORE_NLP = new CorefChains();
		}
		while (entries.hasMoreElements()) {
			ZipEntry entry = entries.nextElement();
			ZipEntry newEntry = new ZipEntry(entry.getName());
			InputStream in = zipFile.getInputStream(entry);
			StringBuilder sb = new StringBuilder();
			if (entry.getName().matches(".*\\.html")) {
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String s;
				while ((s = br.readLine()) != null) {
					sb.append((s.replaceAll(".â€�", "”") + " "));
					// sb.append("\r\n\r\n");
				}

				if (sb.toString().contains(CHAPTER_IDENTIFIER_PATTERN)) {
					sb = specialProcessing(sb.toString(), chapter);
					chapter++;
				}

				if (WRITE_TO_FILE) {
					zout.putNextEntry(newEntry);
					byte[] data = sb.toString().getBytes();
					zout.write(data, 0, data.length);
				}

				br.close();
			} else if (WRITE_TO_FILE) {
				newEntry.setCompressedSize(-1);
				zout.putNextEntry(newEntry);
				while (0 < in.available()) {
					int read = in.read(buffer);
					zout.write(buffer, 0, read);
				}
			}
			in.close();
			if (WRITE_TO_FILE) {
				zout.closeEntry();
			}
		}
		if (WRITE_TO_FILE) {
			zout.close();
		}
		zipFile.close();
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for (Map.Entry<Integer, List<String>> entry : characterChapters.entrySet()) {
			map.put(entry.getKey(), entry.getValue().toString());
		}
		List<Entry<Integer, String>> characterChapters = sortHashMapByKey(map);
		for (Entry<Integer, String> entry : characterChapters) {
			System.out.println(entry.getKey() + ": " + entry.getValue());
		}
	}
}
