package com.automation.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.automation.Generic.DriverUtils;
import com.automation.Pages.LoginPage;

public class LoginTest extends BaseTest{
	
	@DataProvider(name="loginProvider")
	public  String[][] getData(){
		return new String[][]{ {"user1","pass1"},{"user2","pass2"},{"user3","pass3"}};
	}
	
	/**
	 * This test gets invalid data from dataprovider so it is suppose to fail.
	 * Login is asserted by checking the current url
	 * @throws AssertionError
	 */
	@Test(dataProvider = "loginProvider",priority = 1)
	public void verifyLoginWithInValidData(String username,String password) throws AssertionError {
		System.out.println("Running Login Test with "+username);
		LoginPage lp = new LoginPage();
		lp.goToLoginPage();
		lp.enterUsername(username);
		lp.enterPassword(password);
		lp.clickLogin();
//		assertEquals(DriverUtils.getDriver().getCurrentUrl(),"https://examples.testsigma.com/?username=admin&pass=12345");
		assertFalse(DriverUtils.getDriver().getCurrentUrl().contains("login"), "Login Unsuccesful");
	}
	
	/**
	 * This test reads data from sample.xlsx which are correct credentials to login.
	 * Test should always pass.
	 */
	@Test(priority=2,alwaysRun = true)
	public void verifyLoginWithValidData()  {
		System.out.println("Running Login Test with valid data");
		LoginPage lp = new LoginPage();
		lp.goToLoginPage();
		lp.enterUsername(DriverUtils.ReadExcelData("./data/sample.xlsx", "Sheet1", 1, 0));
		lp.enterPassword(DriverUtils.ReadExcelData("./data/sample.xlsx", "Sheet1", 1, 1));
		lp.clickLogin();
//		assertEquals(DriverUtils.getDriver().getCurrentUrl(),"https://examples.testsigma.com/?username=admin&pass=12345");
		assertFalse(DriverUtils.getDriver().getCurrentUrl().contains("login"), "Login Unsuccesful");
}
	/**
	 * must log in first
	 */
	@Test(dependsOnMethods = "verifyLoginWithValidData",description = "verify logout")
	public void verifyLogout() {
		new LoginPage().clickLogout();
	}
	
	
	@Test(dependsOnMethods = "verifyLogout",description="verify signup")
	public void verifySignUpLink() {
		LoginPage lp = new LoginPage();
		lp.goToLoginPage();
		lp.clickSignUpButton();
		assertTrue(DriverUtils.getDriver().getCurrentUrl().contains("signup"));
	}
	
	@Test(dependsOnMethods = "verifyLogout",description="verify password")
	public void verifyResetPassword() {
		LoginPage lp = new LoginPage();
		lp.goToLoginPage();
		lp.clickForgotPasswordButton();
		lp.assertForgotPasswordForm();
	}

}
