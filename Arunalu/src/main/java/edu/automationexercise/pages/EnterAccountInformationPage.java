package edu.automationexercise.pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.*;

import java.time.Duration;

public class EnterAccountInformationPage {

    private WebDriver driver;

    @FindBy(xpath = "//h2[contains(.,'Enter Account Information')]")
    private WebElement enterAccountInformation;

    @FindBy(id = "id_gender1")
    private WebElement genderMrRadioButton;

    @FindBy(id = "password")
    private WebElement passwordInput;

    @FindBy(id = "days")
    private WebElement daysDropdown;

    @FindBy(id = "months")
    private WebElement monthsDropdown;

    @FindBy(id = "years")
    private WebElement yearsDropdown;

    @FindBy(id = "newsletter")
    private WebElement newsletterCheckbox;

    @FindBy(id = "optin")
    private WebElement specialOffersCheckbox;

    @FindBy(id = "first_name")
    private WebElement firstNameInput;

    @FindBy(id = "last_name")
    private WebElement lastNameInput;

    @FindBy(id = "address1")
    private WebElement addressInput;

    @FindBy(id = "state")
    private WebElement stateInput;

    @FindBy(id = "city")
    private WebElement cityInput;

    @FindBy(id = "zipcode")
    private WebElement zipCodeInput;

    @FindBy(id = "mobile_number")
    private WebElement mobileNumberInput;

    @FindBy(xpath = "//button[contains(text(),'Create Account')]")
    private WebElement createAccountButton;

    @FindBy(xpath = "//h2[contains(text(),'Account Created')]")
    private WebElement accountCreated;

    public EnterAccountInformationPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public WebElement getEnterAccountInformation() {
        return enterAccountInformation;
    }

    public String fillAccountDetailsAndGetSuccessMessage() {
        genderMrRadioButton.click();
        passwordInput.sendKeys("Test@1234");
        daysDropdown.sendKeys("10");
        monthsDropdown.sendKeys("May");
        yearsDropdown.sendKeys("1990");
        newsletterCheckbox.click();
        specialOffersCheckbox.click();
        firstNameInput.sendKeys("John");
        lastNameInput.sendKeys("Doe");
        addressInput.sendKeys("123 Test Street");
        stateInput.sendKeys("Test State");
        cityInput.sendKeys("Test City");
        zipCodeInput.sendKeys("12345");
        mobileNumberInput.sendKeys("1234567890");
        createAccountButton.click();

        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement successMsg = wait.until(ExpectedConditions.visibilityOf(accountCreated));
        return successMsg.getText();
    }
}