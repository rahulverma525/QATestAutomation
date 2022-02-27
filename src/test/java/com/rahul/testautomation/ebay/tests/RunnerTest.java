package com.rahul.testautomation.ebay.tests;
import java.util.List;

import org.testng.TestNG;
import org.testng.collections.Lists;


public class RunnerTest {

	public static void main(String[] args) {
	//	List<String> testFilesList = new ArrayList<String>();
	//	 testFilesList.add("./testng.xml"); //test suite resides in the working directory's root folder
	    
		 TestNG testng = new TestNG();
		  List<String> suites = Lists.newArrayList();
		    suites.add("./testng.xml");
		    testng.setTestSuites(suites);
		    testng.run();
		 
		 
		
	}
}
