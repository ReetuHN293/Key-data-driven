package commonFunctions;

import java.io.FileInputStream;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public  class FunctionLibrary {
public static Properties conpro;
public static WebDriver driver;
//method for launching browser
public static WebDriver startBrowser()throws Throwable
{
	conpro = new Properties();
	//load property file
	conpro.load(new FileInputStream("./PropertyFiles/Environment.properties"));
	if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
	{
		driver = new ChromeDriver();
		driver.manage().window().maximize();
	}
	else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox"))
	{
		driver = new FirefoxDriver();
	}
	else
	{
		Reporter.log("Browser Value is Not Matching",true);
	}
	return driver;
}
public static String generateDate()
{
	Date date = new Date();
	SimpleDateFormat df = new SimpleDateFormat("YYYY_MM_dd");
	return df.format(date);
}

//method for launch url
public static void openUrl()
{
	driver.get(conpro.getProperty("Url"));
	
}
//method for wait for any element
public static void waitForElement(String LocatorType,String LocatorValue,String wait, String EleName)
{
	WebDriverWait mywait = new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(wait)));
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		//wait unti element is visible
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LocatorValue)));
		System.out.println("waiting for " + EleName);
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		//wait unti element is visible
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.name(LocatorValue)));
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		//wait unti element is visible
		mywait.until(ExpectedConditions.visibilityOfElementLocated(By.id(LocatorValue)));
	}
	
	new WebDriverWait(driver, Duration.ofSeconds(30))
    .until(webDriver -> ((JavascriptExecutor) webDriver)
    .executeScript("return document.readyState").equals("complete"));
	
}
public static void typeAction(String LocatorType,String LocatorValue,String TestData)
{
	if(LocatorType.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(LocatorValue)).clear();
		driver.findElement(By.name(LocatorValue)).sendKeys(TestData);
	}
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		driver.findElement(By.xpath(LocatorValue)).clear();
		driver.findElement(By.xpath(LocatorValue)).sendKeys(TestData);
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(LocatorValue)).clear();
		driver.findElement(By.id(LocatorValue)).sendKeys(TestData);
	}
}
//method for buttons,links,radiobutton,checkboxes and images
public static void clickAction(String LocatorType,String LocatorValue)
{
	if(LocatorType.equalsIgnoreCase("xpath"))
	{
		try {
		driver.findElement(By.xpath(LocatorValue)).click();
		}
		catch(Exception e)
		{
			driver.findElement(By.xpath(LocatorValue)).click();
		}
	}
	if(LocatorType.equalsIgnoreCase("name"))
	{
		driver.findElement(By.name(LocatorValue)).click();
	}
	if(LocatorType.equalsIgnoreCase("id"))
	{
		driver.findElement(By.id(LocatorValue)).sendKeys(Keys.ENTER);
	}
}
//method for validateTitle
public static void validateTitle(String Expected_Title)
{
	String Actual_Title =driver.getTitle();
	try {
		Assert.assertEquals(Actual_Title, Expected_Title,"Title is Not Matching");
	} catch (AssertionError e) {
		System.out.println(e.getMessage());
	}
}
public static void closeBrowser()
{
	driver.quit();
}

public static String getText(String locatorType, String locatorValue, String EleName, String TestData) 
{

	waitForElement(locatorType, locatorValue, TestData, EleName);
	String text = driver.findElement(By.xpath(locatorValue)).getText();
	System.out.println("✅ Text on webpage  " + text);
	Reporter.log("✅ Text on webpage" + text);
	return null;
}

public static String getValue(String locatorType, String locatorValue, String EleName) 
{
	JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("document.body.style.zoom='30%';");
    System.out.println("Minimized screen2");

	String text = driver.findElement(By.xpath(locatorValue)).getAttribute("value");
	System.out.println("✅ Text on webpage  " + text);
	Reporter.log("✅ Text on webpage" + text);
	return null;
}
public static void waitTillPageLoad ()
{
	WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
	wait.until(ExpectedConditions.urlToBe("https://sandbox.appsteer.io/user/#/u/application/11285/adddata/d0beafd0-4b83-4ff6-95c1-a562f6cf01b4/2787/3"));
}

public static void minimizeScreen() 
{

	 ChromeOptions options = new ChromeOptions();
     options.addArguments("--force-device-scale-factor=0.6");
}

}












