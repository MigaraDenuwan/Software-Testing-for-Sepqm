package edu.automationexercise.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactUsPage {
    private WebDriver driver;

    @FindBy(xpath = "//h2[contains(text(), 'Get In Touch')]")
    public WebElement getInTouchText;

    @FindBy(name = "name")
    private WebElement nameInput;

    @FindBy(name = "email")
    private WebElement emailInput;

    @FindBy(name = "subject")
    private WebElement subjectInput;

    @FindBy(id = "message")
    private WebElement messageInput;

    @FindBy(name = "upload_file")
    private WebElement uploadFileInput;

    @FindBy(name = "submit")
    private WebElement submitButton;

    @FindBy(xpath = "//div[contains(text(), 'Success! Your details have been submitted successfully.')]")
    public WebElement successMessage;

    @FindBy(xpath = "//a[contains(text(), 'Home')]")
    private WebElement homeButton;

    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isGetInTouchVisible() {
        return getInTouchText.isDisplayed();
    }

    public void fillContactForm(String name, String email, String subject, String message) {
        nameInput.sendKeys(name);
        emailInput.sendKeys(email);
        subjectInput.sendKeys(subject);
        messageInput.sendKeys(message);
    }

    public void uploadFile(String filePath) {
        uploadFileInput.sendKeys(filePath);
    }

    public void clickSubmit() {
        submitButton.click();
    }

    public void acceptAlert() {
        driver.switchTo().alert().accept();
    }

    public boolean isSuccessMessageVisible() {
        return successMessage.isDisplayed();
    }

    public void clickHomeButton() {
        homeButton.click();
    }
}