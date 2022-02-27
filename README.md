# QATestAutomation

Public Repo : https://github.com/rahulverma525/QATestAutomation.git

Sample UI and API Test Automation

Project is developed in Java and uses Selenium Web Driver API for UI and HttpURLConnection class for API tests.
Maven is used as dependency manager and TestNG as test execution tool. Extent Reports utility is used for reporting test results.
The tests are data driven and take the test data from Excel using Apacher POI.


There are two test classes: one for the UI testing on EBay and other for calling REST APIs from https://petstore.swagger.io.



The UI test searches for a particular product on Ebay and asserts the search results.
API tests call the relevant APIs from https://petstore.swagger.io to find pet by ID, update pet name and delete pet.


One or both of these test classes can be run in one go by including/omitting these test cases in testng.xml.
testng.xml in the project home directory can be used to specify which tests are to be run.


 
UI test attempts to capture screenshots at the time of failures.
The project source code  can be downloaded from the repository and build and run as Maven/TestNG tests.
The project also includes TestNG runner class which has the main method that can be used to run the tests from command line.


**Setup**
 
1) Clone the Repository to a folder on local machine (git clone https://github.com/rahulverma525/QATestAutomation.git)
2) Cd to 'QATestAutomation' folder  (< Project Install Root>)
3) Create a 'Reports' folder under <Project Install Root> (if not existing) 
4) Go to src/test/data/ and update the excel files for the test data for UI and API test ,if and as needed:
 
    a) In the API test data file, update the 'id' to be a valid pet ID of an existing pet in the petstore and its currentPetName and the name to be updated.
    
    b) In the GUI test data file, update the search string and the data expected to be included in the search results.

5) mvn test -f "./pom.xml"
6) Check the reports under the folder after the run


**Limitations:**

Currently, recompilation is needed if the configuration needs to be changed as it is read from a Java File. 
This can be improved by having configuration as key:value pairs in a config.properties file. 
That way it can be changed before each run  and the already compiled classes should be able to get the updated configuration.
