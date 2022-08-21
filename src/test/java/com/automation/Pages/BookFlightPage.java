package com.automation.Pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * POM Class for Booking flight
 * @author Mainak
 *
 */
public class BookFlightPage extends BasePage{
	
	@FindBy(xpath = "//a[contains(text(),'Book Now')]")
	private List<WebElement> bookNowBtn;
	@FindBy(xpath = "//input[contains(@class,'tvlrInput')][@placeholder='Mobile No']")
	private WebElement mobileField;
	@FindBy(xpath = "//input[contains(@class,'tvlrInput')][@placeholder='Email']")
	private WebElement emailField;
	@FindBy(xpath = "//a[contains(text(),'Finish Payment')]")
	private WebElement finishPayBtn;
	
	public void clickBookNow() {
		new WebDriverWait(driver, 10).until(ExpectedConditions.visibilityOfAllElements(bookNowBtn)).get(0).click();
	}

	public void proceedBooking() {
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
		new WebDriverWait(driver, 10).until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='Continue']")))
				.click();
	}
	public void viewFlightDetails() {
		System.out.println("----Flight Details----");
		System.out.println(this.driver.findElement(By.xpath("//span[contains(text(),'DEPART')]/..")).getText());
		System.out.println(this.driver.findElement(By.xpath("//span[contains(text(),'DEPART')]/../../following-sibling::span")).getText());
		System.out.println();
	}
	public void viewFareSummary() {
		System.out.println("----Fare Summary----");
		System.out.println(this.driver.findElement(By.xpath("//p[contains(text(),'Fare Summary')]/..")).getText());
		System.out.println();
	}
	public void fillContactInformation(String email,String phone) {
		if(this.driver.getCurrentUrl().equals("https://travel.testsigma.com/traveller")) {
//			new WebDriverWait(driver, 10).until(ExpectedConditions.presenceOfElementLocated(By.xpath("'//div[@id='contactDetailsCard']")));
//			JavascriptExecutor js = (JavascriptExecutor)driver;
//			js.executeScript("document.getElementById('contactDetailsCard').scrollIntoView()");
			this.mobileField.sendKeys(phone);
			this.emailField.sendKeys(email);
		}
	}
	public void addMeal() {
		WebElement ele = new WebDriverWait(driver,10).until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Select your prefered meal(s)')]/../..//div[@class='meal-body']//a[contains(@class,'meal-counter')]")));
//		System.out.println(ele.getText());
//		act.moveToElement(ele).click().perform();
		//new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//div[@class='simply_ts-logo w-100']")));
		//cant fix it for now
		JavascriptExecutor jse2 = (JavascriptExecutor)driver;
		jse2.executeScript("arguments[0].scrollIntoView()", ele); 
		jse2.executeScript("arguments[0].click()", ele);
	}
	public void finishPayment() {
		this.finishPayBtn.click();
	}
	public void viewBookingSummary() {
		System.out.println("---Booking Summary---");
		System.out.println(this.driver.findElement(By.xpath("//p[contains(text(),'TRAVELLERS')]/..")).getText());
		System.out.println(this.driver.findElement(By.xpath("//p[contains(text(),'CONTACT DETAILS')]/..")).getText());
		System.out.println(this.driver.findElement(By.xpath("//p[contains(text(),'PRICE BREAKUP')]/..")).getText());
		System.out.println("TOTAL "+this.driver.findElement(By.xpath("//span[contains(@class,'total_sumprice')]")).getText());
		System.out.println();
	}

}
