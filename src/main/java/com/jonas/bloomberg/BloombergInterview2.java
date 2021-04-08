package com.jonas.bloomberg;

//This is the text editor interface. 
//Anything you type or change here will be seen by the other person in real time.

//apple, banana, clue, cold, colludes, shoe, schooled, olds, zebra

//C  L  U  E
//O  L  D  S
//C O L L U D E S -> interlocked word -> real word IF its also in the array

//ALSO pretend as though the array contains every english word
import java.util.*;

public class BloombergInterview2 {

	static String[] english = {"apple", "banana", "shoe", "cold", "schooled", "spit", "ohs", "sped", "aha", "sure", "cris", "sord", "cue", "fils", "obe", "clue", "old", "wee", "ewe", "act", "sos", "brad", "ami", "sophist", "saphead", "scurries", "scoured", "foibles", "collude", "weewee", "ascots", "barmaid"};

	static Set<String> wordMap = new HashSet<String>();
	//Write a function to filter and print each interlocked word and its components
	//colludes = CLUE + olds
	//ascots = ['act', 'sos']
	//scurries = ['sure', 'cris']
	//scoured = ['sord', 'cue']
	//sophist = ['spit', 'ohs']
	//schooled = ['shoe', 'cold']
	//weewee = ['wee', 'ewe']
	//saphead = ['sped', 'aha']
	//foibles = ['fils', 'obe']
	//barmaid = ['brad', 'ami']
	//collude = ['CLUE', 'old']

	//pen, ice, nos
	//pinecones

	public static void putInWordMap(String[] words) {
		for (String s : words) {
			wordMap.add(s);
		}
	}

	public static String[] checkForComponents(String s) {
		char[] array = s.toCharArray();

		char[] word1 = new char[s.length()/2 + 1];
		char[] word2 = new char[s.length()/2 + 1];

		for (int i = 0; i < array.length; i++) {
			if (i % 2 == 0) {
				word1[i/2] = array[i];
			} else {
				word2[i/2] = array[i];
			}
		}

		String word1Str = new String(word1).trim();
		String word2Str = new String(word2).trim();

		if (wordMap.contains(word1Str) && wordMap.contains(word2Str)) {
			return new String[]{word1Str, word2Str};
		} else {
			return null;
		}
	}


	// for word in words 
	//  is there a component set that would yield word when merged? 

	public static void main(String[] args) {
		putInWordMap(english);
		for (String s : wordMap) {
			String[] arr = checkForComponents(s);
			if (arr != null) {
				assert(arr.length == 2);
				System.out.println(
						"Interlocked Word: " + s + 
						" Component Words are: " + arr[0] + " + " + arr[1]);
			}
		}
	}

}
