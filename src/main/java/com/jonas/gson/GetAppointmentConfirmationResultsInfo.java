package com.jonas.gson;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({"Appt_Confirmation_Result_ID", "Long_Description", "Short_Description"})
public class GetAppointmentConfirmationResultsInfo {

	@JsonProperty("Appt_Confirmation_Result_ID")
	private String apptConfirmationResultID;
	@JsonProperty("Long_Description")
	private String longDescription;
	@JsonProperty("Short_Description")
	private String shortDescription;

	@JsonProperty("Appt_Confirmation_Result_ID")
	public String getApptConfirmationResultID() {
		return apptConfirmationResultID;
	}

	@JsonProperty("Appt_Confirmation_Result_ID")
	public void setApptConfirmationResultID(String apptConfirmationResultID) {
		this.apptConfirmationResultID = apptConfirmationResultID;
	}

	@JsonProperty("Long_Description")
	public String getLongDescription() {
		return longDescription;
	}

	@JsonProperty("Long_Description")
	public void setLongDescription(String longDescription) {
		this.longDescription = longDescription;
	}

	@JsonProperty("Short_Description")
	public String getShortDescription() {
		return shortDescription;
	}

	@JsonProperty("Short_Description")
	public void setShortDescription(String shortDescription) {
		this.shortDescription = shortDescription;
	}

}