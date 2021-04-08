package com.jonas.sandbox;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

public class FieldPrinter {
	private static String DELIMITER = ",";
	
	public static void main(String args[]) {
		doit();
	}
	
	public static void doit() {

		String filename = "C:\\2.txt";
		FileInputStream fstream;
		try {
			fstream = new FileInputStream(filename);
			BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
			String strLine;
				
			while ((strLine = br.readLine()) != null) {
				String[] fields = strLine.split(DELIMITER);
				int i = 0;
				System.out.println("Fields Length is: " + fields.length);
				for (String s : fields) {
					System.out.println(i + ": " + s);
					i++;			
				}
			}
			br.close();
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
