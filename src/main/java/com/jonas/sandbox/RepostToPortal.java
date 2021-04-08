package com.jonas.sandbox;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RepostToPortal {
	
	public static void main(String args[])
	{
		
		try
		{
			String filename = "C:\\1.csv";
			FileInputStream fstream = new FileInputStream(filename);
			
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String strLine;
			//Read File Line By Line
			while ((strLine = br.readLine()) != null) {
				String request = getRequest(strLine);
				String response = sendAndReceive(request);
				System.out.println(response);
			}
			in.close();
			System.out.println("Finished Processing");
		}
		catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
	}
	
	private static String getRequest(String line) {
		String request = "ToCountry=US&ToState=NJ&SmsMessageSid=";
		String[] fields = line.split(",(?=(?:[^\"]*\"[^\"]*\")*(?![^\"]*\"))");
		if (fields.length >= 10) {
			request += fields[9];
			request += "&NumMedia=0&ToCity=TRENTON&FromZip=08852&SmsSid=";
			request += fields[9];
			request += "&FromState=NJ&SmsStatus=received&FromCity=MONMOUTH+JUNCTION&Body=";
			request += fields[2];
			request += "&FromCountry=US&To=%2B1";
			request += fields[1];
			request += "&ToZip=08611&MessageSid=";
			request += fields[9];
			request += "&AccountSid=AC952bde59154bb122b423940011177405&From=%2B1";
			request += fields[0];
			request += "&ApiVersion=2010-04-01";
		}
		return request;
	}
	
	private static String sendAndReceive(String request) throws Exception {
		String type = "application/x-www-form-urlencoded";
		URL u = new URL("http://portal.talksoftonline.com/index.php/report/smsreceived_twilio");
		HttpURLConnection conn = (HttpURLConnection) u.openConnection();
		conn.setDoOutput(true);
		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-Type", type);
		conn.setRequestProperty("Content-Length", String.valueOf(request.length()));
		OutputStream os = conn.getOutputStream();
		os.write(request.getBytes());
		InputStream is = conn.getInputStream();
		return is.toString();
	}
}
