package com.jonas.sandbox;
import java.io.*;
public class ParseQuotes {
	
	public static void main(String args[])
	{
		
		try
		{
			String filename = "C:\\1.csv";
			FileInputStream fstream = new FileInputStream(filename);
			File output = new File("C:\\Output.txt");
			FileOutputStream ostream = new FileOutputStream(output);
			PrintWriter out = new PrintWriter(ostream);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			out.println(br.readLine());
			//Read File Line By Line
			while ((strLine = br.readLine()) != null) 	
			{
				String[] fields = strLine.split("\",\"");
				if (fields.length >= 7) {
					out.print(fields[0] + "\",\"" + fields[1] + "\",\"" + fields[2] + "\",\"" + fields[4] + "\",\"");
					out.print(transformAttemptStatus(fields[5]) +  "\",\"" + fields[6] + "\",\"" + fields[7]);
					out.println();
				} else {
					out.println(strLine);
				}
			}
			out.flush();
		    out.close();
			in.close();
			System.out.println("Finished Processing");
		}
		catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	public static String transformAttemptStatus(String statusCode) {
		String result = "";
		if (!statusCode.equals("")) {
			int sc = Integer.parseInt(statusCode);
			if (sc == 0 || sc == 1 || (sc >= 4 && sc <= 9) || sc == 11 || sc == 13 || sc == 16) {
				result = "failed";
			} else if (sc == 10 || sc == 12 || sc == 14 || sc == 15 || sc == 17 || sc == 2 || sc == 3) {
				result = "success";
			}
		}
		return result;
	}
}
