**Endpoints**

| Request type   | endpoint | Sample request| Status codes
| ------------- | ------------- | -------| -------
| POST request to create a credit card  | /api/v1/creditcards  | { "cardHolderName":"Hello", "cardNumber": "1234567812345670", "limit": 3500 } | **201** - For Successful creation of credit card,<br /> **400** - for invalid bad requests (including Luhn 10 check) and duplicate credit card requests, <br /> **500** - for any other server issues
| GET  request to get all credit card | /api/v1/creditcards  | | **200** - For Successful retrieval of credit card,<br /> **204** - For no data requests, <br /> **500** - for any other server issues


**Assumptions/Decisions taken**

1. Used H2 as embedded database for testing, this means that the data is saved only till the server is up and is lost after shutdown
2. Used Spock groovy for test cases which aid more readability and data tables
3. Used Lombok for auto-generation of code and Sl4j implementation for logging
4. Application runs on port 9099 (chosen an arbitrary port and it can be changed in the application.properties)
5. No Security configured


**Build and run the application** (from the basedir - for example - C:\work\credit-card-application)

- To build the application
```mvn clean install ```
- To build and generate the test report and report gets generated in the folder - {basedir}/target/site/jacoco/index.html
```mvn clean test```
- To run the application
```mvn spring-boot:run ```

**Swagger Integration**

Swagger is integrated in the application and Swagger UI can be accessed  at the URL once the server is up -  ```http://localhost:9099/swagger-ui.html ```

**Few caveats**

+ Test cases covering the max length of 19 for credit card and '0' balance for new cards are covered as part of integration cases
+ data.sql and schema.sql are created for this test purpose so that on start of the application, the GET request would give some records. This would not be used in production.

**Testing**
+ GET request to the endpoint ```http://localhost:9099/api/v1/creditcards``` should give 3 records (records are inserted using data.sql on start of the server)
+ Below sample valid POST request to the endpoint ```http://localhost:9099/api/v1/creditcards``` should give a 201 response <br />
	{<br />
		"cardHolderName":"Raj", <br />
		"cardNumber": "1234567812345670", <br />
		"limit": 3500<br />
	}
 




