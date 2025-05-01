//package edu.automationexercise.pages;
//
//import edu.automationexercise.utils.BasePage;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.support.FindBy;
//import org.openqa.selenium.support.PageFactory;
//import org.openqa.selenium.support.ui.ExpectedConditions;
//import org.openqa.selenium.support.ui.Select;
//import org.openqa.selenium.support.ui.WebDriverWait;
//import org.openqa.selenium.JavascriptExecutor;
//
//public class SignupFormPage extends BasePage {
//    private static final Logger logger = LogManager.getLogger(SignupFormPage.class);
//    private WebDriverWait wait;
//
//    @FindBy(xpath = "//h2[contains(text(), 'ENTER ACCOUNT INFORMATION')]")
//    public WebElement enterAccountInfoText;
//
//    @FindBy(id = "id_gender1")
//    private WebElement titleMr;
//
//    @FindBy(id = "name")
//    public WebElement nameInput;
//
//    @FindBy(id = "email")
//    private WebElement emailInput;
//
//    @FindBy(id = "password")
//    private WebElement passwordInput;
//
//    @FindBy(id = "days")
//    private WebElement dobDay;
//
//    @FindBy(id = "months")
//    private WebElement dobMonth;
//
//    @FindBy(id = "years")
//    private WebElement dobYear;
//
//    @FindBy(id = "newsletter")
//    private WebElement newsletterCheckbox;
//
//    @FindBy(id = "optin")
//    private WebElement offersCheckbox;
//
//    @FindBy(id = "first_name")
//    private WebElement firstNameInput;
//
//    @FindBy(id = "last_name")
//    private WebElement lastNameInput;
//
//    @FindBy(id = "company")
//    private WebElement companyInput;
//
//    @FindBy(id = "address1")
//    private WebElement address1Input;
//
//    @FindBy(id = "address2")
//    private WebElement address2Input;
//
//    @FindBy(id = "country")
//    private WebElement countryDropdown;
//
//    @FindBy(id = "state")
//    private WebElement stateInput;
//
//    @FindBy(id = "city")
//    private WebElement cityInput;
//
//    @FindBy(id = "zipcode")
//    private WebElement zipcodeInput;
//
//    @FindBy(id = "mobile_number")
//    private WebElement mobileNumberInput;
//
//    @FindBy(xpath = "//button[@data-qa='create-account']")
//    private WebElement createAccountButton;
//
//    public SignupFormPage(WebDriver driver) {
//        super(driver);
//        this.wait = wait;
//        PageFactory.initElements(driver, this);
//    }
//
//    public boolean isEnterAccountInfoVisible() {
//        try {
//            return enterAccountInfoText.isDisplayed();
//        } catch (org.openqa.selenium.NoSuchElementException e) {
//            logger.error("Element not found: " + e.getMessage());
//            logger.info("Current page source: " + driver.getPageSource().substring(0, 1000));
//            return false;
//        }
//    }
//
//    public void fillAccountInformation(String title, String name, String email, String password, String day, String month, String year) {
//        if (title.equalsIgnoreCase("Mr")) {
//            wait.until(ExpectedConditions.elementToBeClickable(titleMr)).click();
//            logger.info("Selected title: Mr");
//        }
//        if (!nameInput.getAttribute("value").isEmpty() && !nameInput.getAttribute("value").equals(name)) {
//            nameInput.clear();
//        }
//        nameInput.sendKeys(name);
//        logger.info("Filled name: " + name);
//
//        if (!emailInput.isEnabled()) {
//            logger.info("Email field is pre-filled and disabled, skipping input");
//        } else if (!emailInput.getAttribute("value").equals(email)) {
//            emailInput.sendKeys(email);
//            logger.info("Filled email: " + email);
//        }
//
//        passwordInput.sendKeys(password);
//        logger.info("Filled password");
//
//        try {
//            new Select(dobDay).selectByValue(day);
//            logger.info("Selected day: " + day);
//        } catch (Exception e) {
//            logger.error("Failed to select day: " + e.getMessage());
//        }
//
//        try {
//            new Select(dobMonth).selectByVisibleText(month);
//            logger.info("Selected month: " + month);
//        } catch (Exception e) {
//            logger.error("Failed to select month: " + e.getMessage());
//        }
//
//        try {
//            new Select(dobYear).selectByValue(year);
//            logger.info("Selected year: " + year);
//        } catch (Exception e) {
//            logger.error("Failed to select year: " + e.getMessage());
//        }
//    }
//
//    public void selectNewsLetter() {
//        try {
//            logger.info("Attempting to click newsletter checkbox. Is displayed: " + newsletterCheckbox.isDisplayed() + ", Is enabled: " + newsletterCheckbox.isEnabled());
//            wait.until(ExpectedConditions.elementToBeClickable(newsletterCheckbox)).click();
//            logger.info("Selected newsletter checkbox");
//        } catch (Exception e) {
//            logger.warn("Failed to click newsletter checkbox, attempting JavaScript click: " + e.getMessage());
//            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", newsletterCheckbox);
//            logger.info("Selected newsletter checkbox via JavaScript");
//        }
//    }
//
//    public void selectOffers() {
//        try {
//            logger.info("Attempting to click offers checkbox. Is displayed: " + offersCheckbox.isDisplayed() + ", Is enabled: " + offersCheckbox.isEnabled());
//            wait.until(ExpectedConditions.elementToBeClickable(offersCheckbox)).click();
//            logger.info("Selected offers checkbox");
//        } catch (Exception e) {
//            logger.warn("Failed to click offers checkbox, attempting JavaScript click: " + e.getMessage());
//            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", offersCheckbox);
//            logger.info("Selected offers checkbox via JavaScript");
//        }
//    }
//
//    public void fillAddressInformation(String firstName, String lastName, String company, String address1, String address2,
//                                       String country, String state, String city, String zipcode, String mobile) {
//        firstNameInput.sendKeys(firstName);
//        lastNameInput.sendKeys(lastName);
//        companyInput.sendKeys(company);
//        address1Input.sendKeys(address1);
//        address2Input.sendKeys(address2);
//        new Select(countryDropdown).selectByVisibleText(country);
//        stateInput.sendKeys(state);
//        cityInput.sendKeys(city);
//        zipcodeInput.sendKeys(zipcode);
//        mobileNumberInput.sendKeys(mobile);
//        logger.info("Filled address information");
//    }
//
//    public void clickCreateAccount() {
//        wait.until(ExpectedConditions.elementToBeClickable(createAccountButton)).click();
//        logger.info("Clicked Create Account button");
//    }
//}

package edu.automationexercise.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SignupFormPage {
    private static final Logger logger = LogManager.getLogger(SignupFormPage.class);
    private WebDriver driver;
    private WebDriverWait wait;

    @FindBy(xpath = "//h2[contains(text(), 'ENTER ACCOUNT INFORMATION')]")
    public WebElement enterAccountInfoText;

    @FindBy(id = "id_gender1")
    private WebElement titleMr;

    @FindBy(id = "name")
    public WebElement nameInput;

    @FindBy(id = "email")
    private WebElement emailInput;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "days")
    private WebElement dobDay;

    @FindBy(id = "months")
    private WebElement dobMonth;

    @FindBy(id = "years")
    private WebElement dobYear;

    @FindBy(id = "newsletter")
    private WebElement newsletterCheckbox;

    @FindBy(id = "optin")
    private WebElement offersCheckbox;

    @FindBy(id = "first_name")
    private WebElement firstNameInput;

    @FindBy(id = "last_name")
    private WebElement lastNameInput;

    @FindBy(id = "company")
    private WebElement companyInput;

    @FindBy(id = "address1")
    private WebElement address1Input;

    @FindBy(id = "address2")
    private WebElement address2Input;

    @FindBy(id = "country")
    private WebElement countryDropdown;

    @FindBy(id = "state")
    private WebElement stateInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "zipcode")
    private WebElement zipcodeInput;

    @FindBy(id = "mobile_number")
    private WebElement mobileNumberInput;

    @FindBy(xpath = "//button[@data-qa='create-account']")
    private WebElement createAccountButton;

    public SignupFormPage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
        PageFactory.initElements(driver, this);
    }

    public boolean isEnterAccountInfoVisible() {
        try {
            wait.until(ExpectedConditions.visibilityOf(enterAccountInfoText));
            return enterAccountInfoText.isDisplayed();
        } catch (NoSuchElementException | TimeoutException e) {
            logger.error("Element not found: " + e.getMessage());
            logger.info("Current page source: " + driver.getPageSource().substring(0, 1000));
            captureScreenshot("enter_account_info_error");
            return false;
        }
    }

    public void fillAccountInformation(String title, String name, String email, String password, String day, String month, String year) {
        // Select title
        if (title.equalsIgnoreCase("Mr")) {
            wait.until(ExpectedConditions.elementToBeClickable(titleMr));
            titleMr.click();
            logger.info("Selected title: Mr");
        }

        // Fill name
        wait.until(ExpectedConditions.visibilityOf(nameInput));
        if (!nameInput.getAttribute("value").isEmpty() && !nameInput.getAttribute("value").equals(name)) {
            nameInput.clear();
        }
        nameInput.sendKeys(name);
        logger.info("Filled name: " + name);

        // Handle email (pre-filled and disabled)
        wait.until(ExpectedConditions.visibilityOf(emailInput));
        if (!emailInput.isEnabled()) {
            logger.info("Email field is pre-filled and disabled, skipping input");
        } else if (!emailInput.getAttribute("value").equals(email)) {
            emailInput.clear();
            emailInput.sendKeys(email);
            logger.info("Filled email: " + email);
        }

        // Fill password
        wait.until(ExpectedConditions.visibilityOf(passwordInput));
        passwordInput.sendKeys(password);
        logger.info("Filled password");

        // Handle Date of Birth
        try {
            wait.until(ExpectedConditions.elementToBeClickable(dobDay));
            new Select(dobDay).selectByValue(day);
            logger.info("Selected day: " + day);
        } catch (Exception e) {
            logger.error("Failed to select day: " + e.getMessage());
        }

        try {
            wait.until(ExpectedConditions.elementToBeClickable(dobMonth));
            new Select(dobMonth).selectByVisibleText(month);
            logger.info("Selected month: " + month);
        } catch (Exception e) {
            logger.error("Failed to select month: " + e.getMessage());
        }

        try {
            wait.until(ExpectedConditions.elementToBeClickable(dobYear));
            new Select(dobYear).selectByValue(year);
            logger.info("Selected year: " + year);
        } catch (Exception e) {
            logger.error("Failed to select year: " + e.getMessage());
        }
    }

    public void selectNewsLetter() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(newsletterCheckbox));
            // Scroll into view to avoid ad overlays
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", newsletterCheckbox);
            // Use JavaScript click to bypass interception
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", newsletterCheckbox);
            logger.info("Selected newsletter checkbox");
        } catch (Exception e) {
            logger.error("Failed to select newsletter checkbox: " + e.getMessage());
            captureScreenshot("newsletter_error");
        }
    }

    public void selectOffers() {
        try {
            wait.until(ExpectedConditions.elementToBeClickable(offersCheckbox));
            // Scroll into view
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", offersCheckbox);
            // Use JavaScript click
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", offersCheckbox);
            logger.info("Selected offers checkbox");
        } catch (Exception e) {
            logger.error("Failed to select offers checkbox: " + e.getMessage());
            captureScreenshot("offers_error");
        }
    }

    public void fillAddressInformation(String firstName, String lastName, String company, String address1, String address2,
                                       String country, String state, String city, String zipcode, String mobile) {
        wait.until(ExpectedConditions.visibilityOf(firstNameInput));
        firstNameInput.sendKeys(firstName);
        lastNameInput.sendKeys(lastName);
        companyInput.sendKeys(company);
        address1Input.sendKeys(address1);
        address2Input.sendKeys(address2);
        new Select(countryDropdown).selectByVisibleText(country);
        stateInput.sendKeys(state);
        cityInput.sendKeys(city);
        zipcodeInput.sendKeys(zipcode);
        mobileNumberInput.sendKeys(mobile);
        logger.info("Filled address information");
    }

    public void clickCreateAccount() {
        wait.until(ExpectedConditions.elementToBeClickable(createAccountButton));
        createAccountButton.click();
        logger.info("Clicked Create Account button");
    }

    // Helper method to capture screenshot (if not available elsewhere)
    private void captureScreenshot(String screenshotName) {
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