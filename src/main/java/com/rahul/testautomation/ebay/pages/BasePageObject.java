package com.rahul.testautomation.ebay.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * This is the base class that contains any common code that is required across all pages to reuse code duplication and improve 
 * maintainability. Also this provides a layer of abstraction to the higher page object classes which extend this class.
 * The page object classes can use more user readable names for its methods. In case selenium APIs changes in future we need to make changes to just this class 
 * the page object classes would be immune to Selenium API changes  
 * 
 */


public class BasePageObject {
	
	String classFQNPrefix="com.rahul.testautomation.ebay";
	String classFQN;
	WebDriver driver;
	//private By locateBtnLocator = By.linkText("Locate us");
	
	/**
	 * Constructor to initialize the class with the webdriver
	 */
	BasePageObject(WebDriver driver) {
		this.driver = driver;
	}

	
	public void type(String inputText, By locator)  {
		find(locator).clear();
		find(locator).sendKeys(inputText);
	}
	
	public void visit(String url) {
		driver.get(url);
	}

	void click(By locator) {
		find(locator).click();
	}

	WebElement find(By locator) {
		return driver.findElement(locator);
	}

	void sumbit(By locator) {
		find(locator).submit();
	}

/*Boolean isDisplayed (By locator) {
	try {
	
		return find(locator).isDisplayed();
	}
	catch (org.openqa.selenium.NoSuchElementException exception){
	return false;
	}
}*/

public Boolean IsDisplayed (By locator, Integer ...timeout)
{
	try {
		
	return waitFor (ExpectedConditions.visibilityOfElementLocated (locator),(timeout.length!=0?timeout[0]:null));
	}
	catch (org.openqa.selenium.NoSuchElementException exception){
	return false;
	}
}

Boolean waitFor (ExpectedCondition<?> condition, Integer timeout)
{
	timeout=timeout!=null ? timeout :5;
    WebDriverWait wait=new WebDriverWait (this.driver,timeout);
	try 
	{
     wait.until(condition);
	}
catch ( org.openqa.selenium.NoSuchElementException exception )
	{return false;}
	return true;
}

public Boolean isTitlePresent ( String expectedTitle,Integer ...timeout)

{
try {
		
		return waitFor (ExpectedConditions.titleContains(expectedTitle),(timeout.length!=0?timeout[0]:null));
	}
	catch (org.openqa.selenium.NoSuchElementException exception){
	return false;
	}
}

/**
 * Method to click on the locate us button that shows up across all web pages and hence kept in the base class.This retunes the locate service center page  
 
public FindLocation locateServiceCenters() throws InterruptedException {
	//	type(searchString, globalSearchLocator);
		click(locateBtnLocator);
		return new FindLocation(driver);	
	}
*/
}



