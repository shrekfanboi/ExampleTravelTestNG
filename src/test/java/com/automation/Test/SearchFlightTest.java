package com.automation.Test;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import com.automation.Generic.DriverUtils;
import com.automation.Pages.SearchFlightPage;
import com.automation.Pages.SearchFlightPage.FlightType;
import com.automation.Pages.SearchFlightPage.TravelClass;


public class SearchFlightTest extends BaseTest{
//	private String depCity;
//	private String arrCity;
//	private TravelClass tclass;
//	
//	public SearchFlightTest(String dcity,String arrcity,TravelClass tclass) {
//		this.depCity = dcity;
//		this.arrCity = arrcity;
//		this.tclass = tclass;
//	}
	
	@Test(priority=1)
	public void BasicSearch() {
		SearchFlightPage sp = new SearchFlightPage();
		System.out.println(DriverUtils.getDriver().getCurrentUrl());
		sp.redirect();
		sp.clickSearchButton();
		int num_search = SearchFlightPage.getSearchResultsLength(DriverUtils.getDriver());
		System.out.println(num_search+" search results were found");
		assertEquals(num_search>0, true);
	}
	
	@Test(priority=2)
	public void ParameterizedSearch() {
		SearchFlightPage sp = new SearchFlightPage();
		sp.setDepartureCitiesField("London");
		sp.setArrivalCitiesField("Los Angeles");
		sp.setDepartureDateField("2022", "7", "25");
		sp.setPassengerAndClassField(TravelClass.Economy);
		sp.clickSearchButton();
		int num_search = SearchFlightPage.getSearchResultsLength(DriverUtils.getDriver());
		System.out.println(num_search+" search results were found");
		assertEquals(num_search>0, true);
	}
	@Test(priority=3)
	public void RoundTripSearch() {
		SearchFlightPage sp = new SearchFlightPage();
		sp.setFlightType(FlightType.RoundTrip);
		sp.setDepartureCitiesField("Washington, D.C.");
		sp.setArrivalCitiesField("New York City");
		sp.setDepartureDateField("2022", "7", "28");
		sp.setReturnDateField("2022", "7", "31");
		sp.setPassengerAndClassField(TravelClass.First_Class);
		sp.clickSearchButton();
		int num_search = SearchFlightPage.getSearchResultsLength(DriverUtils.getDriver());
		System.out.println(num_search+" search results were found");
		assertEquals(num_search>0, true);
	}

	@Test(priority=4)
	public void MultiCitySearch() {
		SearchFlightPage sp = new SearchFlightPage();
		sp.setFlightType(FlightType.MultiCity);
		sp.setDepartureCitiesField("Washington, D.C.");
		sp.setArrivalCitiesField("London");
		sp.setDepartureDateField("2022", "7", "28");
		sp.setReturnDateField("2022", "7", "31");
		sp.setPassengerAndClassField(TravelClass.Premium_Economy);
		sp.clickSearchButton();
		int num_search = SearchFlightPage.getSearchResultsLength(DriverUtils.getDriver());
		System.out.println(num_search+" search results were found");
		assertEquals(num_search>0, true);
	}
	
	@Ignore
	public void SetFieldsUsingJS() {
		//this test is optional
		SearchFlightPage sp = new SearchFlightPage();
		sp.setFlightType(FlightType.MultiCity);
		sp.setDepartureCitiesField("Washington, D.C.");
		sp.setArrivalCitiesField("London");
		sp.setDepartureDateField("25/08/2022");
		sp.setReturnDateField("28/08/2022");
		sp.setPassengerAndClassField(TravelClass.Business);
		sp.clickSearchButton();
		int num_search = SearchFlightPage.getSearchResultsLength(DriverUtils.getDriver());
		System.out.println(num_search+" search results were found");
		assertEquals(num_search>0, true);
	}
}
