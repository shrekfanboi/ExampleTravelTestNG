package com.automation.Pages;

import java.text.DateFormatSymbols;
import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.automation.Generic.DriverUtils;

/**
 * POM Class for Searching flight
 * @author Mainak
 *
 */
public class SearchFlightPage extends BasePage {

	@FindBy(xpath = "//span[contains(text(),'Go to Travel')]")
	private WebElement navBtn;
	@FindBy(id = "departure_cities")
	private WebElement departFromField;
	@FindBy(id = "arraival_cities")
	private WebElement arriveAtField;
	@FindBy(xpath = "//input[@id='departure']")
	private WebElement departOnField;
	@FindBy(xpath = "//input[@id='return']")
	private WebElement returnOnField;
	@FindBy(id = "travellerAndClass")
	private WebElement travelClassField;
	@FindBy(xpath = "//button[contains(text(),'Search flights')]")
	private WebElement searchBtn;
	@FindBy(xpath = "//div[text()='Round trip Flying']")
	private WebElement roundTrip;
	@FindBy(xpath = "//div[text()='Multi city']")
	private WebElement multiCity;
	@FindBy(xpath = "//div[text()=One way Flying']")
	private WebElement oneWay;
	
	
	

	private final String DEPARTPICKER = "//label[contains(text(),'Depart')]/..//div[contains(@class,'datepicker-modal')]";
	private final String RETURNPICKER = "//label[contains(text(),'Return')]/..//div[contains(@class,'datepicker-modal')]";

	public enum TravelClass {
		Economy, Premium_Economy, Business, First_Class;

		@Override
		public String toString() {
			return super.toString().replace("_", " ");
		}
	}

	public enum FlightType {
		OneWay, RoundTrip, MultiCity;
	}

	public void redirect() {
		if (!this.driver.getCurrentUrl().contains("travel.testsigma.com")) {
//			System.out.println("switching tabs");
			JavascriptExecutor js = (JavascriptExecutor)driver;
			js.executeScript("arguments[0].scrollIntoView()", this.navBtn);
			this.navBtn.click();
			DriverUtils.switchTab();
		}
	}

	public void setFlightType(FlightType type) {
		JavascriptExecutor  js = (JavascriptExecutor)this.driver;
		if (type.equals(FlightType.OneWay))
		{
			js.executeScript("arguments[0].scrollIntoView();window.scrollBy(0,-80);", this.oneWay);
			this.oneWay.click();			
		}
		else if (type.equals(FlightType.RoundTrip)) {
			js.executeScript("arguments[0].scrollIntoView();window.scrollBy(0,-80);", this.roundTrip);
			System.out.println(this.roundTrip.isDisplayed());
			this.roundTrip.click();
		}
		else {
			js.executeScript("arguments[0].scrollIntoView();window.scrollBy(0,-80);", this.multiCity);
			this.multiCity.click();
		}
	}

	private String resolveDate(String date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		LocalDate localDate = LocalDate.parse(date, formatter);
		String[] months = new DateFormatSymbols().getMonths();
		Integer year = localDate.getYear();
		Integer day = localDate.getDayOfMonth();
		String month = months[localDate.getMonthValue() - 1];
		String finalDate = month + " " + day.toString() + " " + year.toString();
		return finalDate;
	}

	public void setDepartureCitiesField(String city) {
		this.departFromField.click();
		List<WebElement> cities = new WebDriverWait(this.driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
						By.xpath("//span[@id='departure_cities']/..//a[contains(@class,'dropdown-item')]")));
		cities.stream().filter(element -> element.getText().equals(city)).findFirst().get().click();
		//if avove shows compliance error, then uncomment and execute the following code
		/*
		 * for(WebElement e:cities){
		 * if(e.getText().equals(city)) e.click()
		 * }
		 */
	}

	public void setArrivalCitiesField(String city) {
		this.arriveAtField.click();
		List<WebElement> cities = new WebDriverWait(this.driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(
						By.xpath("//span[@id='arraival_cities']/..//a[contains(@class,'dropdown-item')]")));
		cities.stream().filter(element -> element.getText().equals(city)).findFirst().get().click();
		//if avove shows compliance error, then uncomment and execute the following code
		/*
		 * for(WebElement e:cities){
		 * if(e.getText().equals(city)) e.click()
		 * }
		 */
	}

	public void setDepartureDateField(String year, String month, String day) {
		this.departOnField.click();
		String datePath = DEPARTPICKER
				+ "//button[@data-year='%s'][@data-month='%s'][@data-day='%s']".formatted(year, month, day);
		String submitPath = DEPARTPICKER + "//button[contains(text(),'Ok')]";
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath(datePath))).click();
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath(submitPath))).click();
	}

	public void setDepartureDateField(String date) {
		String finalDate = resolveDate(date);
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		js.executeScript("document.getElementById('departure').value=arguments[0]", finalDate);
	}

	public void setReturnDateField(String year, String month, String day) {
		try {
			this.returnOnField.click();
		}
		catch(ElementClickInterceptedException e) {
			new Actions(driver).moveToElement(this.returnOnField).click().perform();
		}
		String datePath = RETURNPICKER
				+ "//button[@data-year='%s'][@data-month='%s'][@data-day='%s']".formatted(year, month, day);
		String submitPath = RETURNPICKER + "//button[contains(text(),'Ok')]";
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath(datePath))).click();
		new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.xpath(submitPath))).click();

	}

	public void setReturnDateField(String date) {
		String finalDate = resolveDate(date);
		JavascriptExecutor js = (JavascriptExecutor) this.driver;
		js.executeScript("document.getElementById('return').value=arguments[0]", finalDate);
	}

	public void setPassengerAndClassField(TravelClass tclass) {
		try {
			this.travelClassField.click();			
		}
		catch(ElementClickInterceptedException e) {
			new Actions(driver).moveToElement(this.travelClassField).click().perform();			
		}
		WebElement travelClassList = new WebDriverWait(this.driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.visibilityOfElementLocated(
						By.xpath("//p[contains(text(),'CHOOSE TRAVEL CLASS')]/following-sibling::ul")));
		String xpath = "//li[text()='%s']";
		travelClassList.findElement(By.xpath(String.format(xpath, tclass.toString()))).click();
		this.driver.findElement(By.xpath("//button[text()='DONE']")).click();
	}

	public void clickSearchButton() {
		this.searchBtn.click();
	}

	public static int getSearchResultsLength(WebDriver driver) {
		return new WebDriverWait(driver, Duration.ofSeconds(10))
				.until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.xpath("//a[text()='Book Now']"))).size();
	}

	

}
