package selenium;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class LocateElements {
	 WebDriver driver;
	 JavascriptExecutor js;

	    @Before
	    public void setup() {
	    	driver = new FirefoxDriver();
	        js = (JavascriptExecutor) driver;
	    }

	    @Test
	    public void locateElements() {
	        driver.get("https://www.saucedemo.com/");

	        Assert.assertTrue(driver.findElement(By.id("user-name")).isDisplayed());
	        Assert.assertTrue(driver.findElement(By.id("password")).isDisplayed());
	        Assert.assertTrue(driver.findElement(By.id("login-button")).isEnabled());
	    }

	    @After
	    public void tearDown() {
	        driver.quit();
	    }
	}


