package com.jonas.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GetAppointmentConfirmationResultsInfoDeserializer
		implements
			JsonDeserializer<GetAppointmentConfirmationResultsInfo> {

	@Override
	public GetAppointmentConfirmationResultsInfo deserialize(final JsonElement json,
			final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		final GetAppointmentConfirmationResultsInfo g = new GetAppointmentConfirmationResultsInfo();
		g.setApptConfirmationResultID(jsonObject.get("Appt_Confirmation_Result_ID").getAsString());
		g.setShortDescription(jsonObject.get("Short_Description").getAsString());
		return g;
	}
}