package com.jonas.icims;

import org.joda.time.DateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.joda.JodaModule;

@JsonInclude(Include.NON_EMPTY)
public class JodaTimePojo {
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd@HH:mm:ss.SSSZ", timezone = "America/New_York")
	private DateTime timeStamp;

	public String toAccessLogJsonString() throws JsonProcessingException {
		return new ObjectMapper().registerModule(new JodaModule())
				.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS).writeValueAsString(this);
	}

	public void setTimeStamp(DateTime timeStamp) {
		this.timeStamp = timeStamp;
	}
}
