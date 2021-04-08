package com.jonas.sandbox;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class TextfileProcessingSimple {

	public static void main(String args[]) {
		try {
			String filename = "C:\\output.csv";
			FileInputStream fstream = new FileInputStream(filename);
			File output = new File("C:\\Output.txt");
			FileOutputStream ostream = new FileOutputStream(output);
			PrintWriter out = new PrintWriter(ostream);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				String[] fields = strLine.split(",");
				if (fields.length >= 3) {
					String s = "insert into preference_scope (pref_id, scope_level_id, value) select pref_id, 1, "
							+ fields[1]
							+ " from preference p join destination d on p.dest_id = d.dest_id where d.destination = "
							+ fields[0] + ";";
					out.println(s);
					s = "insert into preference_scope (pref_id, scope_level_id, value) select pref_id, 1, "
							+ fields[2]
							+ " from preference p join destination d on p.dest_id = d.dest_id where d.destination = "
							+ fields[0] + ";";
					out.println(s);
				}
			}
			out.flush();
			out.close();
			in.close();
			System.out.println("Finished Processing");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
