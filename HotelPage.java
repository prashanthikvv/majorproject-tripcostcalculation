package pageObjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HotelPage extends BasePage {

    public HotelPage(WebDriver driver) {
        super(driver);
    }

    // Locators with PageFactory
    @FindBy(id = "st-chipCheckbox-5")
    private WebElement highestRatingButton;

    @FindBy(xpath = "//div[@class='HotelListCard_hotelCard__0QC9d']")
    private List<WebElement> hotelsList;

    @FindBy(id = "webpush-onsite")
    private List<WebElement> webPushIframe;

    @FindBy(xpath = "//button[contains(text(),'Later')]")
    private List<WebElement> dismissButtons;

    // Methods
    public void sortByHighestRating() throws InterruptedException {
        // Handle iframe popup if present
        if (!webPushIframe.isEmpty()) {
            driver.switchTo().frame(webPushIframe.get(0));
            if (!dismissButtons.isEmpty()) {
                dismissButtons.get(0).click();
            }
            driver.switchTo().defaultContent();
        }
        
     // Scroll to the button before clicking
        JavascriptExecutor jsExecutor = (JavascriptExecutor)driver;
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", highestRatingButton);

        highestRatingButton.click();
        Thread.sleep(3000);
    }

    public List<Map<String, String>> getDetailsOfTopHotels() {
        List<Map<String, String>> hotelData = new ArrayList<>();

        for (int i = 0; i < hotelsList.size(); i++) {
            WebElement hotelCard = hotelsList.get(i);

            // Hotel name
            String name = hotelCard.findElement(org.openqa.selenium.By.xpath(".//div[@class='HotelListCard_hotelInfo___TpZO']//h2")).getText();

            // Price per night
            String price = hotelCard.findElement(org.openqa.selenium.By.xpath(".//div[@class='HotelListCard_pricing__7CCDe']//h2")).getText();

            // Taxes
            String tax = hotelCard.findElement(org.openqa.selenium.By.xpath(".//div[@class='HotelListCard_pricing__7CCDe']//span[contains(@class,'HotelListCard_taxes__e3rol')][1]")).getText();

            // Convert price and tax to numbers
            int priceValue = Integer.parseInt(price.replaceAll("[^0-9]", ""));
            int taxValue = Integer.parseInt(tax.replaceAll("[^0-9]", ""));
            int totalPrice = priceValue + taxValue;

            // Print to console
            System.out.println("Hotel " + (i + 1));
            System.out.println("Name: " + name);
            System.out.println("Price per night: " + price);
            System.out.println("Taxes: " + tax);
            System.out.println("Total Price: ₹ " + String.format("%,d", totalPrice));
            System.out.println("----------------------------");

            // Store in map for Excel
            Map<String, String> hotelMap = new HashMap<>();
            hotelMap.put("name", name);
            hotelMap.put("pricePerNight", price);
            hotelMap.put("taxes", tax);
            hotelMap.put("totalPrice", "₹ " + String.format("%,d", totalPrice));

            hotelData.add(hotelMap);

            // Stop after top 3 hotels
            if (i == 2) break;
        }

        return hotelData;
    }
}
