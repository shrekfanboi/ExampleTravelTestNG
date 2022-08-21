package com.automation.Pages;

import java.io.File;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

/**
 * POM Class for Register Page
 * @author sanchit
 *
 */
public class RegisterPage extends BasePage{
	
	@FindBy(xpath="//a[.='Signup']")
	private WebElement clickSignup;
	
	@FindBy(xpath="//input[@id='icon_prefix']")
	private WebElement Fullname;
	
	@FindBy(xpath="//input[@id='icon_email']")
	private WebElement Email;
	
	@FindBy(xpath="//input[@id='icon_address']")
	private WebElement Address;
	
	@FindBy(xpath="//input[@id='icon_telephone']")
	private WebElement Telephone;
	
	@FindBy(xpath="//button[.='CONTINUE']")
	private WebElement clickContinue;
	
	@FindBy(xpath="//input[@name='age'][@value='young']")
	private WebElement ageYoung;
	@FindBy(xpath="//input[@name='age'][@value='middle']")
	private WebElement ageMiddle;
	@FindBy(xpath="//input[@name='age'][@value='old']")
	private WebElement ageOld;
	
	@FindBy(xpath="//input[@name='gender'][@value='male']")
	private WebElement genderMale;
	@FindBy(xpath="//input[@name='gender'][@value='female']")
	private WebElement genderFemale;
	
	@FindBy(xpath="//input[@name='status'][@value='studying']")
	private WebElement empstudy;
	@FindBy(xpath="//input[@name='status'][@value='working']")
	private WebElement empwork;
	@FindBy(xpath="//input[@name='status'][@value='retired']")
	private WebElement empretired;


	
	@FindBy(xpath="//button[.='BACK']")
	private WebElement clickBack;
	@FindBy(xpath="/html/body/div/div/div/div/form/ul/li[2]/div[2]/div[2]/button[1]")
	private WebElement clickedContinue;
	
	@FindBy(xpath="//li[contains(@class,'active')]//button[contains(text(),'CONTINUE')]")
	private WebElement flexibleContinue;
	
	@FindBy(xpath="//input[@type='file']")
	private WebElement profile;
	
	@FindBy(xpath="//input[@id='icon_Pass']")
	private WebElement Password;
	
	@FindBy(xpath="//input[@id='icon_repass']")
	private WebElement Repass;
	
	@FindBy(xpath="//input[@value='SUBMIT']")
	private WebElement Submit;
	
//	@FindBy(xpath="//button[.='BACK']")
//	private WebElement clickedBack;
//	
	@FindBy(xpath="//button[.='Accept']")
	private WebElement acceptCookies;
	
//	@FindBy(xpath="//div[@class='step-content collection pb-0']")
//	private WebElement div;
	
	
	

	
	
	public void clickSignup()
	{
		clickSignup.click();
	}
	public void enterName(String str)
	{
		Fullname.sendKeys(str);
	}
	public void enterEmail(String str)
	{
		Email.sendKeys(str);
	}
	public void enterAddress(String str)
	{
		Address.sendKeys(str);
	}
	public void enterTelephone(String str)
	{
		Telephone.sendKeys(str);
	}
	public void clickContinue()
	{
		clickContinue.click();
	}
	/**
	 * @param age
	 * can be young, middle or old
	 */
	public void selectAge(String age)
	{
		if(age.equals("young")) new Actions(this.driver).moveToElement(ageYoung).click().perform();
		else if(age.equals(" middle")) new Actions(this.driver).moveToElement(ageMiddle).click().perform();
		else if(age.equals("old")) new Actions(this.driver).moveToElement(ageOld).click().perform();
		else throw new IllegalArgumentException("Not a valid age");
//		age.click();
	}
	/**
	 * @param gender
	 * can be male or female
	 */
	public void selectGender(String gender)
	{
		if(gender.equals("male")) new Actions(this.driver).moveToElement(genderMale).click().perform();
		else if(gender.equals("female")) new Actions(this.driver).moveToElement(genderFemale).click().perform();
		else throw new IllegalArgumentException("Not a valid gender");
	}
	
	/**
	 * @param status
	 * can be studying, working or retired
	 */
	public void selectemp(String status)
	{
		if(status.equals("studying")) new Actions(this.driver).moveToElement(empstudy).click().perform();
		else if(status.equals("working")) new Actions(this.driver).moveToElement(empwork).click().perform();
		else if(status.equals("retired")) new Actions(this.driver).moveToElement(empretired).click().perform();
		else throw new IllegalArgumentException("Not a valid status");
	}
	/**
	 * Uses Javascript to scroll to continue button and click it.
	 * Location of the button is determined by the active class
	 */
	public void clickFlexibleContinue() {
		JavascriptExecutor js = (JavascriptExecutor)this.driver;
		js.executeScript("arguments[0].scrollIntoView()",this.flexibleContinue);
		this.flexibleContinue.click();
	}
	
	public void clickedContinue()
	{
		clickedContinue.click();
	}
	public void clickBack()
	{
		clickBack.click();
	}
	public void selectProfile(String img)
	{
		profile.sendKeys(new File(img).getAbsolutePath());
	}
	public void enterPassword(String str)
	{
		Password.sendKeys(str);
	}
	public void RenterPassword(String str)
	{
		Repass.sendKeys(str);
	}
	public void clickSubmit()
	{
		Submit.click();
	}
	public void clickedaccept()
	{
		acceptCookies.click();
	}


}
