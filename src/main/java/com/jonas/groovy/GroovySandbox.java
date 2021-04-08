package com.jonas.groovy;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import org.junit.Test;
import org.luaj.vm2.LuaValue;

import bsh.Interpreter;
import groovy.lang.GroovyShell;

@SuppressWarnings({"unused", "restriction"})
public class GroovySandbox {

	@Test
	public void test() {
		try {
			long start = System.currentTimeMillis();
			for (int i = 0; i < 100; i++) {
				String toEvaluate = "'string' == 'string' && ('string' == 'String' || 'a' == 'N' || true)";
				boolean b = (Boolean) new GroovyShell().evaluate(toEvaluate);
			}
			System.out.println("Groovy took: " + (System.currentTimeMillis() - start) + "ms");

			start = System.currentTimeMillis();
			for (int i = 0; i < 100; i++) {
				ScriptEngine e = new ScriptEngineManager().getEngineByName("luaj");
				boolean b = (e.eval(
						"result = \"string\" == \"string\" and (\"strng\" == \"Sring\" or \"a\" == \"N\" or true)") == LuaValue.TRUE);
			}
			System.out.println("Lua took: " + (System.currentTimeMillis() - start) + "ms");

			start = System.currentTimeMillis();
			for (int i = 0; i < 100; i++) {
				String toEvaluate = "\"string\".equals(\"string\") && (\"string\".equals(\"String\") || \"a\".equals(\"N\") || true)";
				boolean b = (Boolean) new Interpreter().eval(toEvaluate);
			}
			System.out.println("Beanshell took: " + (System.currentTimeMillis() - start) + "ms");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
