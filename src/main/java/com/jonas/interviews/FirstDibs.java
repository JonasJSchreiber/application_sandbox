package com.jonas.interviews;

import java.util.HashMap;
import java.util.Map;

public class FirstDibs {
    public static void main (String[] args) {
        System.out.println(Boolean.toString(canNoteBeFormed("Hello Java", "Jaakoe")));
    }
    
    // takes two String arguments: magazines, note
    // return boolean: can the note be composed from the text in magazines
    
    //length of magazines m, length of note n
    //initialization takes m, space m
    
    //search
    //arraylist implementation: nm
    //map implementation takes n
    
    public static boolean canNoteBeFormed(String mag, String note) {
    	Map<Character, Integer> magmap = new HashMap<Character, Integer>();
        for (char c : mag.toCharArray()) {
            if (magmap.containsKey(c)) {
                magmap.put(c, magmap.get(c) + 1);
            } else {
                magmap.put(c, 1);
            }
        }
        
        for (char c : note.toCharArray()) {
            if (magmap.get(c) != null && magmap.get(c) > 0) {
                magmap.put(c, magmap.get(c) - 1);
            } else {
                return false;
            }
        }
        return true;
    }
}
