package com.jonas.stockfighter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class RestRequest {

	@SuppressWarnings("unchecked")
	public Map<String, Object> get(String getUrl)
			throws JsonParseException, JsonMappingException, IOException {
		Client client = ClientBuilder.newBuilder().build();
		WebTarget target = client.target(getUrl);
		Response response = target.request().get();
		String value = response.readEntity(String.class);
		response.close();
		return new ObjectMapper().readValue(value, HashMap.class);
	}
	
	public String post(String postContent, String postUrl) throws Exception {
		Client client = ClientBuilder.newBuilder().build();
		WebTarget target = client.target(postUrl);
		Response response =
				target.request(MediaType.TEXT_PLAIN_TYPE)
		                .post(Entity.entity("A string entity to be POSTed", MediaType.TEXT_PLAIN));
		String value = response.readEntity(String.class);
		response.close();
		return value;
	}
}
