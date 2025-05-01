package edu.automationexercise.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginSignupPage {
    private WebDriver driver;

    @FindBy(xpath = "//h2[contains(text(), 'New User Signup!')]")
    public WebElement newUserSignupText;

    @FindBy(xpath = "//h2[contains(text(), 'Login to your account')]")
    public WebElement loginToAccountText;

    @FindBy(name = "name")
    private WebElement signupNameInput;

    @FindBy(xpath = "//input[@data-qa='signup-email']")
    private WebElement signupEmailInput;

    @FindBy(xpath = "//button[@data-qa='signup-button']")
    private WebElement signupButton;

    @FindBy(xpath = "//input[@data-qa='login-email']")
    private WebElement loginEmailInput;

    @FindBy(xpath = "//input[@data-qa='login-password']")
    private WebElement loginPasswordInput;

    @FindBy(xpath = "//button[@data-qa='login-button']")
    private WebElement loginButton;

    @FindBy(xpath = "//p[contains(text(), 'Email Address already exist!')]")
    public WebElement emailExistsError;

    @FindBy(xpath = "//p[contains(text(), 'Your email or password is incorrect!')]")
    public WebElement incorrectLoginError;

    public LoginSignupPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isNewUserSignupVisible() {
        return newUserSignupText.isDisplayed();
    }

    public boolean isLoginToAccountVisible() {
        return loginToAccountText.isDisplayed();
    }

    public void enterSignupDetails(String name, String email) {
        signupNameInput.sendKeys(name);
        signupEmailInput.sendKeys(email);
    }

    public void clickSignup() {
        signupButton.click();
    }

    public void enterLoginDetails(String email, String password) {
        loginEmailInput.sendKeys(email);
        loginPasswordInput.sendKeys(password);
    }

    public void clickLogin() {
        loginButton.click();
    }

    public boolean isEmailExistsErrorVisible() {
        return emailExistsError.isDisplayed();
    }

    public boolean isIncorrectLoginErrorVisible() {
        return incorrectLoginError.isDisplayed();
    }
}