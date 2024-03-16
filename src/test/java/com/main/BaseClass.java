package com.main;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class BaseClass extends ConfigFileReader {

	public static WebDriver driver;
	public static ExtentSparkReporter htmlReporter;
	public static ExtentReports extent;
	public static ExtentTest logger;
	public static Logger log = Logger.getLogger("BaseClass");

//----------------Login and Logout Application----------------------

	@BeforeMethod
	public void loginAdhocApp() throws Exception {

		PropertyConfigurator.configure("Log4j.properties");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		log.info("Chrome Driver launched");
		driver.get(getProperty("flipKartUrl"));
	}

	@AfterMethod
	public void tearDownDriver(ITestResult result) {
		driver.quit();
	}

//----------------Dynamic report creation----------------------
	@BeforeTest
	public void setExtent() throws IOException {
		String repName = "AutomationTestResult" + ".html";
		htmlReporter = new ExtentSparkReporter(System.getProperty("user.dir") + "/Reports/" + repName);
		htmlReporter.loadXMLConfig("html-config.xml");
		extent = new ExtentReports();
		// Passing General information
		extent.setSystemInfo("Host name", "Test IP");
		extent.setSystemInfo("user", "Jeyasankar");
		extent.setSystemInfo("Browser", "Chrome");
		extent.setSystemInfo("Testing Type", "Regression Test");
	}

	@AfterTest
	public void reportEnd() {
		extent.flush();

	}

	public static void startLogger(String Message) {
		logger = extent.createTest(Message);
		extent.attachReporter(htmlReporter);

	}

//----------------ScreenShot Validation Part----------------------

	public static void extentScreenshot(String Name) throws Exception {
		String screenshotPath = takeSnapShot(Name);
		logger.addScreenCaptureFromPath(screenshotPath);

	}

	public static String takeSnapShot(String screenshotName) throws Exception {
		File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		String dateName = new SimpleDateFormat("yyyyMMddhhmmss").format(new Date());
		String FinalPath = System.getProperty("user.dir") + "/Screenshots/" + screenshotName + dateName + ".png";
		System.out.println(FinalPath);
		FileUtils.copyFile(screenshot, new File(FinalPath));
		return FinalPath;
	}

//----------------Common Methods----------------------

	// Type Method
	public static String type(String loc, String value) throws InterruptedException {
		int attempts = 0;
		while (attempts++ < 3) {

			try {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
				wait.until(ExpectedConditions.elementToBeClickable(By.xpath(getLocator(loc))));
				driver.findElement(By.xpath(getLocator(loc))).sendKeys(getProperty(value));
				break;
			} catch (Exception e) {
				log.info(loc + " element is not clickable,pease check!", e);
			    throw new NoSuchElementException(loc + " element is not clickable,pease check!");

			}
		}
		return null;

	}

	// Click Method
	public static String click(String button) {
		int attempts = 0;
		while (attempts++ < 3) {

			try {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
				wait.until(
						ExpectedConditions.and(ExpectedConditions.elementToBeClickable(By.xpath(getLocator(button)))));
				driver.findElement(By.xpath(getLocator(button))).click();
				break;
			} catch (NoSuchElementException e) {
				log.info(button + " element is not clickable,pease check!", e);
			    throw new NoSuchElementException(button + " element is not clickable,pease check!");
			}
		}

		return null;
	}

//----------------Dynamic wait for each element----------------------

	// Wait For Element
	public static String waitForElement(String button) {
		int attempts = 0;
		while (attempts++ < 2) {
			try {
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(50));
				wait.until(ExpectedConditions.and(
						ExpectedConditions.presenceOfElementLocated(By.xpath(getLocator(button))),
						ExpectedConditions.elementToBeClickable(By.xpath(getLocator(button))),
						ExpectedConditions.visibilityOfElementLocated(By.xpath(getLocator(button)))));
				break;
			} catch (Exception e) {
				log.info(button + " element is not visible,pease check!", e);
				throw new RuntimeException(button + " element is not visible,pease check!");
			}
		}
		return null;
	}

// ----------------Verification/Validation Part----------------------

	public static String verifyByText(String actValue, String expValue) throws IOException {

		String expectedText = getProperty(expValue);
		String actualText = driver.findElement(By.xpath(getLocator(actValue))).getText();
		if (actualText.equalsIgnoreCase(expectedText)) {
			Assert.assertTrue(true);
			logger.info("Actual Text :" + actualText + " Passed");
		} else {
			logger.info("Actual Text : " + actualText + " Failed");
			Assert.assertTrue(false);
		}
		return null;
	}

	public static String verifyByTitle(String expValue) throws IOException {

		String expectedText = getProperty(expValue);
		String actualText = driver.getTitle();
		if (actualText.equalsIgnoreCase(expectedText)) {
			Assert.assertTrue(true);
			logger.info("Title of the page :" + actualText + " Passed");
		} else {
			logger.info("Title of the page : " + actualText + " Failed");
			Assert.assertTrue(false);
		}
		return null;
	}

	/*
	 * HCL Interview 1.Launch Flipkart
	 * 
	 * 2.Type iphone in the search box and hit enter
	 * 
	 * 3.collect all the product through for loop
	 * 
	 * 4.Among the product, validate the product that matches with
	 * "iphone 11 128GB black"
	 * 
	 * 5.click on the product to go to the product details page
	  * driver.get("flipkart") 
	 * driver.findElement(By.id("").sendKeys("iphone");
	 * driver.findElement(By.xpath("seacrchBoxId").click();
	 * 
	 * int productSize=driver.findElements(By.xpath"").size();
	 * 
	 * for (int i=1; i<productSize; i++){
	 *  String productName = driver.findElement(By.xpath""[i]).getText();
	 * (                    //*[@class="_4rR01T"])[1]
	 * 
	 * if(productName.equalIgnoreCase("iphone 11 128GB black"){
	 * 
	 * driver.findElement(By.name("productDetails).click() }
	 * 
	 */
	

	
	

}
