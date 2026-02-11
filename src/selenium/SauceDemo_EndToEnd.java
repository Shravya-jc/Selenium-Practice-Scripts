package selenium;
import java.time.Duration;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SauceDemo_EndToEnd {
	 WebDriver driver;
	    WebDriverWait wait;
	    JavascriptExecutor js;

	    @Before
	    public void setup() {
	        driver = new FirefoxDriver();
	        driver.manage().window().maximize();
	        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        js = (JavascriptExecutor) driver;
	    }

	    @Test
	    public void endToEndFlow() throws InterruptedException {

	        // 1️⃣ Launch Application
	        driver.get("https://www.saucedemo.com/");

	        // 2️⃣ Login
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")))
	                .sendKeys("standard_user");

	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")))
	                .sendKeys("secret_sauce");

	        wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")))
	                .click();

	        // 3️⃣ Verify Products Page
	        Assert.assertTrue(
	                wait.until(ExpectedConditions.visibilityOfElementLocated(
	                        By.className("title"))).getText().contains("Products"));

	        // 4️⃣ Add Product to Cart
	        wait.until(ExpectedConditions.elementToBeClickable(
	                By.id("add-to-cart-sauce-labs-backpack"))).click();

	        // 5️⃣ Go to Cart
	        wait.until(ExpectedConditions.elementToBeClickable(
	                By.className("shopping_cart_link"))).click();

	        // 6️⃣ Checkout
	        wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout"))).click();

	        // 7️⃣ Enter Checkout Details
	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")))
	                .sendKeys("John");

	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("last-name")))
	                .sendKeys("David");

	        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("postal-code")))
	                .sendKeys("560001");

	        js.executeScript("window.scrollBy(0,300)");

	        wait.until(ExpectedConditions.elementToBeClickable(By.id("continue"))).click();

	        // 8️⃣ Finish Order
	        wait.until(ExpectedConditions.elementToBeClickable(By.id("finish"))).click();

	        // 9️⃣ Verify Order Success
	        String successMsg = wait.until(
	                ExpectedConditions.visibilityOfElementLocated(
	                        By.className("complete-header"))).getText();

	        Assert.assertEquals("Thank you for your order!", successMsg);

	        // 🔟 Logout
	        wait.until(ExpectedConditions.elementToBeClickable(
	                By.id("react-burger-menu-btn"))).click();

	        wait.until(ExpectedConditions.elementToBeClickable(
	                By.id("logout_sidebar_link"))).click();

	        // Pause to see final screen
	        Thread.sleep(3000);
	    }

	    @After
	    public void tearDown() {
	        driver.quit();
	    }
	}
	


