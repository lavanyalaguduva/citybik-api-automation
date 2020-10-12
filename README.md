Citybik API Test Automation (Cucumber - Rest Assured) 
========================================
This automation suite tests the Citybik service GET methods

#### Code Structure
##### Features
Test scenarios have been written using Cucumber.
The feature files are under `src/test/resources/features`. 
Mainly 3 scenarios have been automated. 
1. Get all the company details in citybik network
   - Endpoint: http://api.citybik.es/v2/networks
   - Checks: Status code, No of companies

2. Get details about a company with a specific network id
    - Endpoint: http://api.citybik.es/v2/networks/<network_id>
    - Network_id: visa-frankfurt
    - Checks: Status code, Country(DE), Latitude, Longitude, Schema  
    
3. Filter the response
    - Endpoint: http://api.citybik.es/v2/networks/<netwrok_id>?fields=id,name,href,location
    - Checks: Status code, Schema to contain only the fields in the query param

##### StepDefs
Includes the step definitions, assertions. 
Rest-assured framework has been used test and validate the citybik restful services.

##### TestRunner
The tests run with Junit Test Runner

#### Commands
```
You will need:
- Java 11 installed (Does not work with Java below 1.8 and JDK 14) [I ran it on JDK 11]
- Maven Installed 
- IDE
```
**Important: This suite should work on both windows and mac platforms however has only been tested on a Mac. 
If possible please use a Mac to execute the test suite**

In order to execute the tests navigate to the Project directory within a terminal/CMD window and 
run the command: 

```mvn clean test```

If you want to run the tests from IDE, run the TestRunner.class under `src/test/java/TestRunner.java`

#### Reports
All the reports  can be found under the directory `target/reports/`
If you like to view the html report, it can be found under `target/reports/report/index.html`


