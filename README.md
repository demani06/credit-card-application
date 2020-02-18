Endpoints 

| Request type   | endpoint | 
| ------------- | ------------- | 
| POST request to create a credit card  | /api/v1/creditcards  |   
| GET  request to get all credit card | /api/v1/creditcards  |


Assumptions/Decisions taken

1. Using HSQL embedded database
2. Used Spock groovy for test cases which aid more readability
3. Used Lombok for auto-generation of code and Sl4j implementation

Build and run the application

- To build the application
```mvn clean install ```
- To build and generate the test report and report gets generated in the folder - {basedir}/target/site/surefire-report.html
```mvn clean install surefire-report:report```
- To run the application
```mvn spring-boot:run ```

  
