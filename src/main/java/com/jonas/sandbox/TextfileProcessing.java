package com.jonas.sandbox;
import java.io.*;

public class TextfileProcessing {
	final static String PRODUCT_ID = "RemindMe";
	public static void main(String args[]) {
		try {
			String filename = "C:\\1.txt";
			FileInputStream fstream = new FileInputStream(filename);
			File output = new File("C:\\Output.txt");
			FileOutputStream ostream = new FileOutputStream(output);
			PrintWriter out = new PrintWriter(ostream);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				String[] fields = strLine.replaceAll("(;| |\\=)+", " ").split(" ");

				String friendlyName = "";
				String[] nameFields = fields[1].replaceAll("_", " ").toLowerCase().split(" ");
				nameFields[0] = null;
				for (String n : nameFields) {
					if (n == null) {
						continue;
					}
					if (n.equals("pos")) {
						n = "position";
					}
					friendlyName += n.substring(0, 1).toUpperCase() + n.substring(1, n.length()) + " ";
				}
				if (fields.length >= 3) {
					String s = "INSERT INTO `payload_statics_fields` (`product_id`, `variable_name`, `friendly_name`, `type`, `input`, `values`, `default_value`) VALUES ('" + PRODUCT_ID + "', '" + fields[1] + "', '" + friendlyName.trim() + "', ";
					
					if (fields[0].equals("int")) {
						s += "'integer', 'text', null,";
					} else if (fields[0].equals("String")) {
						s += "'string', 'text', null, ";
					} else if (fields[0].equals("boolean")) {
						s += "'string', 'select', 'false,true', ";
					}
					
					if (fields[2].equals("null") || fields[2].replaceAll("\"", "").trim().length() == 0) {
						s += "null);";
					} else {
						s += "'" + fields[2].replaceAll("\"", "") + "');";
					}
					out.println(s);
				}
			}
			out.flush();
		    out.close();
			in.close();
			System.out.println("Finished Processing");
		}
		catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
}
