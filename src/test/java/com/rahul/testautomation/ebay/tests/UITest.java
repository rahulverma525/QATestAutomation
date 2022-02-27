package com.rahul.testautomation.ebay.tests;

import static org.testng.AssertJUnit.assertTrue;

import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.UnexpectedAlertBehaviour;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.rahul.testautomation.ebay.config.Constant;
import com.rahul.testautomation.utils.PropertyUtils;
import com.rahul.testautomation.utils.ExcelUtils;
import com.rahul.testautomation.utils.TestUtils;
import com.rahul.testautomation.ebay.pages.HomePage;
import com.rahul.testautomation.ebay.pages.RequestedProduct;
import com.rahul.testautomation.ebay.pages.SearchResults;
import io.github.bonigarcia.wdm.WebDriverManager;



public class UITest {
	HomePage homePage;
	SearchResults searchResults;
	RequestedProduct returnedService;
	WebDriver driver;
	WebDriverWait wait;
	ExtentReports extent;
	ExtentTest logger;
	DesiredCapabilities capability;
	ChromeOptions chromeOptions;
	/**
	 * Before the test execution is started , setup and initialize the extent reporter 
	 * @throws IOException 
	 */
	
	
	@BeforeTest 
	
	
	public void setup() throws IOException {
		//PropertyUtils properties=new PropertyUtils();
		//properties.getPropValues();
		ExtentHtmlReporter reporter = new ExtentHtmlReporter("./Reports/TestReport_UI_Automation.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		//System.setProperty("webdriver.chrome.driver", Constant.CHROME_DRIVER_PATH);
		//System.setProperty("webdriver.chrome.driver", propertyConfig.chromeDriverPath);
		WebDriverManager.chromedriver().setup(); 
		
		chromeOptions=new ChromeOptions();
		if (Constant.IS_HEADLESS.contains("Yes"))
		chromeOptions.addArguments("--headless");
		chromeOptions.addArguments("--disable-dev-shm-usage");
		chromeOptions.addArguments("--no-sandbox");
		chromeOptions.setAcceptInsecureCerts(true);
		chromeOptions.setUnhandledPromptBehaviour(UnexpectedAlertBehaviour.ACCEPT);
	}

	/**
	 * Before the test  is executed , setup and initialize web driver and register the test with the extent reporter
	 * so that test result is captured in the html report
	 * @throws MalformedURLException 
	 */
	@BeforeMethod
	public void register(Method method) throws MalformedURLException {
		
		String testName = method.getName();
		logger = extent.createTest(testName);

		 if (Constant.IS_GRID.contains("Yes"))
		 { 
			 DesiredCapabilities capability = DesiredCapabilities.chrome();
			 capability.setVersion("2.45");

			 driver= new RemoteWebDriver (new URL("http://localhost:4444/wd/hub"), DesiredCapabilities.chrome());
		
		 } else  driver = new ChromeDriver();
	   
		driver.get(Constant.URL);
		homePage = new HomePage(driver);
		assertTrue("Home Page was not found", homePage.isTitlePresent("| eBay"));
	}

	/**
	 * Test to see if correct search results are shown when user searches for a particular product on EBay AU Site. 
	 *
	 * @param  product2Search is the product to be searched on EBay global search eg Apple Watch or Humidifier	
	 * @param  expectedProductInSearchResults  is one of the products expected to show up in  the search results
	 * Assertions are made that the expected search result is shown 
	 */
	
	@DataProvider	   
	public static Object[][]  getData() throws Exception {
	   
		Object[][] testObjArray = ExcelUtils.getTableArray(Constant.UI_TEST_DATA_PATH);
		return (testObjArray); 
		 
	}
	
	@Test(dataProvider="getData")

	public void searchProduct(String product2Search, String expectedProductInSearchResults
			) throws InterruptedException {
		logger.log(Status.INFO,
				"Search for required product and verify the search results");
		searchResults = homePage.searchProduct(product2Search);// Search for desired product on home page and expect search results page
		assertTrue("Search  page was not found", searchResults.isTitlePresent(product2Search + " | eBay"));
		returnedService = searchResults.choseRequestedProduct(expectedProductInSearchResults);//Click on the desired service on the search results page to land on desired service page 
	//	assertTrue("Requested service page was not found",returnedService.isTitlePresent(expectedProductInSearchResults ));
	}

	/**
	 * Test the result after each test run to make sure that screenshots are captured for each tests error/failure and attached to the test report.	 * 
	 */
	
	@AfterMethod
	
	public void tearDown(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS, "The Test Method named as :" + result.getName() + " is passed");
		} else if (result.getStatus() == ITestResult.FAILURE) {
			String temp = TestUtils.getScreenshot(driver);

			logger.fail(result.getThrowable().getMessage(),
					MediaEntityBuilder.createScreenCaptureFromPath(temp).build());
			logger.log(Status.FAIL, "The Test Method named as :" + result.getName() + " is failed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP, "The Test Method named as :" + result.getName() + " is skipped");
		}
		driver.quit();
	}

	/**
	 * After the test execution is complete , flush the extent html report 
	 */
	
	@AfterTest

	public void cleanUp() {

		extent.flush();

	}

}

