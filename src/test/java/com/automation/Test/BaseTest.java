package com.automation.Test;

import java.util.concurrent.TimeUnit;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import com.automation.Generic.DriverUtils;
import com.automation.Generic.PropertyReader;

public class BaseTest {

	@BeforeTest
	public void beforeTest() {
		DriverUtils.createWebDriver("--start-maximized","--incognito");
		PropertyReader.initProperty();
	}
	@BeforeClass()
	public void beforeClass() {		
		DriverUtils.getDriver().manage().window().maximize();
		DriverUtils.getDriver().manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		DriverUtils.getDriver().manage().timeouts().pageLoadTimeout(5, TimeUnit.SECONDS);
		DriverUtils.getDriver().get(PropertyReader.getProperty("url"));
	}
	
	@AfterTest
	public void afterTest() {
		DriverUtils.getDriver().quit();
	}
}
