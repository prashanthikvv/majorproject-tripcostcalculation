package pageObjects;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import utilities.ScreenShot;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
		ScreenShot.captureScreenshot(driver, "HomePage");
	}

	// Locators with PageFactory
	
	@FindBy(xpath = "//button[@id=\"simple-tab-1\"]")
	private WebElement hotelButton;
	
	@FindBy(xpath = "//button[@aria-label='Button']//div[@class='SearchInputField_searchRegionInfo__R91xS']")
	private WebElement regionButton;

	@FindBy(xpath = "//input[@class='Input_input__hHazC']")
	private WebElement searchBox;

	@FindBy(xpath = "//button[@class='SearchItems_details__6nDmU buttonReset']")
	private WebElement firstOption;

	@FindBy(xpath = "//button[@aria-label='Search']")
	private WebElement searchButton;

	@FindBy(xpath = "(//button[@class='CustomDateField_itemsWrapper__jA7BC undefined buttonReset'])[1]")
	private WebElement datePickerButton;

	@FindBy(xpath = "(//button[@class='undefined buttonReset'])[2]")
	private WebElement guestButton;

	@FindBy(xpath = "//button[contains(text(), 'Apply')]")
	private WebElement applyGuestsButton;

	// Methods
	public void openHotelSection() {
        wait.until(ExpectedConditions.elementToBeClickable(hotelButton)).click();
        System.out.println("Navigated to Hotel section.");
    }
	
	public void searchHotel(String city) throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(regionButton)).click();

		wait.until(ExpectedConditions.visibilityOf(searchBox)).sendKeys(city);

		Thread.sleep(2000);

		// Always re-fetch the list before clicking
		List<WebElement> options = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(
				org.openqa.selenium.By.xpath("//button[@class='SearchItems_details__6nDmU buttonReset']")));

		if (!options.isEmpty()) {
			System.out.println("Selecting: " + options.get(0).getText());
			options.get(0).click();
		} else {
			System.out.println("No options appeared for city search.");
		}
		
		Thread.sleep(3000);
	}

	public void selectDates() throws InterruptedException {
		// Keep hardcoded logic for now
		wait.until(ExpectedConditions.elementToBeClickable(datePickerButton)).click();
 
		LocalDate today = LocalDate.now();
		LocalDate startDate = today.plusDays(1);
		LocalDate endDate = startDate.plusDays(5);
 
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, MMMM d'th', yyyy");
 
		String startLabel = "Choose " + startDate.format(formatter);
		String endLabel = "Choose " + endDate.format(formatter);
 
		WebElement startElement = wait.until(ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.xpath("//div[@aria-label='Choose Thursday, August 6th, 2026']")));
		startElement.click();
				
		Thread.sleep(3000);
	
		//wait.until(ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.xpath("//p[contains(text(), 'Check-out Date')]"))).click();
	
		WebElement endElement = wait.until(ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.xpath("//div[@aria-label='Choose Monday, August 10th, 2026']")));
		endElement.click();
	}
		

	public void selectGuests() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(guestButton)).click();

		Thread.sleep(3000);

		WebElement guestOption = wait.until(ExpectedConditions.elementToBeClickable(org.openqa.selenium.By.xpath("(//button[contains(text(), '4')])[1]")));
		guestOption.click();

		wait.until(ExpectedConditions.elementToBeClickable(applyGuestsButton)).click();

		System.out.println("Guests set to: 4");
		
		Thread.sleep(3000);
		
		ScreenShot.captureScreenshot(driver, "HotelPage");
	}

	public void clickSearch() throws InterruptedException {
		wait.until(ExpectedConditions.elementToBeClickable(searchButton)).click();
		Thread.sleep(5000);
	}
}
