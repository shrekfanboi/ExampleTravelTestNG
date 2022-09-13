package com.automation.Test;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.automation.Generic.DriverUtils;
import com.automation.Pages.RegisterPage;

public class RegisterTest extends BaseTest {
	
	@Test(priority = 1)
	@Parameters({"username","password"})
	public void registerWithValidData(String username,String password) {
		RegisterPage rp = new RegisterPage();
		rp.clickSignup();
		rp.enterName(username);
		rp.enterEmail(DriverUtils.ReadExcelData("./src/test/resources/testData/sample.xlsx", "Sheet2", 1, 1));
		rp.enterAddress(DriverUtils.ReadExcelData("./src/test/resources/testData/sample.xlsx", "Sheet2", 1, 2));
		rp.enterTelephone(DriverUtils.ReadExcelData("./src/test/resources/testData/sample.xlsx", "Sheet2", 1, 3));
		rp.clickFlexibleContinue();
		rp.selectAge("young");
		rp.selectGender("male");
		rp.selectemp("retired");
		rp.clickFlexibleContinue();
		rp.selectProfile(DriverUtils.ReadExcelData("./src/test/resources/testData/sample.xlsx", "Sheet2", 1, 5));
		rp.enterPassword(password);
		rp.RenterPassword(password);
		rp.clickSubmit();
		rp.clickedaccept();
	}
}