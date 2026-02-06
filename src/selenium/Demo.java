package selenium;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.WebElement;

public class Demo {

	public static void main(String[] args) {
		//System.setProperty("webdriver.chrome.driver", "Path to chrome driver");
		WebDriver driver = new ChromeDriver();
		driver.get("https://www.google.com");
		driver.get("https://omayo.blogspot.com");
        driver.manage().window().maximize();
        driver.findElement(By.id("but2"));
        //WebElement element=driver.findElement(By.id("confirm"));
        //element.click();
        WebElement element=driver.findElement(By.name("q"));
        element.sendKeys("Shravya");
	}

}
