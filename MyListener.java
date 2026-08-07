package tripCostReport;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class MyListener implements ITestListener {

    private static ExtentReports extent;
    public static ExtentTest test;

    @Override
    public void onStart(ITestContext context) {

        String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

        String reportName = "AutomationReport_" + timestamp + ".html";

        ExtentSparkReporter sparkReporter = new ExtentSparkReporter("./Reports/" + reportName);

        sparkReporter.config().setDocumentTitle("Automation Execution Report");

        sparkReporter.config().setReportName("Hotel & Cruise Validation");

        sparkReporter.config().setTheme(Theme.DARK);

        extent = new ExtentReports();

        extent.attachReporter(sparkReporter);

        //Meta data
        extent.setSystemInfo("Tester", "Team");
        extent.setSystemInfo("Environment", "QA");
        extent.setSystemInfo("Framework", "Selenium + Java + TestNG");
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version",
                System.getProperty("java.version"));
    }

    @Override
    public void onTestStart(ITestResult result) {

        test = extent.createTest(result.getMethod().getMethodName());

        test.log(Status.INFO,"Execution Started at : " + getCurrentTime());
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        test.log(Status.PASS,"Execution Completed at : " + getCurrentTime());
    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.log(Status.FAIL, "Execution Failed at : " + getCurrentTime());

        test.log(Status.FAIL, result.getThrowable());
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        test.log(Status.SKIP, "Test Skipped");

        if(result.getThrowable()!=null) {

            test.log(Status.INFO, result.getThrowable());
        }
    }

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();
    }

    public static String getCurrentTime() {

        return new SimpleDateFormat("hh:mm:ss a").format(new Date());
    }
}