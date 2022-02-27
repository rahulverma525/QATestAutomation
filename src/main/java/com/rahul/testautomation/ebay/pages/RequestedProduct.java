package com.rahul.testautomation.ebay.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class RequestedProduct extends BasePageObject{
	WebDriverWait wait;
	
//	//private By searchBtnLocator = By.id("edit-submit-site-search");
	/**
	 * Constructor to initialize the Retunred Service class with the webdriver
	 */
	
	public RequestedProduct(WebDriver driver, String requestedProduct) {
		super(driver);
		wait = new WebDriverWait(driver, 5);
	//	assertTrue("The page could not be loaded", wait.until(ExpectedConditions.titleContains(requestedProduct +" | ebay")));
	}

}
