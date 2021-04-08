package com.jonas.sandbox;
import java.io.*;
public class DeidentifyOutreach {
	
	public static void main(String args[])
	{
		
		try
		{
			String filename = "C:\\1.csv";
			FileInputStream fstream = new FileInputStream(filename);
			File output = new File("C:\\Output.csv");
			FileOutputStream ostream = new FileOutputStream(output);
			PrintWriter out = new PrintWriter(ostream);
			// Get the object of DataInputStream
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null) 	
			{
				String[] fields = strLine.split("\",\"");
				if (fields.length >= 7) {
					fields[0] = "LASTNAME" + Integer.toString(randomWithRange(100, 999));
					if (fields[2].length() == 10) {
						fields[2] = "2" + Integer.toString(randomWithRange(100000000, 999999999));
					}
					if (fields[3].length() == 10) {
						fields[3] = "570" + Integer.toString(randomWithRange(3000000, 9999999));
					}
					fields[5] = Integer.toString(randomWithRange(343, 347));
					fields[13] = "TEST PROVIDER " + Integer.toString(randomWithRange(1, 10));
					fields[14] = "TEST LOCATION " + Integer.toString(randomWithRange(1, 10));
					String line = "\"";
					for (String s : fields) {
						line += s + "\",\"";
					}
					line = line.substring(0, line.length() - 3);
					out.println(line);
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
	
	static int randomWithRange(int min, int max) {
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
}
