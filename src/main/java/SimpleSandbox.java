import java.io.File;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.stream.IntStream;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class SimpleSandbox {

	@Test
	public void fileTest() {
		String s = "/opt/talksoft/remote/xserver/archive/payload/20171019/720059-4005652020425913633.txt";
		if (!StringUtils.isEmpty(s) && s.contains("payload")) {
			int ptr = s.indexOf("payload") + "payload".length();
			s = s.substring(ptr, s.length());
			System.out.println(s);
		}
		File file = new File(
				"/opt/talksoft/remote/xserver/archive/payload/20171019/720059-4005652020425913633.txt");

		System.out.println(file.getName());
	}
	@Test
	public void test() {
		IntStream.range(1, 6).forEach(i -> System.out
				.println("Seconds to sleep: " + (int) Math.pow((double) (i + 1), 3.0)));
	}

	@Test
	public void test2() {
		String s = "hello@asdf";
		if (s.matches(".*\\@.*")) {
			System.out.println("matches");
		}
	}

	@Test
	public void test3() {
		String s = "blah Details: ";
		String out = s.substring(s.indexOf("Details: ") + "Details: ".length(), s.length()).trim()
				.replace("\"", "").replace(".", "");
		System.out.println(out);
		System.out.println(out.length());
	}

	@Test
	public void testGetMd5() {
		String x = "9ce26605e312e56828c3b0f28d52c1a8";
		for (int i = 100000; i < 1000000; i++) {
			try {
				String md5 = getMD5(Integer.toString(i));
				if (md5.equals(x)) {
					System.out.println(i);
					break;
				}
			} catch (NoSuchAlgorithmException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public String getMD5(String s) throws NoSuchAlgorithmException {
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(StandardCharsets.UTF_8.encode(s));
		return String.format("%032x", new BigInteger(1, md5.digest()));
	}

	@Test
	public void test4() {
		if (new File("c:/tmp").exists()) {
			System.out.println("already exists");
		}
	}

	@Test
	public void testIntRange() {
		IntStream.range(0, 10).forEach(System.out::println);
	}
}
