package com.jonas.wordprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class WordProcessor2 {

	public static String TARGET_FILE = "C:/1.csv";
	public static String TARGET_FILE2 = "C:/2.csv";
	
	public Map<String, String> apptNamesToId = new HashMap<>();
	
	public static String OUTPUT_FILE = 
			TARGET_FILE.substring(0, TARGET_FILE.length() - 4) 
			+ "-edited" 
			+ TARGET_FILE.substring(TARGET_FILE.length() - 4);
	
	public void specialProcessing(BufferedReader br, PrintWriter out) throws IOException {
		String s;
		
		while ((s = br.readLine()) != null) {
			String[] fields = s.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
			if (apptNamesToId.get(fields[1]) != null) {
				s += "," + apptNamesToId.get(fields[1]);
			}
			out.println(s);
		}
	}
	
	public void buildApptIdMap(BufferedReader br) throws IOException {
		String s;
		
		while ((s = br.readLine()) != null) {
			String[] fields = s.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
			for (String f : fields) {
				f = f.replace("\"", "");
			}
			if (apptNamesToId.containsKey(fields[1])) {
				System.out.println("Second Appointment Encountered for: " + fields[1]);
				System.exit(1);
			}
			apptNamesToId.put(fields[1], fields[0]);
		}
	}

	public static void main(String[] args) {
		WordProcessor2 sb = new WordProcessor2();
		
		try {
			sb.go();
			System.out.println("Finished Processing");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void go() throws IOException {

		File file = new File(TARGET_FILE2);
		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(file)));
		buildApptIdMap(br);
		
		br.close();
		
		file = new File(TARGET_FILE);
		br = new BufferedReader(
				new InputStreamReader(new FileInputStream(file)));

		File output = new File(
				TARGET_FILE.substring(0, TARGET_FILE.length() - 4) 
				+ "-edited" 
				+ TARGET_FILE.substring(TARGET_FILE.length() - 4));
		FileOutputStream ostream = new FileOutputStream(output);
		PrintWriter out = new PrintWriter(ostream);
		
		specialProcessing(br, out);
		
		br.close();
		out.flush();
	    out.close();
	}

}
