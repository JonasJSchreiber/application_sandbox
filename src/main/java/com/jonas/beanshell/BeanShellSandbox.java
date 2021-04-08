package com.jonas.beanshell;
import static org.junit.Assert.fail;

import bsh.EvalError;
import bsh.Interpreter;
import org.junit.Test;

public class BeanShellSandbox {

	@Test
	public void test() {
		Interpreter i = new Interpreter();
		String toEvaluate = " (\"Canceled by Staff-Meeting (9205)\".equals(\"General Time (410)\") || \"Canceled by Staff-Meeting (9205)\".equals(\"Non-billable Group Code (702)\") || \"Canceled by Staff-Meeting (9205)\".equals(\"Other Paid Leave (404)\") || \"Canceled by Staff-Meeting (9205)\".equals(\"Holiday (403)\") || \"Canceled by Staff-Meeting (9205)\".equals(\"Sick (402)\") || \"Canceled by Staff-Meeting (9205)\".equals(\"Vacation (401)\") || \"Canceled by Staff-Meeting (9205)\".equals(\"Travel (413)\") || \"Canceled by Staff-Meeting (9205)\".equals(\"Institution Code (407)\") || \"Canceled by Staff-Meeting (9205)\".equals(\"Supervision Giving and Receiving (391)\") || \"Canceled by Staff-Meeting (9205)\".equals(\"Supervision (391)\") || \"Canceled by Staff-Meeting (9205)\".equals(\"Training (390)\") || \"Canceled by Staff-Meeting (9205)\".equals(\"Training Given and Received (390)\") || \"Canceled by Staff-Meeting (9205)\".equals(\"Community Outreach (350)\") || \"Canceled by Staff-Meeting (9205)\".equals(\"Contact Attempt (344)\") || \"Canceled by Staff-Meeting (9205)\".equals(\"Non-Billable Service (701)\") || \"Canceled by Staff-Meeting (9205)\".equals(\"Administrative Meeting (411)\") || \"T.Point Sequoia Youth Services,TP Sequoia Youth Services\".matches(\".Point Visalia Youth Services,School\"))";
		try {
			Boolean result = (Boolean) i.eval(toEvaluate);
			System.out.println(result);
		} catch (EvalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toEvaluate = "Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY";
		try {
			Boolean result = (Boolean) i.eval(toEvaluate);
			System.out.println(result);
		} catch (EvalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toEvaluate = "string == string && (\"string\".equals(\"tring\"))";
		try {
			Boolean result = (Boolean) i.eval(toEvaluate);
			System.out.println(result);
		} catch (EvalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toEvaluate = "Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY";
		try {
			Boolean result = (Boolean) i.eval(toEvaluate);
			System.out.println(result);
		} catch (EvalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toEvaluate = "string == string && (\"string\".equals(\"tring\"))";
		try {
			Boolean result = (Boolean) i.eval(toEvaluate);
			System.out.println(result);
		} catch (EvalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		toEvaluate = "Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY";
		try {
			Boolean result = (Boolean) i.eval(toEvaluate);
			System.out.println(result);
		} catch (EvalError e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testBeanShell() {
		try {
			String input = "active_dc	destination_type_cd	status	contacts\r\nconshohocken	APP	Pending	10\r\nconshohocken	APP	Complete	10\r\nconshohocken	APP	Failed	15\r\nconshohocken	APP	Message Sent	18\r\nconshohocken	CALL	Pending	11359\r\nconshohocken	CALL	Processing	502\r\n";
			System.out.println(makeBeanShellSafe(input));
			Interpreter i = new Interpreter();
			String toEvaluate = "(\"" + makeBeanShellSafe(input)
					+ "\").matches(\".*Cannot parse data field.*continuing.*\")";
			Boolean result = (Boolean) i.eval(toEvaluate);
			System.out.println(result);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
	}

	@Test
	public void testMatches() {
		String regex = ".*\\s(INJ|FLU|N12}COL|EGD|FLEXS|OVREMB|OCPBON|ACTMR|OVBD|OVAREB|OVARED|OVBOBD|OVBONE|OVBONV|OVCYTX|OVOREN|OVREBO|OVRECL|OVREM|OVRITX|OVROPH|OVWBLY|OVWSOL|SS|MSLT|OVP|OVPFT|PSG|ECV|OVABI|INJ|FLU|N12|OVWABD|OVZOLB|OVZOLE|OVZOM|SIMP|SIMP2|SIMP3|SIMPQ8|ZOLE|ZOM|OVWSA|1R11|BEN|BEN#1|BEN#2|BEN#3|IAR|IBO|IBO6|IOR|IOR2|IOR6|IOR8|IR#1|IR#2|IR#3|IR#4|IR10|IR11|IR12|IR13|IR14|IR4|IR5|IR6|IR6.5|IR7|IR8|IR9|IRC|IRE|IRM|IROPH|IRPH|IRPHN|IRX|IVFLU|IVRE#2|LEG|DDW)\\s*.*";
		String appointment = "Bard, Burnetta K   (78y F)	                                  Chart#:  	SE - 062784	                                            Chart has been scanned	Phone Numbers:	         Home:	  (201) 791-7871	          Work:	 (201) 487-7744	                Mobile:	 	 02/02/2016	     11:20 AM	  40	     	          AIM - New Jersey Associates in Medicine, PA	 PT	 - 	Physical Therapy	                                                     PT - Physical Therapy	02/04/2016	     10:40 AM	40	     	          AIM - New Jersey Associates in Medicine, PA  PT	 - 	Physical Therapy	                                                     PT - Physical Therapy";
		System.out.println(appointment.matches(regex));
	}

	private String makeBeanShellSafe(String query) {
		return query.replaceAll("\\r?\\n", " ");
	}
}
