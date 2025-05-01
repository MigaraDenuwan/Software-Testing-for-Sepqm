package edu.automationexercise.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class AccountDeletedPage {
    private WebDriver driver;

    @FindBy(xpath = "//a[contains(text(),'Continue')]")
    private WebElement continueButton;

    public AccountDeletedPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void continueButtonClick() {
        continueButton.click();
    }
}