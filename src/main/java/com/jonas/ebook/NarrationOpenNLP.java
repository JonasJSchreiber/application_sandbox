package com.jonas.ebook;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
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

import opennlp.tools.coref.DiscourseEntity;
import opennlp.tools.coref.mention.MentionContext;

import org.owasp.html.Handler;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.HtmlSanitizer;
import org.owasp.html.HtmlStreamRenderer;

import com.jonas.nlp.OpenNLP;

public class NarrationOpenNLP {
	public static String EBOOK = "C:/1.epub";
	public static String CHAPTER_IDENTIFIER_PATTERN = "<p class=\"calibre_10\">";
	public static String SUBCHAPTER_IDENTIFIER_PATTERN = "<p class=\"calibre_12\">";
	
	public static OpenNLP OPEN_NLP;	
	public static Map<Integer, List<String>> CHAPTER_CHARACTERS;
	
	public static StringBuilder specialProcessing(String str, int chapter) {
		String[] entries;
		if (SUBCHAPTER_IDENTIFIER_PATTERN.trim().length() > 0) {
			entries = str.split(SUBCHAPTER_IDENTIFIER_PATTERN);
		} else {
			entries = new String[]{ str };
		}
		StringBuilder result = new StringBuilder();
		List<String> characters = new ArrayList<String>();
		for (String s : entries) {
			HashMap<String, Integer> characterCounts;
			String povCharacter;
			s = preprocessText(sanitize(s));
			characterCounts = new HashMap<String, Integer>();
			OPEN_NLP = new OpenNLP();
			final String[] sentences = filterDialogueAndHeadings(OPEN_NLP.detectSentences(s));
			final DiscourseEntity[] entities = OPEN_NLP.findEntityMentions(sentences);
			for (int i = 0; i < entities.length; i++) {
				final DiscourseEntity ent = entities[i];
				final Iterator<MentionContext> mentionGraph = ent.getMentions();

				String representative = null;
				int mentions = entities[i].getNumMentions();
				while (mentionGraph.hasNext() && representative == null) {
					final MentionContext mc = mentionGraph.next();
					if (mc.getHeadTokenTag().matches("NN.*")) {
						representative = mc.getHeadTokenText();
					}
//					if (mc.toString().matches("[A-Z].*") && mc.toString().trim().length() >= 4) {
//						representative = mc.toString().toString().replaceAll("[^A-Za-z ]", "").trim();
//						break;
//					}
				}
				if (representative != null) {
					if (characterCounts.containsKey(representative)) {
						characterCounts.put(representative, characterCounts.get(representative) + mentions);
					} else {
						characterCounts.put(representative, mentions);
					}
					characterCounts.put(representative, mentions);		
				}
				
			} 

			List<Entry<String, Integer>> characterFrequencies = sortHashMapByValue(characterCounts);

			if (characterFrequencies.size() > 0) {
				povCharacter = characterFrequencies.get(0).getKey();
				characters.add(povCharacter);
			}
			result.append(str);
		}
		System.out.println(chapter + ": " + characters);
		CHAPTER_CHARACTERS.put(chapter, characters);
		return result;
	}
	
	@SuppressWarnings("unused")
	public static String[] filterDialogueAndHeadings(String[] sentences) {
		ArrayList<String> filtered = new ArrayList<String>();
		boolean inDialogue = false;
//		for (String s : sentences) {
//			if (s.contains("“") && !s.contains("”")) {
//				inDialogue = true;				
//			} else if (s.contains("”")) {
//				inDialogue = false;
//			} else if (!inDialogue && !s.trim().matches(".*[A-Z]{3,}.*")) {
//				filtered.add(s);
//			}
//		}		
//		return filtered.toArray(new String[filtered.size()]);		
		return sentences;
	}
	
	public static String preprocessText(String str) {
		str = str.replaceAll("  +", ". ").replaceAll("\\.\\.", ".").trim();
		return str;
	}
	
	public static String sanitize(String src) {
	    StringBuilder sb = new StringBuilder();
	    HtmlSanitizer.Policy policy = new HtmlPolicyBuilder().build(HtmlStreamRenderer.create(sb,
	        new Handler<String>() {
	          public void handle(String x) { }
	        }));
	    HtmlSanitizer.sanitize(src, policy);
	    return sb.toString();
	}
	
	public static List<Entry<String, Integer>> sortHashMapByValue(HashMap<String, Integer> map) {
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(map.entrySet());
    	Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
    		public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 ) {
    			return (o2.getValue()).compareTo(o1.getValue());
    		}
    	});
    	return list;
	}
	
	public static List<Entry<Integer, String>> sortHashMapByKey(HashMap<Integer, String> map) {
		List<Entry<Integer, String>> list = new ArrayList<Entry<Integer, String>>(map.entrySet());
    	Collections.sort(list, new Comparator<Map.Entry<Integer, String>>() {
    		public int compare( Map.Entry<Integer, String> o1, Map.Entry<Integer, String> o2 ) {
    			return (o1.getKey()).compareTo(o2.getKey());
    		}
    	});
    	return list;
	}
	
	public static void main(String[] args) throws IOException {
		ZipFile zipFile = new ZipFile(EBOOK);
		NarrationOpenNLP.CHAPTER_CHARACTERS = new HashMap<Integer, List<String>>();
		int chapter = 0;
		Enumeration<? extends ZipEntry> entries = zipFile.entries();
		while(entries.hasMoreElements() && chapter <= 5){
			ZipEntry entry = entries.nextElement(); 
			InputStream in = zipFile.getInputStream(entry);
			StringBuilder sb = new StringBuilder();
			if (entry.getName().matches(".*\\.html")) {
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
//				String s;
//				while ((s = br.readLine()) != null) {
//					sb.append((s.replaceAll(".â€�", "”") + " "));	
//				}	
				if (sb.toString().contains(CHAPTER_IDENTIFIER_PATTERN)) {
					sb = specialProcessing(sb.toString(), chapter);
					chapter++;
				}
				br.close();
			} 
			in.close();
			
		}
		
		zipFile.close();
		
		HashMap<Integer, String> map = new HashMap<Integer, String>();
		for(Map.Entry<Integer, List<String>> entry : CHAPTER_CHARACTERS.entrySet()) {
			map.put(entry.getKey(), entry.getValue().toString());
		}
		
		File file = new File("C:/output.txt");
		PrintWriter out = new PrintWriter(file);
		List<Entry<Integer, String>> characterChapters = sortHashMapByKey(map);
		for(Entry<Integer, String> entry : characterChapters) {
			out.println(entry.getKey() + ": " + entry.getValue());
		}
		out.close();
		System.out.println("Finished Processing");
	}
}
