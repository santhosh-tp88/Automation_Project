package org.honeywell.Trace.main;

import org.honeywell.Trace.driver.DriverScript;
import org.testng.TestListenerAdapter;
import org.testng.TestNG;

public class AppMain {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		TestListenerAdapter tla = new TestListenerAdapter();
		TestNG testng = new TestNG();
		testng.setTestClasses(new Class[] { DriverScript.class });
		testng.addListener(tla);
		testng.run();
		}
}
