package selenium;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

public class InvalidLogin {
	WebDriver driver;
	JavascriptExecutor js;

    @Before
    public void setup() {
    	driver = new FirefoxDriver();
        js = (JavascriptExecutor) driver;
    }

    @Test
    public void invalidLogin() {
        driver.get("https://www.saucedemo.com/");

        driver.findElement(By.id("user-name")).sendKeys("wrong");
        driver.findElement(By.id("password")).sendKeys("wrong123");
        driver.findElement(By.id("login-button")).click();
    }

    @After
    public void tearDown() {
        driver.quit();
    }
}

