package testCases;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Test;

import base.BaseTest;
import pageObjects.CruisePage;
import pageObjects.HomePage;
import pageObjects.HotelPage;
import utilities.ExcelUtils;

public class HotelTest extends BaseTest {
	@Test(priority = 1)
	public void hotelSearch() throws Exception {
		HomePage home = new HomePage(getDriver());
		HotelPage hotel = new HotelPage(getDriver());

		home.openHotelSection();
		home.searchHotel("Nairobi");
		home.selectDates();
		home.selectGuests();
		home.clickSearch();

		hotel.sortByHighestRating();
		List<Map<String, String>> topHotels = hotel.getDetailsOfTopHotels();

		Assert.assertTrue(topHotels.size() > 0, "No hotels were fetched!");

		// Write to Excel
		ExcelUtils excel = new ExcelUtils();
		excel.writeHotelData(topHotels, "HotelData.xlsx");
	}

	@Test(priority = 2)
	public void cruiseSearch() throws Exception {
		CruisePage cruise = new CruisePage(getDriver());

		getDriver().get("https://www.yatra.com/");
		cruise.openCruiseSection();
		cruise.clickCruisePackage();

		List<String> inclusions = cruise.cruiseInclusions();
		List<String> exclusions = cruise.cruiseExclusions();
		List<String> paymentPolicy = cruise.cruisePaymentPolicy();
		List<String> cancellationPolicy = cruise.cruiseCancellationPolicy();

		Assert.assertFalse(inclusions.isEmpty(), "No cruise inclusions found!");
		Assert.assertFalse(exclusions.isEmpty(), "No cruise exclusions found!");
		Assert.assertFalse(paymentPolicy.isEmpty(), "No cruise payment policy found!");
		Assert.assertFalse(cancellationPolicy.isEmpty(), "No cruise cancellation policy found!");

		// Write to Excel
		ExcelUtils excel = new ExcelUtils();
		excel.writeCruiseData(inclusions, exclusions, paymentPolicy, cancellationPolicy, "CruiseData.xlsx");
	}

}
