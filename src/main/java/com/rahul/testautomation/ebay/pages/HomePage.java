package com.rahul.testautomation.ebay.pages;

//import static org.testng.AssertJUnit.assertTrue;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage extends BasePageObject {
	private By globalSearchLocator = By.id("gh-ac");
	private By searchBtnLocator = By.id("gh-btn");
	WebDriverWait wait;

	/**
	 * Constructor to initialize the HomePage class with the webdriver 
	 */
	
	public HomePage(WebDriver driver) {
		super(driver);
		wait = new WebDriverWait(driver, 5);
	//	assertTrue("The page could not be loaded", wait.until(ExpectedConditions.titleContains("eBay")));
	}

	/**
	 * Method to perform a global search on the Home page for a particular product and initialize the class for the search results page which gets opened 
	 */
	
	public SearchResults searchProduct(String searchString) throws InterruptedException {
		type(searchString, globalSearchLocator);
		click(searchBtnLocator);
		return new SearchResults(driver);
	}
}
