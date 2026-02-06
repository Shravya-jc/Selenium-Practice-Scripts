package selenium;
import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
//import io.github.bonigarcia.wdm.WebDriverManager;

public class LoginTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

        // Setup Chrome Driver
        //WebDriverManager.chromedriver().setup();
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.google.com");

        // Open Browser
        driver.get("https://the-internet.herokuapp.com/login");
        driver.manage().window().maximize();

        // Locate Username
        WebElement username = driver.findElement(By.id("username"));
        username.sendKeys("tomsmith");

        // Locate Password
        WebElement password = driver.findElement(By.id("password"));
        password.sendKeys("SuperSecretPassword!");

        // Click Login
        WebElement loginBtn = driver.findElement(By.xpath("//button[@type='submit']"));
        loginBtn.click();

        // Validation
        String message = driver.findElement(By.id("flash")).getText();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));


        if (message.contains("You logged into a secure area")) {
            System.out.println("✅ Login Test Passed");
        } else {
            System.out.println("❌ Login Test Failed");
        }

        // Close Browser
        driver.quit();

	}

}
