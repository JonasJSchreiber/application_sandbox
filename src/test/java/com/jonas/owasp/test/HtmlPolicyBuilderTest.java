package com.jonas.owasp.test;

import java.util.List;

import org.junit.Test;
import org.owasp.html.AttributePolicy;
import org.owasp.html.ElementPolicy;
import org.owasp.html.Handler;
import org.owasp.html.HtmlPolicyBuilder;
import org.owasp.html.HtmlSanitizer;
import org.owasp.html.HtmlStreamRenderer;

import com.google.common.base.Joiner;
import com.jonas.owasp.MyHtmlSanitizer;

import junit.framework.TestCase;

public class HtmlPolicyBuilderTest extends TestCase {

	static final String EXAMPLE = Joiner.on('\n').join(
			"<h1 id='foo'>Header</h1>",
			"<p onclick='alert(42)'>Paragraph 1<script>evil()</script></p>",
			("<p><a href='java\0script:bad()'>Click</a> <a href='foo.html'>me</a>"
					+ " <a href='http://outside.org/'>out</a></p>"),
			("<p><img src=canary.png alt=local-canary>" +
					"<img src='http://canaries.org/canary.png'></p>"),
			"<p><b style=font-size:bigger>Fancy</b> with <i><b>soupy</i> tags</b>.",
			"<p style='color: expression(foo()); text-align: center;",
			"          /* direction: ltr */; font-weight: bold'>Stylish Para 1</p>",
			"<p style='color: red; font-weight; expression(foo());",
			"          direction: rtl; font-weight: bold'>Stylish Para 2</p>",
			"");
	/*
	 * @Test public static final void testTextFilter() { assertEquals( Joiner.on('\n').join(
	 * "Header", "Paragraph 1", "Click me out", "", "Fancy with soupy tags.", "Stylish Para 1",
	 * "Stylish Para 2", ""), apply(new HtmlPolicyBuilder())); }
	 */

	@Test
	public static final void testCannedFormattingTagFilter() {
		assertEquals(
				Joiner.on('\n').join(
						"Header",
						"Paragraph 1",
						"Click me out",
						"",
						"<b>Fancy</b> with <i><b>soupy</b></i><b> tags</b>.",
						"Stylish Para 1",
						"Stylish Para 2",
						""),
				apply(new HtmlPolicyBuilder()
						.allowCommonInlineFormattingElements()));
	}

	@Test
	public static final void testCannedFormattingTagFilterNoItalics() {
		assertEquals(
				Joiner.on('\n').join(
						"Header",
						"Paragraph 1",
						"Click me out",
						"",
						"<b>Fancy</b> with <b>soupy</b><b> tags</b>.",
						"Stylish Para 1",
						"Stylish Para 2",
						""),
				apply(new HtmlPolicyBuilder()
						.allowCommonInlineFormattingElements()
						.disallowElements("I")));
	}

	@Test
	public static final void testSimpleTagFilter() {
		assertEquals(
				Joiner.on('\n').join(
						"<h1>Header</h1>",
						"Paragraph 1",
						"Click me out",
						"",
						"Fancy with <i>soupy</i> tags.",
						"Stylish Para 1",
						"Stylish Para 2",
						""),
				apply(new HtmlPolicyBuilder()
						.allowElements("h1", "i")));
	}

	@Test
	public static final void testLinksAllowed() {
		assertEquals(
				Joiner.on('\n').join(
						"Header",
						"Paragraph 1",
						// We haven't allowed any protocols so only relative URLs are OK.
						"Click <a href=\"foo.html\">me</a> out",
						"",
						"Fancy with soupy tags.",
						"Stylish Para 1",
						"Stylish Para 2",
						""),
				apply(new HtmlPolicyBuilder()
						.allowElements("a")
						.allowAttributes("href").onElements("a")));
	}

	@Test
	public static final void testExternalLinksAllowed() {
		assertEquals(
				Joiner.on('\n').join(
						"Header",
						"Paragraph 1",
						"Click <a href=\"foo.html\">me</a>"
								+ " <a href=\"http://outside.org/\">out</a>",
						"",
						"Fancy with soupy tags.",
						"Stylish Para 1",
						"Stylish Para 2",
						""),
				apply(new HtmlPolicyBuilder()
						.allowElements("a")
						// Allows http.
						.allowStandardUrlProtocols()
						.allowAttributes("href").onElements("a")));
	}

	@Test
	public static final void testLinksWithNofollow() {
		assertEquals(
				Joiner.on('\n').join(
						"Header",
						"Paragraph 1",
						"Click <a href=\"foo.html\" rel=\"nofollow\">me</a> out",
						"",
						"Fancy with soupy tags.",
						"Stylish Para 1",
						"Stylish Para 2",
						""),
				apply(new HtmlPolicyBuilder()
						.allowElements("a")
						// Allows http.
						.allowAttributes("href").onElements("a")
						.requireRelNofollowOnLinks()));
	}

	@Test
	public static final void testImagesAllowed() {
		assertEquals(
				Joiner.on('\n').join(
						"Header",
						"Paragraph 1",
						"Click me out",
						"<img src=\"canary.png\" alt=\"local-canary\" />",
						// HTTP img not output because only HTTPS allowed.
						"Fancy with soupy tags.",
						"Stylish Para 1",
						"Stylish Para 2",
						""),
				apply(new HtmlPolicyBuilder()
						.allowElements("img")
						.allowAttributes("src", "alt").onElements("img")
						.allowUrlProtocols("https")));
	}

	@Test
	public static final void testStyleFiltering() {
		assertEquals(
				Joiner.on('\n').join(
						"<h1>Header</h1>",
						"<p>Paragraph 1</p>",
						"<p>Click me out</p>",
						"<p></p>",
						"<p><b>Fancy</b> with <i><b>soupy</b></i><b> tags</b>.",
						("</p><p style=\"text-align:center;font-weight:bold\">"
								+ "Stylish Para 1</p>"),
						("<p style=\"color:red;direction:rtl;font-weight:bold\">"
								+ "Stylish Para 2</p>"),
						""),
				apply(new HtmlPolicyBuilder()
						.allowCommonInlineFormattingElements()
						.allowCommonBlockElements()
						.allowStyling()
						.allowStandardUrlProtocols()));
	}

	@Test
	public static final void testElementTransforming() {
		assertEquals(
				Joiner.on('\n').join(
						"<div class=\"header-h1\">Header</div>",
						"<p>Paragraph 1</p>",
						"<p>Click me out</p>",
						"<p></p>",
						"<p>Fancy with soupy tags.",
						"</p><p>Stylish Para 1</p>",
						"<p>Stylish Para 2</p>",
						""),
				apply(new HtmlPolicyBuilder()
						.allowElements("h1", "p", "div")
						.allowElements(
								new ElementPolicy() {
									public String apply(
											String elementName, List<String> attrs) {
										attrs.add("class");
										attrs.add("header-" + elementName);
										return "div";
									}
								}, "h1")));
	}

	@Test
	public static final void testAllowUrlProtocols() {
		assertEquals(
				Joiner.on('\n').join(
						"Header",
						"Paragraph 1",
						"Click me out",
						"<img src=\"canary.png\" alt=\"local-canary\" />"
								+ "<img src=\"http://canaries.org/canary.png\" />",
						"Fancy with soupy tags.",
						"Stylish Para 1",
						"Stylish Para 2",
						""),
				apply(new HtmlPolicyBuilder()
						.allowElements("img")
						.allowAttributes("src", "alt").onElements("img")
						.allowUrlProtocols("http")));
	}

	@Test
	public static final void testPossibleFalloutFromIssue5() {
		assertEquals(
				"Bad",
				apply(
						new HtmlPolicyBuilder()
								.allowElements("a")
								.allowAttributes("href").onElements("a")
								.allowUrlProtocols("http"),

						"<a href='javascript:alert(1337)//:http'>Bad</a>"));
	}

	@Test
	public static final void testTextInOption() {
		assertEquals(
				"<select><option>1</option><option>2</option></select>",
				apply(
						new HtmlPolicyBuilder()
								.allowElements("select", "option"),

						"<select>\n  <option>1</option>\n  <option>2</option>\n</select>"));
	}

	@Test
	public static final void testEntities() {
		assertEquals(
				"(Foo)\u00a0(Bar)\u2666\u2666\u2666\u2666(Baz)"
						+ "&#x14834;&#x14834;&#x14834;(Boo)",
				apply(
						new HtmlPolicyBuilder(),
						"(Foo)&nbsp;(Bar)&diams;&#9830;&#x2666;&#X2666;(Baz)"
								+ "\ud812\udc34&#x14834;&#x014834;(Boo)"));
	}

	@Test
	public static final void testDuplicateAttributesDoNotReachElementPolicy() {
		final int[] idCount = new int[1];
		assertEquals(
				// The id that is emitted is the first that passes the attribute
				// starts-with-b filter.
				// The attribute policy sees 3 id elements, hence id-count=3.
				// The element policy sees 2 attributes, one "id" and one "href",
				// hence attr-count=2.
				"<a href=\"foo\" id=\"bar\" href=\"baz\" id=\"boo\" attr-count=\"4\" id-count=\"3\">link</a>",
				apply(
						new HtmlPolicyBuilder()
								.allowElements(
										new ElementPolicy() {
											public String apply(String elementName,
													List<String> attrs) {
												int nAttrs = attrs.size() / 2;
												attrs.add("attr-count");
												attrs.add("" + nAttrs);
												attrs.add("id-count");
												attrs.add("" + idCount[0]);
												return elementName;
											}
										},
										"a")
								.allowAttributes("id").matching(new AttributePolicy() {
									public String apply(
											String elementName, String attributeName,
											String value) {
										++idCount[0];
										return value.startsWith("b") ? value : null;
									}
								}).onElements("a")
								.allowAttributes("href").onElements("a"),
						"<a href=\"foo\" id='far' id=\"bar\" href=baz id=boo>link</a>"));
	}

	@Test
	public static final void testTextFilter() {
		System.out.println(new MyHtmlSanitizer().sanitize(EXAMPLE));
	}

	private static String apply(HtmlPolicyBuilder b) {
		return apply(b, EXAMPLE);
	}

	private static String apply(HtmlPolicyBuilder b, String src) {
		StringBuilder sb = new StringBuilder();
		HtmlSanitizer.Policy policy = b.build(HtmlStreamRenderer.create(sb,
				new Handler<String>() {
					public void handle(String x) {
						fail(x);
					}
				}));
		HtmlSanitizer.sanitize(src, policy);
		return sb.toString();
	}
}
