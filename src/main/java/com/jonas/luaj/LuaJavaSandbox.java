package com.jonas.luaj;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.apache.commons.collections4.map.MultiValueMap;
import org.junit.Test;
import org.luaj.vm2.LuaValue;

@SuppressWarnings("restriction")
public class LuaJavaSandbox {

	@Test
	public void testLuaSimple() throws Exception {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine e = mgr.getEngineByName("luaj");
		e.eval("result = \"spanish\" == \"Spanish\"");
		Boolean b = (e.get("result") == LuaValue.TRUE);
		System.out.println(b);
	}

	@Test
	public void testLua() throws Exception {
		ScriptEngineManager mgr = new ScriptEngineManager();
		ScriptEngine e = mgr.getEngineByName("luaj");
		e.eval("result = \"string\" == \"string\" and (\"strng\" == \"Sring\" or \"a\" == \"N\" or true)");
		Boolean b = (e.get("result") == LuaValue.TRUE);
		System.out.println(b);

		e.eval("result = os.date(\"%A\") == \"Friday\"");
		System.out.println(e.get("result"));

		e.eval("result = string.find(\"Helllllloooo\", \".*asdf.*\") == nil");
		System.out.println(e.get("result"));

		e.eval("result = 31 >= 32");
		System.out.println(e.get("result"));

		e.eval("result = \"SCOTT C BRANDON MD\" ~= \"RONEL R ENRIQUE MD\" and \"SCOTT C BRANDON MD\" ~= \"NEIL M KASSMAN MD\" and \"SCOTT C BRANDON MD\" ~= \"RESEARCH INSTITUTE\"");
		System.out.println(e.get("result"));

		String token = "server(fiona): Giving up after 10 retries. Error: Could not find msg_queue for this attempt Attempt: {\"msg_queue_id\":1,\"account_id\":\"700720\",\"product_id\":\"RemindMe\",\"destination_type_cd\":\"CALL\",\"destination\":\"17327421006\",\"msg_content\":\"\nSYSTEM:(person detected)\nSYSTEM:700720_1_G.wav The appointment is for ADRIANA  on  Thu August 3rd  at  11 15 a.m. To confirm the appointment, press 1.  If you need to reschedule, press 2.\nCALLER:1\nSYSTEM:Thanks, and we look forward to seeing you.  Goodbye!\nSYSTEM:(hang up)\",\"call_recording_file\":\"d:\\talksoft\\bin\\..\\trace\\20170803\\1_1.wav\",\"channel_id\":23,\"start_datetime\":\"2017-08-03 0:40:58\",\"end_datetime\":\"2017-08-03 0:41:15\",\"attempt_status_cd\":2,\"server\":\"VECTOR\",\"reminder_status_cd\":3,\"confirm_code\":1,\"call_status\":2,\"Transferred_call\":null,\"appointments\":[{\"appt_id\":1,\"call_num\":1}]}";
		String tokenSafe = makeBeanShellSafe(token);
		try {
			System.out.println("result = 2391 == 223 and \"" + tokenSafe + "\" == \"hello\"");
			e.eval("result = 2391 == 223 and \"" + tokenSafe + "\" == \"hello\"");
			System.out.println(e.get("result"));
		} catch (Exception e1) {
			e1.printStackTrace();
		}

		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("EEEEE");
		String dayOfWeek = dateFormat.format(date);
		System.out.println(dayOfWeek);
	}

	private String makeBeanShellSafe(String query) {
		return query.replaceAll("\\r?\\n", " ").replace("\\", "").replace("\"", "'");
	}

	@Test
	public void test() {
		MultiValueMap<String, String> actions = new MultiValueMap<String, String>();
		actions.put("FILTER", "WEEE");
		actions.put("FILTER", "INCLUDE");
		boolean actual = collectionContains(actions.getCollection("FILTER"), "include");
		assertEquals(true, actual);
		actions.put("FILTER", "EXCLUDE");
		actual = collectionContains(actions.getCollection("FILTER"), "include")
				&& !collectionContains(actions.getCollection("FILTER"), "exclude");
		assertEquals(false, actual);
	}

	private boolean collectionContains(Collection<String> c, String itemName) {
		boolean contains = false;
		for (String item : c) {
			if (item.equalsIgnoreCase(itemName)) {
				contains = true;
				break;
			}
		}
		return contains;
	}
}
