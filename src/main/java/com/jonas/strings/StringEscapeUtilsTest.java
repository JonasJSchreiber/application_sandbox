package com.jonas.strings;

import static org.junit.Assert.assertEquals;

import org.apache.commons.lang3.StringEscapeUtils;
import org.junit.Test;

public class StringEscapeUtilsTest {

	@Test
	public void testStringEscapeUtils() {
		String expected = "FINEST|17452/0|Service tsoft_spring|17-12-07 16:36:06|2017-12-07 16:36:06.737  INFO 17452 --- [ taskExecutor-2] c.t.s.c.s.a.AttemptServiceTaskExecutor   : Resolving Confirm Code Given: UserInteraction [interactionId=51102866, mode=CALL, type=RESPONSE, direction=OUTGOING, status=PROCESSING, content=null, receivedDatetime=Thu Dec 07 16:36:06 EST 2017, completedDatetime=null, sourceAddr=, destAddr=, destServer=fiona, notes=SPRING Fielded ATTEMPT Post, autoReponseDatetime=null]";
		String actual = escapeCrlf(expected);
		assertEquals(expected, actual);
	}

	public static String escapeCrlf(String s) {
		return StringEscapeUtils.escapeJava(s);
	}

	@Test
	public void test2() {
		String toEscape = "FINEST|17452/0|Service tsoft_spring|17-12-07 16:36:06|2017-12-07 16:36:06.722  INFO 17452 --- [ taskExecutor-2] c.t.s.c.s.a.AttemptServiceTaskExecutor   : Attempt Service Invokation for Attempt: {\"msg_queue_id\":531396054,\"account_id\":\"703391\",\"product_id\":\"Outreach\",\"destination_type_cd\":\"CALL\",\"destination\":\"17165551212\",\"msg_content\":\"\\nSYSTEM:(person detected)\\nSYSTEM:703391_2_DMD.wav Austin and  Jameson 703391_443_DMF.wav\\nSYSTEM:(hang up)\",\"call_recording_file\":\"e:\\\\talksoft\\\\trace\\\\20171207\\\\531396054_1.wav\",\"channel_id\":20,\"start_datetime\":\"2017-12-07 16:35:12\",\"end_datetime\":\"2017-12-07 16:36:06\",\"attempt_status_cd\":2,\"server\":\"FIONA\",\"reminder_status_cd\":3,\"confirm_code\":null,\"call_status\":2,\"Transferred_call\":null,\"appointments\":[{\"appt_id\":946757810,\"call_num\":1},{\"appt_id\":946757838,\"call_num\":1}]}";
		String expected = "FINEST|17452/0|Service tsoft_spring|17-12-07 16:36:06|2017-12-07 16:36:06.722  INFO 17452 --- [ taskExecutor-2] c.t.s.c.s.a.AttemptServiceTaskExecutor   : Attempt Service Invokation for Attempt: {\\\"msg_queue_id\\\":531396054,\\\"account_id\\\":\\\"703391\\\",\\\"product_id\\\":\\\"Outreach\\\",\\\"destination_type_cd\\\":\\\"CALL\\\",\\\"destination\\\":\\\"17165551212\\\",\\\"msg_content\\\":\\\"\\\\nSYSTEM:(person detected)\\\\nSYSTEM:703391_2_DMD.wav Austin and  Jameson 703391_443_DMF.wav\\\\nSYSTEM:(hang up)\\\",\\\"call_recording_file\\\":\\\"e:\\\\\\\\talksoft\\\\\\\\trace\\\\\\\\20171207\\\\\\\\531396054_1.wav\\\",\\\"channel_id\\\":20,\\\"start_datetime\\\":\\\"2017-12-07 16:35:12\\\",\\\"end_datetime\\\":\\\"2017-12-07 16:36:06\\\",\\\"attempt_status_cd\\\":2,\\\"server\\\":\\\"FIONA\\\",\\\"reminder_status_cd\\\":3,\\\"confirm_code\\\":null,\\\"call_status\\\":2,\\\"Transferred_call\\\":null,\\\"appointments\\\":[{\\\"appt_id\\\":946757810,\\\"call_num\\\":1},{\\\"appt_id\\\":946757838,\\\"call_num\\\":1}]}";
		String actual = escapeCrlf(toEscape);
		assertEquals(expected, actual);
	}
}
