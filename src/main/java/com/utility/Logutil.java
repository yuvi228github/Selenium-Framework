package com.utility;


import org.apache.log4j.Logger;

public class Logutil {

	// Initialize Log4j logs

	private static Logger Log = Logger.getLogger(Logutil.class.getName());//

	// This is to print log for the beginning of the test case, as we usually run so
	// many test cases as a test suite

	public static void startTestCase(String Testcasename) {

		Log.info("****************************************************************************************");

		Log.info("$$$$$$$$$$$$$$$$$$$$$" + Testcasename + "     $$$$$$$$$$$$$$$$$$$$$$$$$");

		Log.info("****************************************************************************************");

	}

	// This is to print log for the ending of the test case

	public static void endTestCase(String Testcasename) {

		Log.info("****************************************************************************************");

		Log.info("XXXXXXXXXXXXXXXXXXXXXXX             " + Testcasename + "             XXXXXXXXXXXXXXXXXXXXXX");

		Log.info("****************************************************************************************");

	}

	// Need to create these methods, so that they can be called

	public static void info(Object message) {

		Log.info(message);

	}

	public static void warn(String message) {

		Log.warn(message);

	}

	public static void error(String message) {

		Log.error(message);

	}

	public static void fatal(String message) {

		Log.fatal(message);

	}

	public static void debug(String message) {

		Log.debug(message);

	}

}
