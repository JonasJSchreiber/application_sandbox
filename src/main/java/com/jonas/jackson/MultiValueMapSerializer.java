package com.jonas.jackson;

import java.io.IOException;
import java.io.StringWriter;
import java.util.Map.Entry;

import org.apache.commons.collections4.map.MultiValueMap;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;

public class MultiValueMapSerializer extends JsonSerializer<MultiValueMap<String, Object>> {

		private ObjectMapper mapper = new ObjectMapper();

		@Override
		public void serialize(MultiValueMap<String, Object> value,
				JsonGenerator gen,
				SerializerProvider serializers)
				throws IOException, JsonProcessingException {

			StringWriter writer = new StringWriter();
		for (Entry<String, Object> e : value.entrySet()) {
			writer.append('"').append(e.getKey()).append('"').append(':');
			if (e.getValue() != null && e.getValue() instanceof String[]) {
				writer.append('"');
				for (String s : (String[]) e.getValue()) {
					writer.append(s);
				}
				writer.append('"');
			} else {
				writer.append(new ObjectMapper().writeValueAsString(e.getValue()));
			}
		}
			mapper.writeValue(writer, value);
			gen.writeFieldName(writer.toString());
		}
	}