package com.ServiceNow;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class servicenow {
	static WebDriver driver;
    static WebDriverWait wait;
    
    public static void captureScreenshot(WebDriver driver, String stepName) {
        try {
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());

            TakesScreenshot ts = (TakesScreenshot) driver;
            File source = ts.getScreenshotAs(OutputType.FILE);
            new File(System.getProperty("user.dir") + "/screenshots").mkdirs();

            String destination = System.getProperty("user.dir")
                    + "/screenshots/" + stepName + "_" + timeStamp + ".png";

            Files.copy(source.toPath(), new File(destination).toPath());

            System.out.println("Screenshot captured: " + stepName);

        } catch (Exception e) {
            System.out.println("Screenshot failed: " + e.getMessage());
        }
    }
    
	public static void main(String[] args) throws InterruptedException  {
		// TODO Auto-generated method stub
		WebDriver driver = new ChromeDriver();		
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        //Login
        driver.get("https://dev294284.service-now.com/login.do");
        driver.findElement(By.id("user_name")).sendKeys("admin");
        driver.findElement(By.id("user_password")).sendKeys("Shravs@1828");
        driver.findElement(By.id("sysverb_login")).click();
        captureScreenshot(driver, "Login_Success");
        System.out.println("Login successful");
       
        //Verify homepage title
        System.out.println("Title: " + driver.getTitle());
        JavascriptExecutor js = (JavascriptExecutor) driver;        
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        driver.switchTo().defaultContent();
        Thread.sleep(1000);
        
        // macroponent
        WebElement macro = wait.until(
                ExpectedConditions.presenceOfElementLocated(
                        By.xpath("//*[starts-with(name(),'macroponent-')]")));
        SearchContext shadow1 = macro.getShadowRoot();

        // sn-canvas-appshell-root (HAS shadow but we DO NOT go inside it)
        WebElement appRoot = shadow1.findElement(
                By.cssSelector("sn-canvas-appshell-root"));     

        // sn-canvas-appshell-layout (directly inside macro shadow)
        WebElement layout = shadow1.findElement(
                By.cssSelector("sn-canvas-appshell-layout"));

        // sn-polaris-layout (HAS shadow)
        WebElement polarisLayout = layout.findElement(
                By.cssSelector("sn-polaris-layout"));
        SearchContext shadow2 = polarisLayout.getShadowRoot();
                
        // sn-polaris-header (HAS shadow)
        WebElement header = shadow2.findElement(
                By.cssSelector("sn-polaris-header"));
        SearchContext shadow3 = header.getShadowRoot();

        //ALL button
        WebElement allButton = shadow3.findElement(
                By.cssSelector("div[aria-label='All']"));
        allButton.click();
        System.out.println("All button clicked successfully!");
                       
     // Locate sn-polaris-menu (Unpinned All menu)
        WebElement menu = shadow3.findElement(
                By.cssSelector("sn-polaris-menu[aria-label='Unpinned All menu']"));
        SearchContext menuShadow = menu.getShadowRoot();

        // Locate filter input inside menu shadow
        WebElement filterInput = menuShadow.findElement(
                By.cssSelector("input#filter"));
        filterInput.clear();
        filterInput.sendKeys("Incident");
        System.out.println("Typed Incident in filter box");		        
       
        List<WebElement> collapsibleLists = menuShadow.findElements(
                By.cssSelector("sn-collapsible-list"));

        WebElement incidentSection = collapsibleLists.get(2);
        SearchContext incidentShadow = incidentSection.getShadowRoot();

        WebElement correctAll = incidentShadow.findElement(
                By.cssSelector("a[aria-label^='All']"));

        // JS click instead of normal click
        JavascriptExecutor js1 = (JavascriptExecutor) driver;
        js1.executeScript("arguments[0].click();", correctAll);

        System.out.println("Clicked Incident -> All correctly");
        System.out.println("Incident list page loaded");
                     
     // after clicking Incident -> All

        wait.until(ExpectedConditions.urlContains("incident_list.do"));
        driver.switchTo().defaultContent();		       
        
        WebElement shadowHost = (WebElement) ((JavascriptExecutor)driver).executeScript("return document.querySelector('body > macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot.querySelector('div > sn-canvas-appshell-root > sn-canvas-appshell-layout > sn-polaris-layout')");
        WebElement iFrame = shadowHost.findElement(By.id("gsft_main"));
        driver.switchTo().frame(iFrame);
        System.out.println("Switched to gsft_main");
        
        // click New
        WebElement newBtn = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("sysverb_new"))
        );
        newBtn.click();
        System.out.println("New button clicked");
        
        //fill caller
        WebElement caller = wait.until(
                ExpectedConditions.elementToBeClickable(By.id("sys_display.incident.caller_id")));
        caller.sendKeys("Abel Tuter");
        caller.sendKeys(Keys.TAB);
        System.out.println("caller filled");
        
        //fill category
        Select category = new Select(
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("incident.category")))); 	    
        category.selectByVisibleText("Software");
        System.out.println("category filled");
        
        //fill subcategory
        Select subCategory = new Select(
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("incident.subcategory"))));        
       subCategory.selectByVisibleText("Email");
        System.out.println("subcategory filled");
        
        //shortdescription
        WebElement shortDesc = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("incident.short_description")));
        shortDesc.sendKeys("Unable to access email");
        System.out.println("short description filled");
        
        //description
        WebElement description = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("incident.description")));
        description.sendKeys("User is unable to access the corporate email. Getting authentication error.");
        System.out.println("description filled");

         //channel       
        Select channel = new Select(
                wait.until(ExpectedConditions.elementToBeClickable(
                        By.id("incident.contact_type"))));
        channel.selectByVisibleText("Phone"); 
        System.out.println("channel filled");   
        
     // Capture Incident Number BEFORE submit
        WebElement incidentNumber = wait.until(
        		ExpectedConditions.presenceOfElementLocated(By.id("incident.number")));
        String incNumber = incidentNumber.getAttribute("value").trim();
        System.out.println("Captured Incident Number: " + incNumber);
        
        // Click Submit
        WebElement submitBtn = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("sysverb_insert")));
        submitBtn.click();
        System.out.println("Form submitted");

        // Wait for save   
        WebElement search = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//input[@aria-label='Search']")));
        search.clear();
        search.sendKeys(incNumber);
        search.sendKeys(Keys.ENTER);     
        WebElement incidentLink = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//a[text()='" + incNumber + "']")));
        incidentLink.click();     

     // Change state to Resolved
        Select state = new Select(wait.until(ExpectedConditions.elementToBeClickable(
                By.id("incident.state"))));
        state.selectByVisibleText("Resolved");
        System.out.println("State changed to resolved"); 
        
        wait.until(ExpectedConditions.attributeToBe(
                By.id("incident.state"), "value", "6"));
        
        WebElement resolutionTab = wait.until(ExpectedConditions.elementToBeClickable(
                By.xpath("//span[@class='tab_caption_text' and text()='Resolution Information']")));
        resolutionTab.click();
        
        //add resolution code and notes
        WebElement resolutionCode = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        By.id("incident.close_code")));
        new Select(resolutionCode).selectByVisibleText("Resolved by caller");
        
        WebElement resolutionNotes = wait.until(
                ExpectedConditions.elementToBeClickable(
                        By.id("incident.close_notes")));
        resolutionNotes.sendKeys("Issue fixed successfully");     
        System.out.println("Resolution code and notes added");
        
        //update
        WebElement updateBtn = driver.findElement(By.id("sysverb_update"));
        updateBtn.click();
        System.out.println("Form updated");
        
     // 1️⃣ Come out of iframe
        driver.switchTo().defaultContent();
        		        
        // 2️⃣ Wait for page
        WebDriverWait wait1 = new WebDriverWait(driver, Duration.ofSeconds(40));
        JavascriptExecutor js11 = (JavascriptExecutor) driver;
        WebElement searchBox = (WebElement)((JavascriptExecutor)driver).executeScript(
        		"return document.querySelector('macroponent-f51912f4c700201072b211d4d8c26010')" +
        		".shadowRoot.querySelector('sn-polaris-layout')" +
        		".shadowRoot.querySelector('sn-polaris-header')" +
        		".shadowRoot.querySelector('sn-search-input-wrapper')" +
        		".shadowRoot.querySelector('sn-component-workspace-global-search-typeahead')" +
        		".shadowRoot.querySelector('#sncwsgs-typeahead-input')");
        searchBox.sendKeys(incNumber);
        searchBox.sendKeys(Keys.ENTER);
        System.out.println("found the global search result");
        	       
      Thread.sleep(3000);	      
      String parentWindow = driver.getWindowHandle();
      JavascriptExecutor js111 = (JavascriptExecutor) driver;

      WebElement button = (WebElement) js111.executeScript(

      "return document.querySelector('body > macroponent-f51912f4c700201072b211d4d8c26010')" +
      ".shadowRoot.querySelector('#item-snCanvasAppshellMain')" +
      ".shadowRoot.querySelector('div > sn-canvas-experience-shell > macroponent-76a83a645b122010b913030a1d81c780')" +
      ".shadowRoot.querySelector('div > sn-canvas-root > sn-canvas-layout > sn-canvas-main')" +
      ".shadowRoot.querySelector('main > sn-canvas-screen:nth-child(2)')" +
      ".shadowRoot.querySelector('section > screen-action-transformer-dcd3e42dc7202010099a308dc7c26002 > "
      + "macroponent-d4d3a42dc7202010099a308dc7c2602b')" +
      ".shadowRoot.querySelector('#item-search_result_wrapper_1')" +
      ".shadowRoot.querySelector('sn-component-workspace-global-search-tab')" +
      ".shadowRoot.querySelector('now-button-iconic')" +
      ".shadowRoot.querySelector('button')"
      );
      js111.executeScript("arguments[0].click();", button);
      wait.until(ExpectedConditions.numberOfWindowsToBe(2));

      for (String win : driver.getWindowHandles()) {
          if (!win.equals(parentWindow)) {
              driver.switchTo().window(win);
              break;
          }
      }
     
	      System.out.println("Incident form opened ");
	        
	   // Click Notes tab
      Thread.sleep(3000);
	        WebElement notesTab = wait.until(ExpectedConditions.elementToBeClickable(
	                By.xpath("//span[@class='tab_caption_text' and text()='Notes']")));
	        notesTab.click();
	       
	        // Wait for textarea
	        WebElement workNotes = wait.until(ExpectedConditions.elementToBeClickable(
	                By.cssSelector("#activity-stream-textarea")));
	       // Type notes
	        workNotes.sendKeys("Issue verified. Closing the incident.");
	      Thread.sleep(1000);
	        System.out.println("Work notes submitted successfully");
	        
	        //Update State
	      Thread.sleep(2000);
	        Select state1 = new Select(wait.until(
	                ExpectedConditions.elementToBeClickable(By.id("incident.state"))));
	        state1.selectByVisibleText("Closed");
	      Thread.sleep(2000);
	        driver.findElement(By.id("sysverb_update")).click();  
	        System.out.println("Incident is Updated");
	      driver.close();
	    driver.switchTo().window(parentWindow);
	  driver.switchTo().defaultContent();
	  WebElement correctAll1 = incidentShadow.findElement(
		        By.cssSelector("a[aria-label^='All']"));
		JavascriptExecutor js1111 = (JavascriptExecutor) driver;
		js1111.executeScript("arguments[0].click();", correctAll1);
		System.out.println("Opened all incidents");
		  Thread.sleep(5000);

		WebElement shadowHost1 = (WebElement) ((JavascriptExecutor)driver).executeScript("return document.querySelector('body > macroponent-f51912f4c700201072b211d4d8c26010').shadowRoot.querySelector('div > sn-canvas-appshell-root > sn-canvas-appshell-layout > sn-polaris-layout')");
		WebElement iFrame1 = shadowHost1.findElement(By.id("gsft_main"));
		driver.switchTo().frame(iFrame1);    	
			WebElement searchBox1 = wait.until(ExpectedConditions.visibilityOfElementLocated(
			        By.xpath("//input[@placeholder='Search']")));

			searchBox1.clear();
			searchBox1.sendKeys(incNumber);
			searchBox1.sendKeys(Keys.ENTER);
		  Thread.sleep(3000);
			
			WebElement state11 = wait.until(ExpectedConditions.visibilityOfElementLocated(
			        By.xpath("//a[text()='" + incNumber + "']/../../td[8]")));
			System.out.println("Final state = " + state11.getText());
		String finalState = state11.getText();

		if(finalState.equalsIgnoreCase("Closed")) {
		    System.out.println("✅ Incident closed successfully");
		} else {
		    System.out.println("❌ Incident not closed");
		}
		}
		}
		       


	        	      
	