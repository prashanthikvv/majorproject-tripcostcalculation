package utilities;

import java.io.File;
import java.io.IOException;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenShot {

    public static String captureScreenshot(WebDriver driver, String screenshotName) {

        String path = System.getProperty("user.dir")
                + "/Reports/Screenshots/" + screenshotName + ".png";

        try {
            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            File destination = new File(path);

            FileUtils.copyFile(source, destination);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return path;
    }
}