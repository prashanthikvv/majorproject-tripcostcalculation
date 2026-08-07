package pageObjects;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utilities.ScreenShot;

public class CruisePage extends BasePage {

	public CruisePage(WebDriver driver) {
		super(driver);
	}

	// Locators with PageFactory
	@FindBy(xpath = "//div[@aria-label='Cruise']")
	private WebElement cruiseButton;

	@FindBy(xpath = "//span[normalize-space()='2 Nights Chennai Cruise']")
	private WebElement cruisePackageButton;

	@FindBy(id = "BE_holiday_leaving_city")
	private WebElement fromCityInput;

	@FindBy(id = "BE_holiday_destination_city")
	private WebElement toCityInput;

	@FindBy(id = "BE_holiday_search_btn")
	private WebElement holidaySearchButton;

	// Flow methods
	public void openCruiseSection() {

		// Scroll to the Cruise button before clicking
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		jsExecutor.executeScript("arguments[0].scrollIntoView(true);", cruiseButton);
		wait.until(ExpectedConditions.elementToBeClickable(cruiseButton)).click();
		System.out.println("Navigated to Cruise section.");
	}

	public void clickCruisePackage() {
		wait.until(ExpectedConditions.elementToBeClickable(cruisePackageButton)).click();
		System.out.println("Selected Cruise Package.");

		// From City
		wait.until(ExpectedConditions.elementToBeClickable(fromCityInput)).clear();
		fromCityInput.sendKeys("Chennai");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(.,'Chennai')]"))).click();

		// Destination City
		wait.until(ExpectedConditions.elementToBeClickable(toCityInput)).clear();
		toCityInput.sendKeys("Andaman");
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//li[contains(.,'Andaman')]"))).click();

		// Search
		wait.until(ExpectedConditions.elementToBeClickable(holidaySearchButton)).click();

		ScreenShot.captureScreenshot(driver, "CruisePage");

		// Optional Hotel filter
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[normalize-space()='Hotel Star']")))
					.click();
		} catch (Exception e) {
			System.out.println("Hotel filter not found");
		}

		// Optional rating filter
		try {
			wait.until(ExpectedConditions.elementToBeClickable(
					By.xpath("//div[@class='checkbox-custom']//label[@for='comfortRating3.1-4']"))).click();
			ScreenShot.captureScreenshot(driver, "RatingPage");
		} catch (Exception e) {
			System.out.println("Rating filter not found");
		}

		// Optional Apply button
		try {
			wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@class,'btn-primary')]")))
					.click();
		} catch (Exception e) {
			System.out.println("Apply button not found");
		}

		// Open first package
		try {
			wait.until(
					ExpectedConditions.elementToBeClickable(By.xpath("(//button[contains(text(),'View Details')])[1]")))
					.click();
			ScreenShot.captureScreenshot(driver, "FirstPage");
		} catch (Exception e) {
			System.out.println("Package button not found");
		}
	}

	// Policy/inclusion methods
	public List<String> cruiseInclusions() {
		return getListFromSection("//span[contains(@class,'tick')]");
	}

	public List<String> cruiseExclusions() {
		return getListFromSection("(//span[@class='tick ng-binding ng-scope'])[2]");
	}

	public List<String> cruisePaymentPolicy() {
		return getListFromSection("(//span[@class='tick ng-binding ng-scope'])[3]");
	}

	public List<String> cruiseCancellationPolicy() {
		return getListFromSection("(//span[@class='tick ng-binding ng-scope'])[4]");
	}

	// Helper method
	private List<String> getListFromSection(String xpath) {
		List<String> items = new ArrayList<>();
		try {
			WebElement section = driver.findElement(By.xpath(xpath));
			List<WebElement> liElements = section.findElements(By.tagName("li"));
			for (WebElement li : liElements) {
				items.add(li.getText());
				System.out.println(li.getText());
			}
		} catch (Exception e) {
			System.out.println("Section not found: " + xpath);
		}
		return items;
	}
}
