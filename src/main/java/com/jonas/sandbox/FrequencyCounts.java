package com.jonas.sandbox;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
public class FrequencyCounts {
	
	public static void main(String args[])
	{
		
		try
		{
			String filename = "C:\\1.csv";
			File output = new File("C:\\Output.csv");
			PrintWriter out = new PrintWriter(new FileOutputStream(output));
			// Get the object of DataInputStream
			BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(filename)));
			String strLine;
			Map<String, Integer> emails = new TreeMap<String, Integer>();
			//Read File Line By Line
			while ((strLine = br.readLine()) != null) 	
			{
				String[] fields = strLine.split(",");
				if (fields.length >= 6) {
					if (emails.containsKey(fields[5])) {
						int val = emails.get(fields[5]) + 1;
						emails.remove(fields[5]);
						emails.put(fields[5], val);
					} else {
						emails.put(fields[5], 1);
					}
				}
			}
			List<Entry<String, Integer>> emailList = sortHashMapByValue(emails);
			for(Map.Entry<String, Integer> e : emailList) {
				out.println(e.getKey() + "," + e.getValue());
			}
			br.close();
			out.flush();
		    out.close();
			System.out.println("Finished Processing");
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static List<Entry<String, Integer>> sortHashMapByValue(Map<String, Integer> map) {
		List<Entry<String, Integer>> list = new ArrayList<Entry<String, Integer>>(map.entrySet());
    	Collections.sort(list, new Comparator<Map.Entry<String, Integer>>() {
    		public int compare( Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2 ) {
    			return (o2.getValue()).compareTo(o1.getValue());
    		}
    	});
    	return list;
	}
}
