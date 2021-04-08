package com.jonas.codeeval;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class CodeEval1 {
	public static void main (String[] args) throws IOException {
		File file = new File(args[0]);
		BufferedReader buffer = new BufferedReader(new FileReader(file));
		int numLongestLines = 0;
		Map<String, Integer> map = new HashMap<String, Integer>();
		numLongestLines = Integer.parseInt(buffer.readLine());		
		String line;
		while ((line = buffer.readLine()) != null) {
			map.put(line, line.length());
		}
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(map.entrySet());
    	Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
    		public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 ) {
    			return (o2.getValue()).compareTo(o1.getValue());
    		}
    	});
    	for (int i = 0; i < numLongestLines; i++) {
    		System.out.println(list.get(i).getKey());
    	}
		buffer.close();
	}
}