package com.jonas.subtitles;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class Conversion {

	public static int convert(File file) {

		try {
			// Open the file that is the first
			// command line parameter
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			@SuppressWarnings("static-access")
			String outputFile = ("C:" + file.separator + "Users" + file.separator + "JJS-GAMING"
					+ file.separator + "Desktop" + File.separator + file.getName());

			if (file.getAbsolutePath().equals(outputFile))
				outputFile = file.getAbsolutePath().substring(0,
						file.getAbsolutePath().length() - 4) + " (Processed).srt";

			File output = new File(outputFile);
			FileOutputStream ostream = new FileOutputStream(output);
			PrintWriter out = new PrintWriter(ostream);

			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String[] sub = new String[5];
			String strLine;
			int i = 0;
			int currentsub = 1;
			int openindex;
			int closeindex;

			// Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				i = 0;
				while (strLine != null && strLine.length() > 0) {
					if (strLine.contains("(") || strLine.contains(")") || strLine.contains("{")
							|| strLine.contains("[") || strLine.contains("]")
							|| strLine.equals("<i")) {
						openindex = 0;
						closeindex = strLine.length();
						if (strLine.contains("("))
							openindex = strLine.indexOf('(');
						else if (strLine.contains("{"))
							openindex = strLine.indexOf('{');
						else if (strLine.contains("["))
							openindex = strLine.indexOf('[');
						if (strLine.contains(")"))
							closeindex = strLine.indexOf(')');
						else if (strLine.contains("}"))
							closeindex = strLine.indexOf('}');
						else if (strLine.contains("]"))
							closeindex = strLine.indexOf(']');
						if (openindex <= 2 && closeindex >= strLine.length() - 1)
							strLine = "";
						else if (closeindex - openindex == 2)
							strLine = strLine.substring(0, openindex - 1)
									+ strLine.charAt(openindex + 1);
						else if (openindex <= 2 && closeindex < strLine.length() - 1) {
							strLine = strLine.substring(closeindex, strLine.length());
							while (strLine.charAt(0) == ' ' || strLine.charAt(0) == ']'
									|| strLine.charAt(0) == '}' || strLine.charAt(0) == ')')
								strLine = new String(strLine.substring(1, strLine.length()));
						} else if (openindex > 2)
							strLine = strLine.substring(0, openindex - 1);
					}
					if (strLine.length() > 0) {
						sub[i] = new String(strLine);
						i += 1;
					}
					if ((strLine = br.readLine()) == null)
						break;
				}
				if (sub[2] != null) {
					out.println(currentsub);
					for (i = 1; i < sub.length; i++) {
						if (sub[i] != null)
							out.println(sub[i]);
					}
					currentsub += 1;
					out.println("");
				}
				sub = null;
				sub = new String[5];
			}
			out.flush();
			out.close();
			in.close();
			return 0;
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
			return 1;
		}

	}
}
