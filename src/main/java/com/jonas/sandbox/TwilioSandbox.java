package com.jonas.sandbox;

import com.twilio.sdk.LookupsClient;
import com.twilio.sdk.TwilioRestClient;
import com.twilio.sdk.TwilioRestException;
import com.twilio.sdk.resource.factory.MessageFactory;
import com.twilio.sdk.resource.instance.Message;
import com.twilio.sdk.resource.instance.lookups.PhoneNumber;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("unused")
public class TwilioSandbox {
	// Find your Account Sid and Token at twilio.com/user/account
	public static final String ACCOUNT_SID = "ACd79e55d93b9e5b451bbd18cda84ebd9c";
	public static final String AUTH_TOKEN = "886e02a6eba0e31393b34f94b6bf3a99";
	
	public static void main(String[] args) throws TwilioRestException {
//		TwilioRestClient client = new TwilioRestClient(ACCOUNT_SID, AUTH_TOKEN);
//		// Build a filter for the MessageList
//		List<NameValuePair> params = new ArrayList<NameValuePair>();
//		params.add(new BasicNameValuePair("Body", "Sent programmatically from Jonas"));
//		params.add(new BasicNameValuePair("To", "+18486676800"));
//		params.add(new BasicNameValuePair("From", "+17323557639"));
//		MessageFactory messageFactory = client.getAccount().getMessageFactory();
//		Message message = messageFactory.create(params);
//		System.out.println(message.getSid());
//		for (int i = 0; i < 20; i++) {
//			int randomNum = (int)(Math.random() * 5);
//			System.out.println(randomNum);
//			System.out.println((int)(Math.random() * 3));
//			System.out.println("Random with range 0, 2: " + randomWithRange(0, 2));
			
//		}
	    LookupsClient client = new LookupsClient(ACCOUNT_SID, AUTH_TOKEN);
	    
	    PhoneNumber number = client.getPhoneNumber("7322221864", "US", true);
		
	    System.out.println(number.getType());
	    number = client.getPhoneNumber("7322411023", "US", true);
	    System.out.println(number.getType());
	    number = client.getPhoneNumber("7326649601", "US", true);
	    System.out.println(number.getType());
	    number = client.getPhoneNumber("8486676800", "US", true);
	    System.out.println(number.getType());
	}
	
	public static int randomWithRange(int min, int max) {
	   int range = (max - min) + 1;     
	   return (int)(Math.random() * range) + min;
	}
}