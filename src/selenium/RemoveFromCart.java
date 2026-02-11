package selenium;

import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class RemoveFromCart {
	WebDriver driver;
	JavascriptExecutor js;

    @Before
    public void setup() {
    	driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void removeFromCart() {
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        driver.findElement(By.id("login-button")).click();

        driver.findElement(By.id("add-to-cart-sauce-labs-backpack")).click();
        driver.findElement(By.id("remove-sauce-labs-backpack")).click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}


