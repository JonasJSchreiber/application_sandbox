package com.jonas.stockfighter.test;

import static org.junit.Assert.fail;

import java.io.BufferedReader;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.FileReader;
//import java.io.IOException;
//import java.io.InputStreamReader;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jonas.stockfighter.RestRequest;

public class RestRequestTester {
	
	RestRequest restRequest;
	public static final String BASE_URL = "https://api.stockfighter.io/ob/api";
	public static final String API_HEARTBEAT = "/heartbeat";
	public static final String VENUES_HEARTBEAT = "/venues/{venue}/heartbeat";
	public static final String GET_STOCKS = "/venues/{venue}/stocks";
	public static final String GET_ORDERBOOKS = "/venues/{venue}/stocks/{stock}";
	public static final String VENUE = "UNYEX";
	public static final String STOCK = "BEL";
	@Before
	public void setup() {
		restRequest = new RestRequest();
	}
	
	@After 
	public void teardown() {
		
	}
	
	@Test
	public void testHeartbeat() {
		try {
			
			Map<String, Object> map = restRequest.get(BASE_URL + API_HEARTBEAT);
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} 
	}

	@Test
	public void testVenueHeartbeat() {
		try {
			Map<String, Object> map = restRequest.get(BASE_URL + VENUES_HEARTBEAT.replace("{venue}", VENUE));
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} 
	}
	
	@Test
	public void testGetStocks() {
		try {
			Map<String, Object> map = restRequest.get(BASE_URL + GET_STOCKS.replace("{venue}", VENUE));
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} 
	}
	
	@Test
	public void testGetOrderbooks() {
		try {
			Map<String, Object> map = restRequest.get(BASE_URL + GET_ORDERBOOKS.replace("{venue}", VENUE).replace("{stock}", STOCK));
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				System.out.println(entry.getKey() + ": " + entry.getValue().toString());
			}
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} 
	}
	
	@Test
	public void testPost() {
		try {
			String request = "";
			BufferedReader in = new BufferedReader(new FileReader("c:\\testdata.xml"));
			String s;
			while ((s = in.readLine())!=null) {
				request += s;
			}
			String response = restRequest.post(request, "https://portal.talksoftonline.com/index.php/api/uploader");
			System.out.println(response);
			in.close();
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		} 
	}

}
