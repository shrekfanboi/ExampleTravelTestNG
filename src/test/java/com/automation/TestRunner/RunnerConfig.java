package com.automation.TestRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.testng.TestNG;
import org.testng.xml.XmlClass;
import org.testng.xml.XmlSuite;
import org.testng.xml.XmlTest;

import com.automation.Generic.PropertyReader;

public class RunnerConfig {
	private static TestNG testng = new TestNG();
	private static List<XmlSuite> suites = new ArrayList<>();
	private static Map<String, Map<String, Object>> testConfig = new LinkedHashMap<>();

	public static TestNG getNG() {
		return testng;
	}

	public static void runNG() {
		getNG().setXmlSuites(getSuites());
		getNG().run();
		getSuites().remove(0);
	}
	public static List<XmlSuite> getSuites() {
		return suites;
	}

	public static XmlSuite addSuite(String suiteName) {
		XmlSuite suite = new XmlSuite();
		suite.setName(suiteName);
		suites.add(suite);
		return suite;
	}

	public static XmlTest createTest(String testName) {
		XmlTest test = new XmlTest();
		test.setName(testName);
		return test;
	}
	public static XmlTest createTest(String testName,XmlSuite suite) {
		XmlTest test = new XmlTest(suite);
		test.setName(testName);
		return test;
	}

	public static List<XmlClass> createClasses(XmlTest test, String... classnames) {
		List<XmlClass> classes = new ArrayList<>();
		for (String c : classnames) {
			classes.add(new XmlClass(c));
		}
		test.setXmlClasses(classes);
		return classes;
	}

	public static Map<String, Map<String, Object>> getTestConfig() {
		return testConfig;
	}

	public static void resolveTestCases() {
		Map<String, Object> testProps = PropertyReader.getProperties("module");
		for (String key : testProps.keySet()) {
			String[] meta = key.split("\\.");
			if (!testConfig.containsKey(meta[1])) {
				testConfig.put(meta[1], new HashMap<String, Object>());
			}
			if (meta[2].equals("order") || meta[2].equals("runCount")) {
				testConfig.get(meta[1]).put(meta[2], Integer.valueOf((String) testProps.get(key)));
			} else if (meta[2].equals("param")) {
				if (!testConfig.get(meta[1]).containsKey("param")) {
					testConfig.get(meta[1]).put("param", new HashMap<String, String>());
				}
				@SuppressWarnings("unchecked")
				Map<String, String> paramMap = (Map<String, String>) testConfig.get(meta[1]).get("param");
				paramMap.put(meta[3], testProps.get(key).toString());

			} else {
				testConfig.get(meta[1]).put(meta[2], testProps.get(key));
			}
		}
	}

	public static void initTest() {
		resolveTestCases();
		XmlSuite suite = addSuite(PropertyReader.getProperty("suite.name"));
		Map<String, Map<String, Object>> config = getTestConfig();
		List<List<XmlTest>> tests = new ArrayList<>();
		for (int i = 0; i < config.keySet().size(); i++)
			tests.add(new ArrayList<>());
		for (Map.Entry<String, Map<String, Object>> entry : config.entrySet()) {
			String name = entry.getValue().get("name").toString();
			String classname = entry.getValue().get("class").toString();
			int order = Integer.parseInt(entry.getValue().get("order").toString());
			int runCount = Integer.parseInt(entry.getValue().get("runCount").toString());
			@SuppressWarnings("unchecked")
			Map<String,String> testParams = (Map<String, String>) entry.getValue().get("param");
			List<XmlTest> nTests = new ArrayList<>();
			while (runCount > 0) {
				XmlTest test = createTest(name + "#" + runCount);
				if(testParams != null) {
					test.setParameters(testParams);					
				}
				createClasses(test, classname);
				runCount--;
				nTests.add(test);
			}
			if (!nTests.isEmpty())
				tests.set(order - 1, nTests);
		}
		tests.stream().flatMap(test -> test.stream()).toList();
		for (XmlTest test : tests.stream().flatMap(test -> test.stream()).toList()) {
			test.setXmlSuite(suite);
			suite.addTest(test);
			System.out.println(test.getName());
		}
		suite.setListeners(Arrays.asList(PropertyReader.getProperty("suite.listeners").split(",")));
		getNG().setXmlSuites(getSuites());
	}

	public static void setExecutionOrder(List<String> modules) {
		for (int i = 0; i < modules.size(); i++) {
			Integer order = i + 1;
			setExecutionOrder(modules.get(i), order);
			setRunCount(modules.get(i), 1);
		}
	}

	public static void setExecutionOrder(String testname, int order) {
		String existingOrder = PropertyReader.getProperty("module." + testname + ".order");
		if (existingOrder.equals(null))
			return;
		if (Integer.parseInt(existingOrder) == order)
			return;
		for (Map.Entry<String, Object> entry : PropertyReader.getProperties("order").entrySet()) {
			if (entry.getValue().equals(String.valueOf(order))) {
				PropertyReader.updateProperty(entry.getKey(), existingOrder);
			}
		}
		PropertyReader.updateProperty("module." + testname + ".order", String.valueOf(order));
	}

	public static void setRunCount(String testname, int rc) {
		PropertyReader.updateProperty("module." + testname + ".runCount", String.valueOf(rc));
	}

	public static void disableTests(List<String> testnames) {
		for (String test : testnames) {
			setRunCount(test, 0);
		}
	}

	public static void setEventListenerClass(String classname) {
		try {
			for (XmlSuite suite : getSuites()) {
				suite.addListener(classname);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setTestParameter(String testname, String param, String val) {
		String key = "module." + testname;
		if (!PropertyReader.getProperties(key).isEmpty()) {
			if (!PropertyReader.getProperties(key).containsKey(key + "." + param))
				PropertyReader.updateProperty(key + ".param." + param, val);
		}
	}

	public static void setDriverOptions(String... args) {
		String options = String.join(",", args);
		if(!PropertyReader.getProperty("driver.options").isEmpty()) {
			options =PropertyReader.getProperty("driver.options").concat(","+options);						
		}
		PropertyReader.updateProperty("driver.options", options);
	}
}
