package com.rahul.testautomation.ebay.tests;

import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.*;
import java.nio.charset.*;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.rahul.testautomation.ebay.config.Constant;
import com.rahul.testautomation.utils.ExcelUtils;
import com.rahul.testautomation.utils.TestUtils;

import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import io.github.bonigarcia.wdm.WebDriverManager;


public class RestAPITest {
	static String json;
	static JSONObject obj;
	ExtentReports extent;
	ExtentTest logger;
	
	
	/**
	 * Before the test execution is started , setup and initialize the extent reporter 
	 */
	@BeforeTest
	public void setup() {

		ExtentHtmlReporter reporter = new ExtentHtmlReporter("./Reports/TestReport_API_Automation.html");
		extent = new ExtentReports();
		extent.attachReporter(reporter);
		//System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
		//WebDriverManager.chromedriver().setup(); 

	}
	
	/**
	 * Before the test  is executed ,  register the test with the extent reporter
	 * so that test result is captured in the html report
	 */
	@BeforeMethod
	public void register(Method method) {
		String testName = method.getName();
		System.out.println(" The test name is"+testName);
		
		logger = extent.createTest(testName);
		}

	
	
	@DataProvider	   
	public static Object[][]  getAPITestData() throws Exception {
	   
		Object[][] testObjArray = ExcelUtils.getTableArray(Constant.API_TEST_DATA_PATH);
		return (testObjArray); 
	}	

	@Test(dataProvider="getAPITestData",priority=1)
	public static void findPetById(String endPoint,String id, String currentPetName, String updatedPetName)
	 {
		try {

			URL url = new URL(endPoint+"/pet/"+id);																											
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
			}
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			while ((json = br.readLine()) != null) {
				obj = new JSONObject(json);
				System.out.println(json);
			}
			// obj = new JSONObject(json);
			String name = obj.getString("name");
		
			System.out.println("The name of the pet  is"+name);
			
			assertTrue("The petname is not as expected", name.equals(currentPetName));
				assertTrue("The find pet by id was not successful", conn.getResponseCode()==200);
	
			conn.disconnect();

		} catch (Exception e) {
			System.out.println("Exception in RestClientGet:- " + e);
		    //e.printStackTrace();
		} 
	}
	
	@Test(dataProvider="getAPITestData",priority=2)
	public static void updatePetName(String endPoint,String id, String currentPetName, String updatedPetName)
 {
		try {
			String urlParameters  = "name='"+ updatedPetName +"'";
			System.out.println("The url paramters is" + urlParameters);
			byte[] postData       = urlParameters.getBytes(Charset.forName("UTF-8"));
			int    postDataLength = postData.length;
			URL url = new URL(endPoint+"/pet/"+id);																											
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput( true );
			conn.setInstanceFollowRedirects( false );
			conn.setRequestMethod( "POST" );
			conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded"); 
			conn.setRequestProperty( "charset", "utf-8");
			conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
			conn.setUseCaches( false );
try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
	wr.write( postData );
 }

 System.out.println("The HTTP request is" + conn);
  if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
			}
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			while ((json = br.readLine()) != null) {
				obj = new JSONObject(json);
				System.out.println(json);
			}
			
			conn.disconnect();

		} catch (Exception e) {
			System.out.println("Exception in RestClientGet:- " + e);
			//e.printStackTrace();
		}
		//assertTrue("The update of pet name  was not successful", conn.getResponseCode()==200);
			
	}

	@Test(dataProvider="getAPITestData",priority=3)
	public static void deletePetById(String endPoint,String id, String currentPetName, String updatedPetName)
	 {
		try {

			URL url = new URL(endPoint+"/pet/"+id);																											
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			
			conn.setRequestMethod("DELETE");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP Error code : " + conn.getResponseCode());
			}
			InputStreamReader in = new InputStreamReader(conn.getInputStream());
			BufferedReader br = new BufferedReader(in);
			while ((json = br.readLine()) != null) {
				obj = new JSONObject(json);
				System.out.println(json);
			}
			// obj = new JSONObject(json);
			
			
			conn.disconnect();

		} catch (Exception e) {
			System.out.println("Exception in RestClientGet:- " + e);
			
		     //e.printStackTrace();
		}
		//assertTrue("The deletion was not successful", conn.getResponseCode()==200);
	}
	
	/**
	 * Test the result after each test run to make sure that screenshots are captured for each tests error/failure and attached to the test report.	 * 
	 */
	
	@AfterMethod
	
	public void tearDown(ITestResult result) throws IOException {

		if (result.getStatus() == ITestResult.SUCCESS) {
			logger.log(Status.PASS, "The Test Method named as :" + result.getName() + " is passed");
		} else if (result.getStatus() == ITestResult.FAILURE) {
		
				logger.log(Status.FAIL, "The Test Method named as :" + result.getName() + " is failed");
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(Status.SKIP, "The Test Method named as :" + result.getName() + " is skipped");
		}
		}

	/**
	 * After the test execution is complete , flush the extent html report 
	 */
	
	@AfterTest

	public void cleanUp() {

		extent.flush();

	}

	
}

