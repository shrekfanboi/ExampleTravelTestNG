package com.automation.Test;

import java.time.Duration;

import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.automation.Generic.DriverUtils;
import com.automation.Generic.PropertyReader;

public class BaseTest {
	private static int implicitWaitTime;
	private static int pageLoadTime;
	private static String url;

	@BeforeSuite
	public void setupBeforeSuit() {
		PropertyReader.initProperty();
		implicitWaitTime = Integer.parseInt(PropertyReader.getProperty("driver.implicit_wait"));
		pageLoadTime = Integer.parseInt(PropertyReader.getProperty("driver.pageload_timeout"));
		url = PropertyReader.getProperty("url");
	}

	@BeforeTest
	@Parameters({ "browser", "mode" })
	public void beforeTest(String browser, String mode) {
		String[] modes = mode.split(",");
		if(browser.equals("Firefox") && modes.length > 0 && modes[0].equals("incognito")) modes[0] = "private"; 
		if(browser.equals("Edge") && modes.length > 0 && modes[0].equals("incognito")) modes[0] = "Inprivate"; 
		DriverUtils.createWebDriver(browser, modes);
	}

	@BeforeClass()
	public void beforeClass() {
		DriverUtils.getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(implicitWaitTime));
		DriverUtils.getDriver().manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTime));
		DriverUtils.getDriver().get(url);
	}

	@AfterTest
	public void tearDown() {
		DriverUtils.getDriver().quit();
	}

	@AfterSuite
	public void flushProperties() {
		PropertyReader.resetProperty();
	}
}
