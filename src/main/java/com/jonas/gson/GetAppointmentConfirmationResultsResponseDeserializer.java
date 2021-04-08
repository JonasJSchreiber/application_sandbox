package com.jonas.gson;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

public class GetAppointmentConfirmationResultsResponseDeserializer
		implements
			JsonDeserializer<GetAppointmentConfirmationResultsResponse> {

	@Override
	public GetAppointmentConfirmationResultsResponse deserialize(final JsonElement json,
			final Type typeOfT, final JsonDeserializationContext context)
			throws JsonParseException {
		final JsonObject jsonObject = json.getAsJsonObject();

		// Delegate the deserialization to the context
		GetAppointmentConfirmationResultsInfo[] getAppointmentConfirmationResultsInfo = context
				.deserialize(jsonObject.get("getappointmentconfirmationresultsinfo"),
						GetAppointmentConfirmationResultsInfo[].class);

		final GetAppointmentConfirmationResultsResponse response = new GetAppointmentConfirmationResultsResponse();
		response.setGetappointmentconfirmationresultsinfo(getAppointmentConfirmationResultsInfo);
		return response;
	}
}
