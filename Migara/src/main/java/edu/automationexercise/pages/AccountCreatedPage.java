package edu.automationexercise.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountCreatedPage {
    private WebDriver driver;

    @FindBy(xpath = "//h2[@data-qa='account-created']")
    public WebElement accountCreatedText;

    @FindBy(xpath = "//a[@data-qa='continue-button']")
    private WebElement continueButton;

    @FindBy(xpath = "//h2[@data-qa='account-deleted']")
    public WebElement accountDeletedText;

    public AccountCreatedPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isAccountCreatedVisible() {
        return accountCreatedText.isDisplayed();
    }

    public void clickContinue() {
        continueButton.click();
    }

    public boolean isAccountDeletedVisible() {
        return accountDeletedText.isDisplayed();
    }
}