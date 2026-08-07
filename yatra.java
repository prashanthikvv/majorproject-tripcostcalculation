package tripCostReport;

import java.util.List;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import base.BaseTest;
import tripCostReport.MyListener;
import pageObjects.CruisePage;
import pageObjects.HomePage;
import pageObjects.HotelPage;
import utilities.ExcelUtils;
import utilities.ReportLogger;

@Listeners(MyListener.class)
public class yatra extends BaseTest {

    @Test(priority = 1)
    public void hotelSearch() throws Exception {

        HomePage home = new HomePage(getDriver());

        HotelPage hotel = new HotelPage(getDriver());

        ReportLogger.info("Opening Hotel Section");

        home.openHotelSection();

        ReportLogger.pass("Hotel Section Opened");


        ReportLogger.info("Searching Nairobi");

        home.searchHotel("Nairobi");

        ReportLogger.pass("Nairobi Selected");


        ReportLogger.info("Selecting Check-In and Check-Out Dates");

        home.selectDates();

        ReportLogger.pass("Dates Selected");


        ReportLogger.info("Selecting Guests");

        home.selectGuests();

        ReportLogger.pass("Guests Selected");


        ReportLogger.info("Clicking Search Button");

        home.clickSearch();

        ReportLogger.pass("Search Completed");


        ReportLogger.info("Sorting by Highest Rating");

        hotel.sortByHighestRating();

        ReportLogger.pass("Sorting Completed");


        ReportLogger.info("Fetching Top 3 Hotel Details");

        List<Map<String,String>> hotels = hotel.getDetailsOfTopHotels();

        Assert.assertTrue(hotels.size() > 0);

        ReportLogger.pass("Top Hotel Details Retrieved");


        ExcelUtils excel = new ExcelUtils();

        excel.writeHotelData(hotels, "HotelData.xlsx");

        ReportLogger.pass("Hotel Data Written into Excel");
    }

    @Test(priority = 2)
    public void cruiseSearch() throws Exception {

        CruisePage cruise = new CruisePage(getDriver());

        ReportLogger.info("Launching Yatra Website");

        getDriver().get("https://www.yatra.com/");

        ReportLogger.pass("Yatra Website Opened");

        ReportLogger.info("Opening Cruise Section");

        cruise.openCruiseSection();

        ReportLogger.pass("Cruise Section Opened");

        ReportLogger.info("Selecting Cruise Package");

        cruise.clickCruisePackage();

        ReportLogger.pass("Cruise Package Selected");

        List<String> inclusions = cruise.cruiseInclusions();

        List<String> exclusions = cruise.cruiseExclusions();

        List<String> payments = cruise.cruisePaymentPolicy();

        List<String> cancellations = cruise.cruiseCancellationPolicy();

        Assert.assertFalse(inclusions.isEmpty());

        Assert.assertFalse(exclusions.isEmpty());

        Assert.assertFalse(payments.isEmpty());

        Assert.assertFalse(cancellations.isEmpty());

        ReportLogger.pass("Cruise Details Extracted Successfully");

        ExcelUtils excel = new ExcelUtils();

        excel.writeCruiseData(
                inclusions,
                exclusions,
                payments,
                cancellations,
                "CruiseData.xlsx");

        ReportLogger.pass("Cruise Data Written into Excel");
    }
}