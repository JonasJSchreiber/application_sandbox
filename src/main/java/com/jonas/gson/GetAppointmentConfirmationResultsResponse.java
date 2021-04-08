package com.jonas.gson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"getappointmentconfirmationresultsinfo"})
public class GetAppointmentConfirmationResultsResponse {

	@JsonProperty("getappointmentconfirmationresultsinfo")
	private GetAppointmentConfirmationResultsInfo[] getappointmentconfirmationresultsinfo = null;

	@JsonProperty("getappointmentconfirmationresultsinfo")
	public GetAppointmentConfirmationResultsInfo[] getGetappointmentconfirmationresultsinfo() {
		return getappointmentconfirmationresultsinfo;
	}

	@JsonProperty("getappointmentconfirmationresultsinfo")
	public void setGetappointmentconfirmationresultsinfo(
			GetAppointmentConfirmationResultsInfo[] getappointmentconfirmationresultsinfo) {
		this.getappointmentconfirmationresultsinfo = getappointmentconfirmationresultsinfo;
	}
}