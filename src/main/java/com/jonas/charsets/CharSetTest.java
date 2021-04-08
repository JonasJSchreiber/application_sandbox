package com.jonas.charsets;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import org.junit.Test;

public class CharSetTest {

	@Test
	public void test() {
		System.out.println("Default Charset=" + Charset.defaultCharset());
		System.setProperty("file.encoding", "Latin-1");
		System.out.println("file.encoding=" + System.getProperty("file.encoding"));
		System.out.println("Default Charset=" + Charset.defaultCharset());
		System.out.println("Default Charset in Use=" + getDefaultCharSet());
	}

	private static String getDefaultCharSet() {
		OutputStreamWriter writer = new OutputStreamWriter(new ByteArrayOutputStream());
		String enc = writer.getEncoding();
		return enc;
	}
}