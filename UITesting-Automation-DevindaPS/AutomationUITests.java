import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;

public class AutomationUITests {
    private static final Logger logger = LoggerFactory.getLogger(AutomationUITests.class);
    private WebDriver driver;
    private WebDriverWait wait;
    private final String BASE_URL = "https://automationexercise.com";

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--no-sandbox", "--disable-dev-shm-usage", "--start-maximized",
                "--disable-extensions", "--remote-allow-origins=*", "--disable-gpu",
                "--ignore-certificate-errors", "--disable-popup-blocking");

        driver = new ChromeDriver(options);
        wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        logger.info("Browser initialized successfully");
    }

    @AfterMethod
    public void tearDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Browser closed");
        }
    }

    @Test
    public void testHomePageVisibility() throws IOException {
        try {
            logger.info("Navigating to home page");
            driver.get(BASE_URL);

            // Verify page title
            Assert.assertTrue(driver.getTitle().contains("Automation Exercise"),
                    "Home page title verification failed");

            // Verify header elements
            verifyElementPresence(By.cssSelector("header[id='header']"), "Header section");
            verifyElementPresence(By.cssSelector("a[href='/products']"), "Products link");

            // Verify recommended items
            List<WebElement> featuresItems = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.cssSelector(".features_items .productinfo ")
                    )
            );
            Assert.assertTrue(featuresItems.size() > 3, "featuresItems items not displayed");

            logger.info("Home page verification successful");

        } catch (Exception e) {
            captureScreenshot("homepage-error");
            throw e;
        }
    }

    @Test
    public void testUserRegistration() throws IOException {
        try {
            driver.get(BASE_URL + "/login");

            // Fill registration form
            sendKeysWithRetry(By.name("name"), "Test User");
            sendKeysWithRetry(By.cssSelector("input[data-qa='signup-email']"),
                    "testuser_" + System.currentTimeMillis() + "@example.com");
            clickWithRetry(By.cssSelector("button[data-qa='signup-button']"));

            // Fill account information
            wait.until(ExpectedConditions.urlContains("/signup"));
            selectDropdown(By.id("days"), "15");
            selectDropdown(By.id("months"), "June");
            selectDropdown(By.id("years"), "1990");
            clickWithRetry(By.id("newsletter"));
            clickWithRetry(By.id("optin"));

            // Complete registration
            sendKeysWithRetry(By.id("password"), "SecurePassword123!");
            sendKeysWithRetry(By.id("first_name"), "John");
            sendKeysWithRetry(By.id("last_name"), "Doe");
            sendKeysWithRetry(By.id("address1"), "123 Main St");
            selectDropdown(By.id("country"), "United States");
            sendKeysWithRetry(By.id("state"), "California");
            sendKeysWithRetry(By.id("city"), "Los Angeles");
            sendKeysWithRetry(By.id("zipcode"), "90001");
            sendKeysWithRetry(By.id("mobile_number"), "555-123-4567");
            clickWithRetry(By.cssSelector("button[data-qa='create-account']"));

            // Verify success message
            WebElement successMessage = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("h2[data-qa='account-created']")
                    )
            );
            Assert.assertTrue(successMessage.getText().contains("ACCOUNT CREATED!"),
                    "Account creation success message not displayed");

        } catch (Exception e) {
            captureScreenshot("registration-error");
            throw e;
        }
    }

    @Test
    public void testAddRemoveItemsToCart() throws IOException {
        try {
            driver.get(BASE_URL + "/product_details/1");

            // Add to cart
            clickWithRetry(By.cssSelector("button[type='button']"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".modal-content")
            ));

            // Using both href and text content
            clickWithRetry(By.xpath("//a[contains(@href,'view_cart') and contains(.,'View Cart')]"));

            // Verify cart items
            List<WebElement> cartItems = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.cssSelector("#cart_info_table tbody tr")
                    )
            );
            Assert.assertTrue(cartItems.size() > 0, "Cart is empty after adding product");

            // Remove item
            clickWithRetry(By.cssSelector(".cart_quantity_delete"));
            wait.until(ExpectedConditions.invisibilityOf(cartItems.get(0)));

            // Verify empty cart
            WebElement emptyCart = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector("#empty_cart p")
                    )
            );
            Assert.assertTrue(emptyCart.getText().contains("Cart is empty"),
                    "Cart not empty after removal");

        } catch (Exception e) {
            captureScreenshot("cart-error");
            throw e;
        }
    }

    @Test
    public void testRegisterUserWithExistingEmail() throws IOException {
        try {
            // Step 1-3: Launch browser and verify home page (handled in @BeforeMethod)
            logger.info("Navigating to home page");
            driver.get(BASE_URL);
            Assert.assertTrue(driver.getTitle().contains("Automation Exercise"),
                    "Home page not visible");

            // Step 4: Click on Signup/Login
            clickWithRetry(By.cssSelector("a[href='/login']"));

            // Step 5: Verify New User Signup
            WebElement signupHeader = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".signup-form h2")
                    )
            );
            Assert.assertTrue(signupHeader.getText().contains("New User Signup!"),
                    "New User Signup section not visible");

            // Step 6: Enter existing credentials
            String existingEmail = "existing@example.com"; // Pre-registered test email
            sendKeysWithRetry(By.name("name"), "Existing User");
            sendKeysWithRetry(By.cssSelector("input[data-qa='signup-email']"), existingEmail);

            // Step 7: Click Signup
            clickWithRetry(By.cssSelector("button[data-qa='signup-button']"));

            // Step 8: Verify error message
            WebElement errorMessage = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".signup-form p")
                    )
            );
            Assert.assertTrue(errorMessage.getText().contains("Email Address already exist!"),
                    "Existing email error not displayed");

        } catch (Exception e) {
            captureScreenshot("existing-email-error");
            throw e;
        }
    }

    @Test
    public void testContactUsForm() throws IOException {
        try {
            // Step 1-3: Launch browser and verify home page
            logger.info("Navigating to home page");
            driver.get(BASE_URL);
            Assert.assertTrue(driver.getTitle().contains("Automation Exercise"),
                    "Home page not visible");

            // Step 4: Click Contact Us
            clickWithRetry(By.cssSelector("a[href='/contact_us']"));

            // Step 5: Verify GET IN TOUCH
            WebElement contactHeader = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".contact-form h2.title")
                    )
            );
            Assert.assertTrue(contactHeader.getText().contains("GET IN TOUCH"),
                    "Contact Us header not visible");

            // Step 6: Fill form
            sendKeysWithRetry(By.name("name"), "Test User");
            sendKeysWithRetry(By.name("email"), "testuser@example.com");
            sendKeysWithRetry(By.name("subject"), "Test Inquiry");
            sendKeysWithRetry(By.name("message"), "This is a test message for contact form");

            // Step 7: Upload file
            WebElement fileInput = driver.findElement(By.name("upload_file"));
            fileInput.sendKeys(new File("src/test/resources/testfile.txt").getAbsolutePath());

            // Step 8: Submit form
            clickWithRetry(By.name("submit"));

            // Step 9: Handle alert
            try {
                Alert alert = wait.until(ExpectedConditions.alertIsPresent());
                alert.accept();
            } catch (Exception e) {
                logger.warn("No alert present after submission");
            }

            // Step 10: Verify success
            WebElement successMessage = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".status.alert.alert-success")
                    )
            );
            Assert.assertTrue(successMessage.getText().contains("Success! Your details have been submitted successfully."),
                    "Success message not displayed");

            // Step 11: Return home
            clickWithRetry(By.cssSelector(".btn-success"));
            wait.until(ExpectedConditions.urlToBe(BASE_URL + "/"));
            Assert.assertTrue(driver.getTitle().contains("Automation Exercise"),
                    "Failed to return to home page");

        } catch (Exception e) {
            captureScreenshot("contact-us-error");
            throw e;
        }
    }

    @Test
    public void testVerifyAllProductsAndDetailPage() throws IOException {
        try {
            // Steps 1-3: Launch browser and verify home page (handled in @BeforeMethod)
            logger.info("Navigating to home page");
            driver.get(BASE_URL);
            verifyElementPresence(By.cssSelector("header[id='header']"), "Home page header");

            // Step 4: Click Products button
            clickWithRetry(By.cssSelector("a[href='/products']"));

            // Step 5: Verify ALL PRODUCTS page
            wait.until(ExpectedConditions.urlContains("/products"));
            WebElement productsTitle = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".features_items h2.title")
                    )
            );
            Assert.assertTrue(productsTitle.getText().contains("ALL PRODUCTS"),
                    "Not navigated to All Products page");

            // Step 6: Verify products list
            List<WebElement> products = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.cssSelector(".features_items .product-image-wrapper")
                    )
            );
            Assert.assertTrue(products.size() > 0, "No products displayed");

            // Step 7: Click first product's View Product
            clickWithRetry(By.cssSelector(".product-image-wrapper a[href='/product_details/1']"));

            // Step 8: Verify product detail page
            wait.until(ExpectedConditions.urlContains("/product_details"));
            verifyElementPresence(By.cssSelector(".product-details"), "Product details section");

            // Step 9: Verify product details
            String[] expectedDetails = {
                    "Blue Top",
                    "Category: Women > Tops",
                    "Rs. 500",
                    "Availability: In Stock",
                    "Condition: New",
                    "Brand: Polo"
            };

            WebElement productInfo = driver.findElement(By.cssSelector(".product-information"));
            String productInfoText = productInfo.getText();

            for (String detail : expectedDetails) {
                Assert.assertTrue(productInfoText.contains(detail),
                        "Missing product detail: " + detail);
            }

        } catch (Exception e) {
            captureScreenshot("product-details-error");
            throw e;
        }
    }

    @Test
    public void testSearchProduct() throws IOException {
        try {
            // Steps 1-3: Launch browser and verify home page
            logger.info("Navigating to home page");
            driver.get(BASE_URL);
            verifyElementPresence(By.cssSelector("header[id='header']"), "Home page header");

            // Step 4: Click Products button
            clickWithRetry(By.cssSelector("a[href='/products']"));

            // Step 5: Verify ALL PRODUCTS page
            wait.until(ExpectedConditions.urlContains("/products"));
            verifyElementPresence(By.cssSelector(".features_items h2.title"), "Products page title");

            // Step 6: Search for product
            String searchTerm = "T-Shirt";
            sendKeysWithRetry(By.id("search_product"), searchTerm);
            clickWithRetry(By.id("submit_search"));

            // Step 7: Verify 'SEARCHED PRODUCTS'
            WebElement searchHeader = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".features_items h2.title")
                    )
            );
            Assert.assertTrue(searchHeader.getText().contains("SEARCHED PRODUCTS"),
                    "Search header not displayed");

            // Step 8: Verify search results
            List<WebElement> searchResults = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.cssSelector(".features_items .productinfo")
                    )
            );
            Assert.assertTrue(searchResults.size() > 0, "No search results found");

            // Verify all results contain search term
            for (WebElement product : searchResults) {
                String productName = product.findElement(By.cssSelector("p")).getText();
                Assert.assertTrue(productName.toLowerCase().contains(searchTerm.toLowerCase()),
                        "Product '" + productName + "' doesn't match search term");
            }

        } catch (Exception e) {
            captureScreenshot("product-search-error");
            throw e;
        }
    }

    @Test
    public void testHomePageSubscription() throws IOException, InterruptedException {
        try {
            // Steps 1-3: Launch browser and verify home page
            logger.info("Navigating to home page");
            driver.get(BASE_URL);
            verifyElementPresence(By.cssSelector("header[id='header']"), "Home page header");

            // Step 4: Scroll to footer
            WebElement footer = driver.findElement(By.id("footer"));
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", footer);
            Thread.sleep(500); // Small pause for scroll to complete

            // Step 5: Verify SUBSCRIPTION text
            WebElement subscriptionText = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".single-widget h2")
                    )
            );
            Assert.assertEquals(subscriptionText.getText(), "SUBSCRIPTION",
                    "Subscription header not matching");

            // Step 6: Enter email and submit
            String testEmail = "test_" + System.currentTimeMillis() + "@example.com";
            sendKeysWithRetry(By.id("susbscribe_email"), testEmail);
            clickWithRetry(By.id("subscribe"));

            // Step 7: Verify success message
            WebElement successMessage = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".alert-success.alert")
                    )
            );
            Assert.assertTrue(successMessage.getText().contains("You have been successfully subscribed!"),
                    "Subscription success message not displayed");

        } catch (Exception e) {
            captureScreenshot("subscription-error");
            throw e;
        }
    }

    @Test
    public void testSubscriptionInCartPage() throws IOException, InterruptedException {
        try {
            // Steps 1-3: Launch browser and verify home page
            logger.info("Navigating to home page");
            driver.get(BASE_URL);
            verifyElementPresence(By.cssSelector("header[id='header']"), "Home page header");

            // Step 4: Click Cart button
            clickWithRetry(By.cssSelector("li a[href='/view_cart']"));
            wait.until(ExpectedConditions.urlContains("/view_cart"));

            // Step 5: Scroll to footer
            WebElement footer = driver.findElement(By.id("footer"));
            ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true);", footer);
            Thread.sleep(500);

            // Step 6: Verify SUBSCRIPTION text
            WebElement subscriptionText = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".single-widget h2")
                    )
            );
            Assert.assertEquals(subscriptionText.getText(), "SUBSCRIPTION",
                    "Subscription header not matching");

            // Step 7: Enter email and submit
            String testEmail = "test_" + System.currentTimeMillis() + "@example.com";
            sendKeysWithRetry(By.id("susbscribe_email"), testEmail);
            clickWithRetry(By.id("subscribe"));

            // Step 8: Verify success message
            WebElement successMessage = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".alert-success.alert")
                    )
            );
            Assert.assertTrue(successMessage.getText().contains("You have been successfully subscribed!"),
                    "Subscription success message not displayed");

        } catch (Exception e) {
            captureScreenshot("cart-subscription-error");
            throw e;
        }
    }

    @Test
    public void testAddMultipleProductsToCart() throws IOException {
        try {
            // Steps 1-3: Launch browser and verify home page
            logger.info("Navigating to home page");
            driver.get(BASE_URL);
            verifyElementPresence(By.cssSelector("header[id='header']"), "Home page header");

            // Step 4: Click Products button
            clickWithRetry(By.cssSelector("a[href='/products']"));
            wait.until(ExpectedConditions.urlContains("/products"));

            // Get product prices for verification
            List<WebElement> products = driver.findElements(By.cssSelector(".productinfo"));
            String firstProductPrice = products.get(0).findElement(By.tagName("h2")).getText();
            String secondProductPrice = products.get(1).findElement(By.tagName("h2")).getText();

            // Step 5: Add first product
            hoverAndClick(products.get(0), By.cssSelector(".add-to-cart"));
            clickWithRetry(By.cssSelector(".modal-footer button.close-modal"));

            // Step 6-7: Add second product
            hoverAndClick(products.get(1), By.cssSelector(".add-to-cart"));

            // Step 8: View Cart
            clickWithRetry(By.xpath("//u[contains(text(),'View Cart')]"));

            // Step 9: Verify both products
            List<WebElement> cartItems = wait.until(
                    ExpectedConditions.presenceOfAllElementsLocatedBy(
                            By.cssSelector("#cart_info_table tbody tr")
                    )
            );
            Assert.assertEquals(cartItems.size(), 2, "Incorrect number of products in cart");

            // Step 10: Verify details
            verifyCartItem(0, firstProductPrice, "1", firstProductPrice);
            verifyCartItem(1, secondProductPrice, "1", secondProductPrice);

        } catch (Exception e) {
            captureScreenshot("multiple-products-cart-error");
            throw e;
        }
    }

    @Test
    public void testProductQuantityInCart() throws IOException {
        try {
            // Steps 1-3: Launch browser and verify home page
            logger.info("Navigating to home page");
            driver.get(BASE_URL);
            verifyElementPresence(By.cssSelector("header[id='header']"), "Home page header");

            // Step 4: Click View Product for first product
            clickWithRetry(By.cssSelector(".features_items a[href='/product_details/1']"));
            wait.until(ExpectedConditions.urlContains("/product_details"));

            // Step 5: Verify product detail page
            verifyElementPresence(By.cssSelector(".product-details"), "Product details section");

            // Step 6: Change quantity to 4
            WebElement quantityInput = driver.findElement(By.id("quantity"));
            quantityInput.clear();
            quantityInput.sendKeys("4");

            // Step 7: Add to cart
            clickWithRetry(By.cssSelector("button[type='button']"));
            wait.until(ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector(".modal-content")
            ));

            // Step 8: View Cart
            clickWithRetry(By.xpath("//u[contains(text(),'View Cart')]"));

            // Step 9: Verify quantity
            WebElement cartQuantity = wait.until(
                    ExpectedConditions.visibilityOfElementLocated(
                            By.cssSelector(".cart_quantity button")
                    )
            );
            Assert.assertEquals(cartQuantity.getText(), "4",
                    "Product quantity in cart doesn't match expected value");

            // Get price elements more precisely
            String unitPriceText = driver.findElement(By.cssSelector(".cart_price p")).getText();
            String totalPriceText = driver.findElement(By.cssSelector(".cart_total p")).getText();

            // Convert prices to integers
            int unitPrice = Integer.parseInt(unitPriceText.replace("Rs. ", "").trim());
            int totalPrice = Integer.parseInt(totalPriceText.replace("Rs. ", "").trim());
            int quantity = Integer.parseInt(cartQuantity.getText());

            // Step 10: Verify total calculation (unit price × quantity)
            int expectedTotal = unitPrice * quantity;
            Assert.assertEquals(totalPrice, expectedTotal,
                    String.format("Price calculation failed. Expected %d × %d = %d, but got %d",
                            unitPrice, quantity, expectedTotal, totalPrice));

        } catch (Exception e) {
            captureScreenshot("cart-quantity-error");
            throw e;
        }
    }


    // Helper methods
    private void verifyElementPresence(By locator, String elementName) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        Assert.assertTrue(element.isDisplayed(), elementName + " not visible");
        logger.info("{} verified", elementName);
    }

    private void sendKeysWithRetry(By locator, String text) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.clear();
        element.sendKeys(text);
        logger.info("Entered text '{}' in {}", text, locator);
    }

    private void clickWithRetry(By locator) {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        element.click();
        logger.info("Clicked on {}", locator);
    }

    private void selectDropdown(By locator, String visibleText) {
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        new Select(element).selectByVisibleText(visibleText);
        logger.info("Selected '{}' from dropdown {}", visibleText, locator);
    }

    private void captureScreenshot(String filename) throws IOException {
        try {
            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String screenshotPath = "./screenshots/" + filename + "-" + System.currentTimeMillis() + ".png";
            FileUtils.copyFile(screenshot, new File(screenshotPath));
            logger.error("Screenshot saved: {}", screenshotPath);
        }catch (Exception e){
            logger.info(e.getMessage());
        }
    }

    private void hoverAndClick(WebElement element, By clickLocator) {
        new Actions(driver).moveToElement(element).perform();
        element.findElement(clickLocator).click();
    }

    private void verifyCartItem(int index, String expectedPrice, String expectedQuantity, String expectedTotal) {
        WebElement cartItem = driver.findElements(By.cssSelector("#cart_info_table tbody tr")).get(index);
        String actualPrice = cartItem.findElement(By.cssSelector(".cart_price")).getText();
        String actualQuantity = cartItem.findElement(By.cssSelector(".cart_quantity")).getText();
        String actualTotal = cartItem.findElement(By.cssSelector(".cart_total")).getText();

        Assert.assertEquals(actualPrice, expectedPrice, "Price mismatch for item " + index);
        Assert.assertEquals(actualQuantity, expectedQuantity, "Quantity mismatch for item " + index);
        Assert.assertEquals(actualTotal, expectedTotal, "Total price mismatch for item " + index);
    }

}