package com.jonas.owasp;

import org.owasp.html.Handler;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.HtmlSanitizer;
import org.owasp.html.HtmlStreamRenderer;

public class MyHtmlSanitizer {

	public String sanitize(String src) {
	    StringBuilder sb = new StringBuilder();
	    HtmlSanitizer.Policy policy = new HtmlPolicyBuilder().build(HtmlStreamRenderer.create(sb,
	        new Handler<String>() {
	          public void handle(String x) { }
	        }));
	    HtmlSanitizer.sanitize(src, policy);
	    return sb.toString();
	}
}
