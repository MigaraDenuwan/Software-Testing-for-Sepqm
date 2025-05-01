package edu.automationexercise.utils;

import edu.automationexercise.pages.SignupFormPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

public class BasePage {
    protected WebDriver driver;
    private static final Logger logger = LogManager.getLogger(SignupFormPage.class);
    public BasePage(WebDriver driver) {
        this.driver = driver;
        hideAds();
    }

    private void hideAds() {
        try {
            ((JavascriptExecutor) driver).executeScript(
                    "var ads = document.querySelectorAll('div[id*=\"aswift\"], iframe[id*=\"aswift\"]');" +
                            "ads.forEach(ad => ad.style.display = 'none');"
            );
            logger.info("Hid ad elements");
        } catch (Exception e) {
            logger.warn("Failed to hide ads: " + e.getMessage());
        }
    }
}