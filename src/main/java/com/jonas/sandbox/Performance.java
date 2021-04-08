package com.jonas.sandbox;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class Performance {
	
	public String getSystemPerformance() throws Exception {
		StringBuilder sb = new StringBuilder();
		OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getPlatformMXBean(OperatingSystemMXBean.class);
		for (Method method : operatingSystemMXBean.getClass().getDeclaredMethods()) {
			method.setAccessible(true);
			if (method.getName().startsWith("get")
					&& !method.getName().equalsIgnoreCase("getProcessCpuTime")
					&& Modifier.isPublic(method.getModifiers())) {
				
				Object value;
			    try {
			    	value = method.invoke(operatingSystemMXBean);
			    } catch (Exception e) {
			    	value = e;
			    }
			    if (method.getName().contains("Size")) {
			    	value = (Long) value / (1024*1024);
			    	sb.append(method.getName().replaceAll("get", "") + " = " + value + "Mb\n");
			    } else {
			    	
			    	sb.append(method.getName().replaceAll("get", "") + " = " + value + "%\n");			    	
			    }
			    
			} 
		}
		try {
	        synchronized(this){
	            wait(2000);
	        }
	    } catch (InterruptedException e) {
	        // ...
	    }
		return sb.toString();
		
	}
	
	public static double getProcessCpuLoad() throws MalformedObjectNameException, ReflectionException, InstanceNotFoundException {

	    MBeanServer mbs    = ManagementFactory.getPlatformMBeanServer();
	    ObjectName name    = ObjectName.getInstance("java.lang:type=OperatingSystem");
	    AttributeList list = mbs.getAttributes(name, new String[]{ "ProcessCpuLoad" });

	    if (list.isEmpty())     return Double.NaN;

	    Attribute att = (Attribute)list.get(0);
	    Double value  = (Double)att.getValue();

	    return ((int)(value * 1000) / 10.0);        // returns a percentage value with 1 decimal point precision
	}
	
	public static void main(String[] args) {
		try {
			for (int i = 0; i < 60; i++) {
				Performance p = new Performance();
				System.out.println(p.getSystemPerformance() + "\n");	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
