package selenium;
import org.junit.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.JavascriptExecutor;


public class LoginLogout {
	 WebDriver driver;
	 JavascriptExecutor js;

	    @Before
	    public void setUp() {
	        driver = new FirefoxDriver();
	        js = (JavascriptExecutor) driver;
	    }
	    @Test
	    public void loginLogout() {
	        driver.get("https://www.saucedemo.com/");

	        driver.findElement(By.id("user-name")).sendKeys("standard_user");
	        driver.findElement(By.id("password")).sendKeys("secret_sauce");
	        driver.findElement(By.id("login-button")).click();

	        driver.findElement(By.id("react-burger-menu-btn")).click();
	        driver.findElement(By.id("logout_sidebar_link")).click();
	    }

	    @After
	    public void tearDown() {
	        driver.quit();
	    }
	}
