package edu.automationexercise.tests;

import edu.automationexercise.pages.*;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class AutomationExerciseTests {
    private static final Logger logger = LogManager.getLogger(AutomationExerciseTests.class);
    protected WebDriver driver;
    private HomePage homePage;
    private LoginSignupPage loginSignupPage;
    private SignupFormPage signupFormPage;
    private AccountCreatedPage accountCreatedPage;
    private ContactUsPage contactUsPage;
    private String baseUrl = "https://automationexercise.com";
    private String uniqueEmail;
    private WebDriverWait wait;

    @BeforeMethod
    public void setUp() {
        logger.info("Setting up test environment");
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.get(baseUrl);
        homePage = new HomePage(driver);
        loginSignupPage = new LoginSignupPage(driver);
        signupFormPage = new SignupFormPage(driver, wait);
        accountCreatedPage = new AccountCreatedPage(driver);
        contactUsPage = new ContactUsPage(driver);
        uniqueEmail = "testuser" + System.currentTimeMillis() + "@example.com";
        logger.info("Navigated to: " + baseUrl);
    }

    public void captureScreenshot(String screenshotName) {
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

    @Test
    public void testRegisterUser() {
        logger.info("Starting testRegisterUser");

        // Verify home page
        wait.until(ExpectedConditions.visibilityOf(homePage.homeLink));
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible");
        logger.info("Home page verified");
        captureScreenshot("home_page");

        // Navigate to Signup/Login page
        homePage.clickSignupLogin();
        logger.info("Clicked Signup/Login button");

        // Verify 'New User Signup!' text
        wait.until(ExpectedConditions.visibilityOf(loginSignupPage.newUserSignupText));
        Assert.assertTrue(loginSignupPage.isNewUserSignupVisible(), "'New User Signup!' is not visible");
        logger.info("'New User Signup!' verified");
        captureScreenshot("new_user_signup");

        // Enter signup details
        loginSignupPage.enterSignupDetails("John Doe", uniqueEmail);
        logger.info("Entered signup details: Name=John Doe, Email=" + uniqueEmail);

        // Click Signup button
        loginSignupPage.clickSignup();
        logger.info("Clicked Signup button");

        // Verify signup form page
        wait.until(ExpectedConditions.urlContains("/signup"));
        wait.until(ExpectedConditions.visibilityOf(signupFormPage.nameInput));
        logger.info("Current URL after signup click: " + driver.getCurrentUrl());
        captureScreenshot("after_signup_click");

        // Fill account information
        signupFormPage.fillAccountInformation("Mr", "John Doe", uniqueEmail, "Password123", "1", "January", "1990");
        logger.info("Filled account information");

        // Select newsletter and offers
        signupFormPage.selectNewsLetter();
        logger.info("Selected newsletter checkbox");

        signupFormPage.selectOffers();
        logger.info("Selected offers checkbox");

        // Fill address information
        signupFormPage.fillAddressInformation("John", "Doe", "Test Company", "123 Main St", "Apt 4B",
                "United States", "California", "Los Angeles", "90001", "1234567890");
        logger.info("Filled address information");

        // Click Create Account
        signupFormPage.clickCreateAccount();
        logger.info("Clicked Create Account button");

        // Verify account created
        wait.until(ExpectedConditions.visibilityOf(accountCreatedPage.accountCreatedText));
        Assert.assertTrue(accountCreatedPage.isAccountCreatedVisible(), "'ACCOUNT CREATED!' is not visible");
        logger.info("'ACCOUNT CREATED!' verified");
        captureScreenshot("account_created");

        // Click Continue
        accountCreatedPage.clickContinue();
        logger.info("Clicked Continue button");

        // Verify logged-in state
        wait.until(ExpectedConditions.visibilityOf(homePage.loggedInAs));
        String loggedInText = homePage.getLoggedInUsername();
        Assert.assertTrue(loggedInText.contains("John Doe"), "'Logged in as John Doe' is not visible");
        logger.info("Logged in as: " + loggedInText);
        captureScreenshot("logged_in");

        // Delete account
        homePage.clickDeleteAccount();
        logger.info("Clicked Delete Account button");

        // Verify account deleted
        wait.until(ExpectedConditions.visibilityOf(accountCreatedPage.accountDeletedText));
        Assert.assertTrue(accountCreatedPage.isAccountDeletedVisible(), "'ACCOUNT DELETED!' is not visible");
        logger.info("'ACCOUNT DELETED!' verified");
        captureScreenshot("account_deleted");

        // Click Continue
        accountCreatedPage.clickContinue();
        logger.info("Clicked Continue button after account deletion");
    }

    @Test
    public void testRegisterUserEmailFailure() {
        logger.info("Starting testRegisterUserEmailFailure");

        wait.until(ExpectedConditions.visibilityOf(homePage.homeLink));
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible");
        logger.info("Home page verified");
        captureScreenshot("home_page");

        homePage.clickSignupLogin();
        logger.info("Clicked Signup/Login button");

        wait.until(ExpectedConditions.visibilityOf(loginSignupPage.newUserSignupText));
        Assert.assertTrue(loginSignupPage.isNewUserSignupVisible(), "'New User Signup!' is not visible");
        logger.info("'New User Signup!' verified");
        captureScreenshot("new_user_signup");

        String existingEmail = "ddd@gmail.com";
        loginSignupPage.enterSignupDetails("Jane Doe", existingEmail);
        logger.info("Entered signup details: Name=Jane Doe, Email=" + existingEmail);

        loginSignupPage.clickSignup();
        logger.info("Clicked Signup button");
        try {
            wait.until(ExpectedConditions.visibilityOf(accountCreatedPage.accountCreatedText));
            Assert.fail("Account was created with existing email! Expected 'Email Address already exist!' error");
        } catch (TimeoutException e) {
            wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[contains(text(), 'Email Address already exist!')]")));
            String errorMessage = String.valueOf(loginSignupPage.isEmailExistsErrorVisible());
            Assert.assertTrue(errorMessage.contains("Email Address already exist!"), "Expected error message not displayed");
            logger.info("Verified expected error message: " + errorMessage);
            captureScreenshot("expected_existing_email_error");
        }
        Assert.assertTrue(driver.getCurrentUrl().contains("/signup"), "User should remain on signup page");
        logger.info("User remained on signup page");
    }

    @Test
    public void testContactUsForm() {
        logger.info("Starting testContactUsForm");

        wait.until(ExpectedConditions.visibilityOf(homePage.homeLink));
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible");
        logger.info("Home page verified");
        captureScreenshot("home_page");

        homePage.clickContactUs();
        logger.info("Clicked Contact Us button");

        wait.until(ExpectedConditions.visibilityOf(contactUsPage.getInTouchText));
        Assert.assertTrue(contactUsPage.isGetInTouchVisible(), "'GET IN TOUCH' is not visible");
        logger.info("'GET IN TOUCH' verified");
        captureScreenshot("get_in_touch");

        contactUsPage.fillContactForm("John Doe", "test@example.com", "Test Subject", "This is a test message");
        logger.info("Filled contact form");

        String filePath = new File("src/test/resources/testfile.txt").getAbsolutePath();
        contactUsPage.uploadFile(filePath);
        logger.info("Uploaded file: " + filePath);

        contactUsPage.clickSubmit();
        logger.info("Clicked Submit button");

        wait.until(ExpectedConditions.alertIsPresent());
        contactUsPage.acceptAlert();
        logger.info("Accepted alert");

        wait.until(ExpectedConditions.visibilityOf(contactUsPage.successMessage));
        Assert.assertTrue(contactUsPage.isSuccessMessageVisible(), "'Success! Your details have been submitted successfully.' is not visible");
        logger.info("'Success! Your details have been submitted successfully.' verified");
        captureScreenshot("contact_form_success");

        contactUsPage.clickHomeButton();
        wait.until(ExpectedConditions.visibilityOf(homePage.homeLink));
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible after clicking Home button");
        logger.info("Home page verified after clicking Home button");
        captureScreenshot("home_page_after_contact");
    }


    @Test
    public void testLoginUserWithCorrectCredentials() {
        logger.info("Starting testLoginUserWithCorrectCredentials");

        // Wait until the home page is visible
        wait.until(ExpectedConditions.visibilityOf(homePage.homeLink));
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible");
        captureScreenshot("home_page");

        // Navigate to the Login/Signup page
        homePage.clickSignupLogin();

        // Wait until the 'Login to your account' text is visible
        wait.until(ExpectedConditions.visibilityOf(loginSignupPage.loginToAccountText));
        Assert.assertTrue(loginSignupPage.isLoginToAccountVisible(), "'Login to your account' is not visible");
        captureScreenshot("login_to_account");

        // Enter login details and click the login button
        loginSignupPage.enterLoginDetails("arunalu2025@gmail.com", "1234");
        loginSignupPage.clickLogin();

        // Wait until the 'Logged in as' text is visible
        wait.until(ExpectedConditions.visibilityOf(homePage.loggedInAs));

        // Get the logged-in text
        String loggedInText = homePage.getLoggedInUsername();

        // Verify if the correct user is logged in by checking the full name or a portion of it
        Assert.assertTrue(loggedInText.contains("Arunalu"), "'Logged in as Arunalu Bamunusinghe' is not visible");
        captureScreenshot("logged_in");
    }

    @Test
    public void testLoginUserWithIncorrectCredentials() {
        logger.info("Starting testLoginUserWithIncorrectCredentials");

        wait.until(ExpectedConditions.visibilityOf(homePage.homeLink));
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page is not visible");
        captureScreenshot("home_page");

        homePage.clickSignupLogin();

        wait.until(ExpectedConditions.visibilityOf(loginSignupPage.loginToAccountText));
        Assert.assertTrue(loginSignupPage.isLoginToAccountVisible(), "'Login to your account' is not visible");
        captureScreenshot("login_to_account");

        loginSignupPage.enterLoginDetails("arunalu2025@gmail.com", "1234");
        loginSignupPage.clickLogin();

        wait.until(ExpectedConditions.visibilityOf(homePage.loggedInAs));
        String loggedInText = homePage.getLoggedInUsername();
        Assert.assertTrue(loggedInText.contains("Jane Doe"), "'Logged in as Jane Doe' is not visible");
        captureScreenshot("incorrect_logged_in");
    }
    @AfterMethod
    public void tearDown() {
        logger.info("Tearing down test environment");
        if (driver != null) {
            driver.quit();
            logger.info("WebDriver closed");
        }
    }
}