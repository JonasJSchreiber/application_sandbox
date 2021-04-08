package com.jonas.sandbox.test;

//import java.io.BufferedInputStream;
import java.io.BufferedReader;
//import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
import java.io.IOException;
//import java.io.InputStream;
import java.io.InputStreamReader;

//import org.apache.commons.io.ByteOrderMark;
//import org.apache.commons.io.input.BOMInputStream;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RegexTest {

	@Before
	public void setup() {
		
	}
	
	@After 
	public void teardown() {
		
	}
	
	@Test
	public void testRegexCompare() {
		if ("hello \r\n \r\n {ALERT_MESSAGE}".matches(".*\\{[^\\s]*\\}.*")) {
			System.out.println("true");
		}
		String content = "At least 100 errors have occurred with Twilio trying to post results back to us today. Please look into this. \r\n\r\n {ALERT_MESSAGE}";
		if (content.replaceAll("[\\r\\n]", " ").matches(".*\\{[^\\s]*\\}.*")) {
			System.out.println("true");
		}
	}
	
	@Test
	public void gimmeabreak() {

		String filename = "C:\\2.txt";
//		String defaultEncoding = "UTF-8";
		
		try {
			InputStreamReader isr = new InputStreamReader(new FileInputStream(filename), "UTF-8"); 
			BufferedReader in = new BufferedReader(isr);
			String strLine;
				
			while ((strLine = in.readLine()) != null) {
				String[] fields = strLine.split("[‚]");
				int i = 0;
				System.out.println("Fields Length is: " + fields.length);
				for (String s : fields) {
					System.out.println(i + ": " + s);
					i++;			
				}
			}
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
