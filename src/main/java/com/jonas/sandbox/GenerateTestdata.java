package com.jonas.sandbox;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.math3.random.RandomData;
import org.apache.commons.math3.random.RandomDataImpl;

@SuppressWarnings("deprecation")
public class GenerateTestdata {
	
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
				if (fields.length >= 12) {
					Calendar cal = Calendar.getInstance();
					cal.set(Calendar.MONTH, cal.get(Calendar.MONTH)+3);
					Date endDate = cal.getTime();
					Date d = nextDate(new Date(), endDate);
					cal.setTime(d);
					cal.set(Calendar.MINUTE, roundMinute(cal.get(Calendar.MINUTE)));
					if (cal.get(Calendar.HOUR_OF_DAY) > 18) {
						cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) - 10);
					} else if (cal.get(Calendar.HOUR_OF_DAY) < 6) {
						cal.set(Calendar.HOUR_OF_DAY, cal.get(Calendar.HOUR_OF_DAY) + 10);
					}  
					DateFormat df = new SimpleDateFormat("MM/dd/yy HH:mm");
					d = cal.getTime();
					String reportDate = df.format(d);
					String[] dateFields = reportDate.split(" ");
					fields[3] = dateFields[0];
					fields[4] = dateFields[1];
					String line = "";
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
	

	public static Date nextDate(Date min, Date max) {

	      RandomData randomData = new RandomDataImpl();
	      return new Date(randomData.nextLong(min.getTime(), max.getTime()));
	}
	
	public static int roundMinute(int n) {
		return (n + 4) / 5 * 5;
	}
}
