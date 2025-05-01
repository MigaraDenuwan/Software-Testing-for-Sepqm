package edu.automationexercise.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class HomePage {
    private WebDriver driver;

    @FindBy(xpath = "//a[@href='/']")
    public WebElement homeLink;

    @FindBy(xpath = "//a[@href='/login']")
    private WebElement signupLoginButton;

    @FindBy(xpath = "//a[@href='/contact_us']")
    private WebElement contactUsButton;

    @FindBy(xpath = "//a[@href='/delete_account']")
    private WebElement deleteAccountButton;

    @FindBy(xpath = "//a[@href='/logout']")
    private WebElement logoutButton;

    @FindBy(xpath = "//a[contains(text(), 'Logged in as')]")
    public WebElement loggedInAs;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isHomePageVisible() {
        return homeLink.isDisplayed();
    }

    public void clickSignupLogin() {
        signupLoginButton.click();
    }

    public void clickContactUs() {
        contactUsButton.click();
    }

    public void clickDeleteAccount() {
        deleteAccountButton.click();
    }

    public void clickLogout() {
        logoutButton.click();
    }

    public String getLoggedInUsername() {
        return loggedInAs.getText();
    }
}