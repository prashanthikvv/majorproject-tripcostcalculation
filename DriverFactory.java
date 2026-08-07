package utilities;

import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class DriverFactory {

	public static WebDriver getDriver() throws Exception {

		String browser = ConfigReader.getProperty("browser");

		WebDriver driver = null;

		if (browser.equalsIgnoreCase("chrome")) {

			ChromeOptions options = new ChromeOptions();

			options.addArguments("--disable-notifications"); // blocks notification popups
			options.addArguments("--disable-gpu");
			options.addArguments("--window-size=1920,1080");

			driver = new ChromeDriver(options);
		}
		driver.manage().window().maximize();
		return driver;
	}
}