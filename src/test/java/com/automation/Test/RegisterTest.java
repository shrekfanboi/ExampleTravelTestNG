package com.automation.Test;

import org.testng.annotations.Test;

import com.automation.Generic.PropertyReader;
import com.automation.Pages.RegisterPage;

public class RegisterTest extends BaseTest {
	@Test(priority = 1)
	public void registerWithValidData() {
		RegisterPage rp = new RegisterPage();
		rp.clickSignup();
		rp.enterName(PropertyReader.getProperty("fullname"));
		rp.enterEmail(PropertyReader.getProperty("email"));
		rp.enterAddress(PropertyReader.getProperty("address"));
		rp.enterTelephone(PropertyReader.getProperty("telephone"));
//		rp.clickContinue(); <- did not work for me
		rp.clickFlexibleContinue();
		rp.selectAge("young");
		rp.selectGender("male");
		rp.selectemp("retired");
//		rp.clickedContinue(); <- did not work for me
		rp.clickFlexibleContinue();
		rp.selectProfile(PropertyReader.getProperty("profile"));
		rp.enterPassword(PropertyReader.getProperty("password"));
		rp.RenterPassword(PropertyReader.getProperty("repassword"));
		rp.clickSubmit();
		rp.clickedaccept();
	}

}