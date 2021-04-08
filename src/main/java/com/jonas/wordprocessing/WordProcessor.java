package com.jonas.wordprocessing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class WordProcessor {

	public static String TARGET_FILE = "C:/1.csv";
	
	public static String OUTPUT_FILE = 
			TARGET_FILE.substring(0, TARGET_FILE.length() - 4) 
			+ "-edited" 
			+ TARGET_FILE.substring(TARGET_FILE.length() - 4);
	
	public void specialProcessing(BufferedReader br, PrintWriter out) throws IOException {
		String s;
		
		while ((s = br.readLine()) != null) {
			String[] fields = s.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
			String[] nameFields = fields[1].replace("\"", "").split(",");
			String name = nameFields[1].trim() + " " + nameFields[0].trim();
			String s2 = fields[0] + ",\"" + name + "\"," + fields[2] + "," + fields[3];
			out.println(s2);
		}
	}

	public static void main(String[] args) {
		WordProcessor sb = new WordProcessor();
		
		try {
			sb.go();
			System.out.println("Finished Processing");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void go() throws IOException {
		File file = new File(TARGET_FILE);
		BufferedReader br = new BufferedReader(
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
