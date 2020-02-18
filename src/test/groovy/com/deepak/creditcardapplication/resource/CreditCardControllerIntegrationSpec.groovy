package com.deepak.creditcardapplication.resource

import com.deepak.creditcardapplication.CreditCardApplication
import com.deepak.creditcardapplication.model.CreditCard
import com.deepak.creditcardapplication.model.CreditCardRequestDTO
import com.deepak.creditcardapplication.repository.CreditCardRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.client.TestRestTemplate
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.http.*
import spock.lang.Specification
import spock.lang.Unroll

@SpringBootTest(classes = CreditCardApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CreditCardControllerIntegrationSpec extends Specification {

    @LocalServerPort
    private int port

    @Autowired
    CreditCardRepository cardRepository

    TestRestTemplate restTemplate = new TestRestTemplate()

    CreditCard creditCard1
    CreditCard creditCard2
    CreditCard creditCard3

    def setup() {
        //Set up test data
        // Create a credit card

    }

    @Unroll("test the get creditcards endpoint for different status codes")
    def "test the get creditcards endpoint for different status codes"() {

        HttpHeaders headers = new HttpHeaders()

        given: "Get creditcards end point exists"

        when: "When I call the end point get creditcards"

        HttpEntity<String> entity = new HttpEntity<String>(null, headers)

        //conditionCheck to insert records into DB so that it will return true
        if (conditionCheck) {
            //Create and save a model to test duplicate credit key exception
            creditCard3 = CreditCard.builder()
                    .cardHolderName("Jennifer")
                    .cardNumber(6331101999990016)
                    .cardLimit(4500)
                    .balance(0.0)
                    .build()

            cardRepository.save(creditCard3)
        }
        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("/api/v1/creditcards"),
                HttpMethod.GET, entity, List.class)

        println("response=" + response)

        then: "It should return different status codes based on the conditions from the database"
        assert response.getStatusCode() == httpStatus

        where:
        scenario           || conditionCheck || httpStatus
        '204 status code ' || false          || HttpStatus.NO_CONTENT
        '200 status code ' || true           || HttpStatus.OK

    }

    @Unroll("Integration test the post creditcards endpoint for different status codes ~ scenario")
    def "Integration test the post creditcards endpoint for status code ~ scenario"() {

        HttpHeaders headers = new HttpHeaders()

        CreditCardRequestDTO creditCardRequestDTO = CreditCardRequestDTO
                .builder()
                .cardHolderName(cardHolderName)
                .cardNumber(creditCardNumber)
                .limit(limit)
                .build()

        given: "POST creditcards end point exists"

        //Create and save a model to test duplicate credit key exception
        creditCard3 = CreditCard.builder()
                .cardHolderName("Jennifer")
                .cardNumber(6331101999990016)
                .cardLimit(4500)
                .balance(0.0)
                .build()

        cardRepository.save(creditCard3)

        when: "When I call the post end point get creditcards"

        HttpEntity<CreditCardRequestDTO> entity = new HttpEntity<CreditCardRequestDTO>(creditCardRequestDTO, headers)

        ResponseEntity response = restTemplate.exchange(
                createURLWithPort("/api/v1/creditcards"),
                HttpMethod.POST, entity, String.class)

        println("response=" + response)

        then: "It should return different response codes based on the input"
        assert response.getStatusCode() == httpStatus

        where:
        scenario                                                 || creditCardNumber   || cardHolderName || limit || httpStatus
        '201 status code case1'                                  || '1234567812345670' || 'David'        || 2500  || HttpStatus.CREATED
        '400 status code case with invalid credit card number'   || '1234567812345671' || 'David'        || 2500  || HttpStatus.BAD_REQUEST
        '400 status code case with duplicate credit card number' || '6331101999990016' || 'Jennifer'     || 4500  || HttpStatus.BAD_REQUEST


    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri
    }


}
