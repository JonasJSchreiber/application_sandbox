package com.jonas.gson;

import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GsonTester {
	private static final String json = "[{\"getappointmentconfirmationresultsinfo\":[{\"Appt_Confirmation_Result_ID\":\"4\",\"Long_Description\":\"phone number disconnected or not in service\",\"Short_Description\":\"Out of Order\"},{\"Appt_Confirmation_Result_ID\":\"5\",\"Long_Description\":\"A person answered, but hung up the phone\",\"Short_Description\":\"Answered - Hung Up\"},{\"Appt_Confirmation_Result_ID\":\"8\",\"Long_Description\":\"left message on answering machine or voicemail\",\"Short_Description\":\"Answering Machine\"},{\"Appt_Confirmation_Result_ID\":\"9\",\"Long_Description\":\"mailbox full or not set up\",\"Short_Description\":\"Attempted Message\"},{\"Appt_Confirmation_Result_ID\":\"10\",\"Long_Description\":\"phone number does not accept incoming calls\",\"Short_Description\":\"No Incoming Calls\"},{\"Appt_Confirmation_Result_ID\":\"11\",\"Long_Description\":\"3 attempts made, phone number busy\",\"Short_Description\":\"Phone Too Busy\"},{\"Appt_Confirmation_Result_ID\":\"12\",\"Long_Description\":\"person answered stated wrong number\",\"Short_Description\":\"Person Answered- Wrong Number\"},{\"Appt_Confirmation_Result_ID\":\"13\",\"Long_Description\":\"Patient confirmed appoinmtment\",\"Short_Description\":\"Confirmed-Yes\"},{\"Appt_Confirmation_Result_ID\":\"16\",\"Long_Description\":\"no answer, no message option\",\"Short_Description\":\"No Answer\"},{\"Appt_Confirmation_Result_ID\":\"17\",\"Long_Description\":\"Telephone Answered left message with person confirming appointment.\",\"Short_Description\":\"Answered -Left Message Confirming\"},{\"Appt_Confirmation_Result_ID\":\"18\",\"Long_Description\":\"called phone number, does not speak english\",\"Short_Description\":\"Called- language Barrier\"}]}]";

	@Test
	public void test() {
		final GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(GetAppointmentConfirmationResultsInfo.class,
				new GetAppointmentConfirmationResultsInfoDeserializer());
		final Gson gson = gsonBuilder.create();
		final GetAppointmentConfirmationResultsResponse r = gson.fromJson(json,
				GetAppointmentConfirmationResultsResponse[].class)[0];
		for (GetAppointmentConfirmationResultsInfo g : r
				.getGetappointmentconfirmationresultsinfo()) {
			System.out.println(g.getShortDescription());
		}

	}
}
