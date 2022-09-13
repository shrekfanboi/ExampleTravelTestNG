package com.automation.Pages;

import static org.testng.Assert.assertNotEquals;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * POM Class for Login Page
 * @author Ujjwal
 *
 */
public class LoginPage extends BasePage {
	
	@FindBy(xpath="//a[text()='login']")
	private WebElement loginNav;
	@FindBy(xpath="//input[@name='username']")
	private WebElement  usernameField;
	@FindBy(xpath="//input[@name='pass']")
	private WebElement  passwordField;
	@FindBy(xpath="//button[text()='Login']")
	private WebElement loginBtn;		
	@FindBy(xpath="//a[text()='logout']")
	private WebElement logoutBtn;
	@FindBy(xpath="//div[@class='actionbtns']/a[text()='Signup']")
	private WebElement signUpBtn;
	@FindBy(xpath="//div[@class='actionbtns']/a[text()='Forgot password?']")
	private WebElement forgotPasswordBtn;
	
	public void goToLoginPage() {
		if(this.driver.getCurrentUrl().equals("https://examples.testsigma.com")) this.loginNav.click();
		else {
			this.driver.navigate().to("https://examples.testsigma.com/login");
		}
	}
	
	public void enterUsername(String username) {
		this.usernameField.clear();
		this.usernameField.sendKeys(username);
	}
	public void enterPassword(String password) {
		this.passwordField.clear();
		this.passwordField.sendKeys(password);
	}
	public void clickLogin() {
		new WebDriverWait(driver,Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(loginBtn)).click();
	}
	public void clickLogout() {
		this.logoutBtn.click();
	}
	public void clickSignUpButton() {
		this.signUpBtn.click();
	}
	public void clickForgotPasswordButton() {
		this.forgotPasswordBtn.click();
	}
	
	public void assertForgotPasswordForm() {
		WebElement submitBtn = new WebDriverWait(this.driver,Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(),'Submit')]")));
		assertNotEquals(submitBtn.getAttribute("display"),"none");		//check display property of submit button 

	}
	
	

}
