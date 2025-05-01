package edu.automationexercise.utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TestListener implements ITestListener {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("Test Failed: " + result.getName());
        Object testInstance = result.getInstance();
        WebDriver driver = null;
        try {
            driver = (WebDriver) testInstance.getClass().getDeclaredField("driver").get(testInstance);
            captureScreenshot(driver, result.getName() + "_failure");
        } catch (Exception e) {
            logger.error("Failed to capture screenshot on test failure: " + e.getMessage());
        }
    }

    private void captureScreenshot(WebDriver driver, String screenshotName) {
        if (driver == null) return;
        String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String fileName = "screenshots/" + screenshotName + "_" + timestamp + ".png";
        File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        try {
            Files.createDirectories(Paths.get("screenshots"));
            Files.copy(screenshot.toPath(), Paths.get(fileName));
            logger.info("Screenshot saved: " + fileName);
        } catch (IOException e) {
            logger.error("Failed to save screenshot: " + e.getMessage());
        }
    }
}