package base;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import utilities.ConfigReader;
import utilities.DriverFactory;

public class BaseTest {

	protected WebDriver driver;

	@BeforeClass
	public void setup() throws Exception {
		driver = DriverFactory.getDriver();
		String url = ConfigReader.getProperty("url");
		driver.get(url);
	}

	@AfterClass
	public void tearDown() {

		driver.quit();

	}

	public WebDriver getDriver() {

		return driver;

	}

}
