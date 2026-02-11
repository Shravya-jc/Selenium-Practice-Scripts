package selenium;
import java.time.Duration;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CheckOutFlow {
	 WebDriver driver;
	    WebDriverWait wait;
	    JavascriptExecutor js;

	    @Before
	    public void setup() {
	    	driver = new FirefoxDriver();
	        wait = new WebDriverWait(driver, Duration.ofSeconds(5));
	        js = (JavascriptExecutor) driver;
	        driver.manage().window().maximize();
	    }

	    @Test
	    public void checkoutFlow()  throws InterruptedException {
	    	 

	            // Open application
	            driver.get("https://www.saucedemo.com/");

	            // Login
	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("user-name")))
	                    .sendKeys("standard_user");
	            Thread.sleep(3000);

	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("password")))
	                    .sendKeys("secret_sauce");
	            Thread.sleep(3000);

	            wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button")))
	                    .click();
	            Thread.sleep(3000);

	            // Add product to cart
	            wait.until(ExpectedConditions.elementToBeClickable(
	                    By.id("add-to-cart-sauce-labs-backpack"))).click();
	            Thread.sleep(5000);

	            // Open cart
	            wait.until(ExpectedConditions.elementToBeClickable(
	                    By.className("shopping_cart_link"))).click();
	            Thread.sleep(3000);

	            // Checkout
	            wait.until(ExpectedConditions.elementToBeClickable(By.id("checkout"))).click();
	            Thread.sleep(3000);

	            // Checkout details
	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("first-name")))
	                    .sendKeys("John");
	            Thread.sleep(3000);

	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("last-name")))
	                    .sendKeys("David");
	            Thread.sleep(3000);

	            wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("postal-code")))
	                    .sendKeys("560001");
	            Thread.sleep(3000);

	            // Scroll if needed (JS)
	            js.executeScript("window.scrollBy(0,300)");

	            wait.until(ExpectedConditions.elementToBeClickable(By.id("continue"))).click();

	            // Finish order
	            wait.until(ExpectedConditions.elementToBeClickable(By.id("finish"))).click();
	            Thread.sleep(3000);

	            // Pause to see success page
	            Thread.sleep(3000);
	        }

	        @After
	        public void tearDown() {
	            driver.quit();
	        }
	    }