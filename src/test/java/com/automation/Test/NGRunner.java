package com.automation.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

/**
 * Running TestNG programatically using command line args
 * @see https://testng.org/doc/documentation-main.html#running-testng-programmatically
 * @author mainak.mondal
 *
 */
public class NGRunner {
	static final TestNG testng = new TestNG();
	static List<XmlSuite> suites = new ArrayList<>();

	public  static XmlSuite addSuite(String suiteName) {
		XmlSuite suite = new XmlSuite();
		suite.setName(suiteName);
		suites.add(suite);
		return suite;
	}
	
	public static XmlTest createTest(String testName,XmlSuite suite) {
		XmlTest test = new XmlTest(suite);
		test.setName(testName);
		return test;
	}
	public static List<XmlClass> createClasses(XmlTest test,String... classnames) {
		List<XmlClass> classes = new ArrayList<>();
		for(String c:classnames) {
			classes.add(new XmlClass(c));
		}
		test.setXmlClasses(classes);
		return classes;
	}
	
	public static void main(String[] args) {
		
		//create a suite
		XmlSuite suite = addSuite("Suite");
		
		if(args.length == 0) System.exit(1);
		Set<String> testCases=new LinkedHashSet<>(Arrays.asList(args));  
		
		System.out.println("Will Run the following Tests---->");
		for(String tc:testCases) {
			System.out.println(tc.toUpperCase());
			switch(tc) {
			case "registration":
				XmlTest registerTest = createTest("registerTest", suite);
				createClasses(registerTest,"com.automation.Test.RegisterTest");
				break;
			case "login":
				XmlTest loginTest = createTest("loginTest", suite);
				createClasses(loginTest,"com.automation.Test.LoginTest");
				break;
			case "searching":
				XmlTest searchTest = createTest("searchFlightTest", suite);
				createClasses(searchTest,"com.automation.Test.SearchFlightTest");
				break;
			case "booking":
				XmlTest bookTest = createTest("BookFlightTest", suite);
				createClasses(bookTest,"com.automation.Test.BookingTest");
				break;
			default:
				break;
			}
		}
		
		suite.addListener("com.automation.Generic.Listener");
		testng.setXmlSuites(suites);
		testng.run();
		
	}

}
