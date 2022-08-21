package com.automation.Test;

import static org.testng.Assert.assertEquals;

import java.util.Scanner;

import org.testng.annotations.Test;

import com.automation.Generic.DriverUtils;
import com.automation.Generic.RetryAnalyzer;
import com.automation.Pages.BookFlightPage;
import com.automation.Pages.SearchFlightPage;

public class BookingTest extends BaseTest{
	/**
	 * Method books a ticket.
	 * Will be skipped if search page was not loaded.
	 */
	@Test(dependsOnMethods = "loadSearchPage")
	public void verifyBooking()  {
		BookFlightPage sp = new BookFlightPage();
		sp.clickBookNow();
		sp.viewFlightDetails();
		sp.viewFareSummary();
		sp.proceedBooking();
		sp.fillContactInformation("abc@mail", "982920102");
		sp.proceedBooking();
		sp.addMeal();
		sp.proceedBooking();
		sp.finishPayment();
		sp.viewBookingSummary();
	}
	
	/**
	 *This method is used to trigger rerunning of loadSearchPage Test
	 * Within 10 sec, turning off internet will cause loadSearchPage to rerun
	 */
	@Test(timeOut = 10000,priority=1)
	public void turnOffWifi() {
		Scanner sc =new Scanner(System.in);
		System.out.println("Turn off your internet and enter a key to proceed ");
		sc.next();
		sc.close();
	}
	
	
	/**
	 * This method is used to load the search flight page.
	 * If page did not load, if attempts to refresh the page 10 times.
	 * @see https://testng.org/doc/documentation-main.html#rerunning
	 */
	@Test(retryAnalyzer = RetryAnalyzer.class,dependsOnMethods = "turnOffWifi",alwaysRun = true)
	public void loadSearchPage() {
		new SearchFlightPage().redirect();
		DriverUtils.getDriver().navigate().refresh();
		assertEquals(DriverUtils.getDriver().getCurrentUrl(), "https://travel.testsigma.com/");
	}

}
