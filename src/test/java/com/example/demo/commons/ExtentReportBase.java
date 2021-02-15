package com.example.demo.commons;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import lombok.Data;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.springframework.stereotype.Component;
import org.testng.ITestContext;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Component
@Data
public class ExtentReportBase {

    public WebDriver driver;
    String reportLocation = "Reports/" + resultPath + "/";
    protected static ExtentReports reports;
    protected static ExtentTest test;
    private static String resultPath = getResultPath();

    private static String getResultPath() {
        resultPath = "temp";

        if (!new File(resultPath).isDirectory()) {
            new File(resultPath);
        }

        return resultPath;
    }

    public static ExtentReports getReports() {
        return reports;
    }

    public static void setReports(ExtentReports reports) {
        ExtentReportBase.reports = reports;
    }

    public static ExtentTest getTest() {
        return test;
    }

    public static void setTest(ExtentTest test) {
        ExtentReportBase.test = test;
    }

    public String getScreenShotPath(String testcasename, WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File destinationFile = new File("Reports/screenshots/" + testcasename + ".png");
        String errflpath = destinationFile.getAbsolutePath();

        FileUtils.copyFile(source, destinationFile);
        return errflpath;
    }

    public void onTestStartExtent(ITestResult result) throws IOException, NoSuchFieldException, IllegalAccessException {

    }

    public void onTestSuccessExtent(ITestResult result) throws IOException, NoSuchFieldException, IllegalAccessException {

//        test.log(LogStatus.PASS, "Test passed");
//        test.log(LogStatus.PASS, result.getInstanceName());
    }

    public void onTestFailureExtent(ITestResult result) throws Exception {

        test.log(LogStatus.FAIL, result.getThrowable().getMessage());
        if(driver !=null){
            driver = (WebDriver) result.getTestClass().getRealClass().getDeclaredField("driver").get(result.getInstance());
            getScreenShotPath(result.getName() + "failed", driver);}
    }

    public void onTestSkippedExtent(ITestResult result) {
        test.log(LogStatus.SKIP, "Test skipped");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        // TODO Auto-generated method stub
    }

    public void onStartExtent(ITestContext context) {
        // Add appropriate date format
        reports = new ExtentReports(reportLocation + context.getSuite().getName() + " " + getDate() + ".html", false);

        test = reports.startTest(context.getName());
    }

    public void onFinishExtent(ITestContext context) {
        reports.endTest(test);
        reports.flush();
    }

    public String getDate() {

        DateTimeFormatter FOMATTER = DateTimeFormatter.ofPattern(("yyyy-MM-dd hh"));

        //Local date time instance
        LocalDateTime localDateTime = LocalDateTime.now();

        //Get formatted String
        String ldtString = FOMATTER.format(localDateTime);

        return ldtString;
    }
}
