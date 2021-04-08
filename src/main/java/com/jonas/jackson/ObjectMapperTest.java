package com.jonas.jackson;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ObjectMapperTest {

	private MultiValueMap<String, Object> mvm;

	private Integer id;
	public Map<String, Object> getMvm() {
		return this.mvm.entrySet().stream()
				.collect(Collectors.toMap(e -> String.valueOf(e.getKey()),
						e -> getString(e.getValue())));
	}

	@SuppressWarnings("unchecked")
	public String getString(Object o) {
		return o instanceof ArrayList
				? ((List<String>) o).stream().collect(Collectors.joining(""))
				: String.valueOf(o);

	}

	@SuppressWarnings("unchecked")
	public Map<Object, Object> getMvm1() {
		return this.mvm.entrySet().stream()
				.collect(Collectors.toMap(e -> String.valueOf(e.getKey()),
						e -> e.getValue() instanceof ArrayList
								? ((List<String>) e.getValue()).stream()
										.collect(Collectors.joining(""))
								: String.valueOf(e.getValue())));
	}

	public boolean check1(Object o) {
		return o instanceof ArrayList;
	}

	public void setMvm(MultiValueMap<String, Object> mvm) {
		this.mvm = mvm;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String toJson() {
		try {
			return new ObjectMapper().writeValueAsString(this);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			return this.toString();
		}
	}


	@Test
	public void test() {
		this.mvm = new MultiValueMap<String, Object>();
		this.mvm.put("key", "v1");
		this.id = 1234;
		System.out.println(this.toJson());
		assertTrue(true);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ObjectMapperTest [mvm=");
		builder.append(mvm);
		builder.append(", id=");
		builder.append(id);
		builder.append("]");
		return builder.toString();
	}

}
