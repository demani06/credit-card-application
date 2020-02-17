package com.deepak.creditcardapplication.resource

import com.deepak.creditcardapplication.model.CreditCard
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [CreditCardController])
class CreditCardControllerUnitSpec extends Specification{

    @Autowired
    private MockMvc mockMvc

    def setup() {
        //Set up test data
        // Create a credit card
        CreditCard creditCard = CreditCard.builder()
                .cardHolderName("Johnny")
                .cardNumber("9090121898077890")
                .limit(2400L)
                .build();


    }


    @Unroll("test the get creditcards endpoint")
    def "test the get creditcards endpoint"() {

        given: "Get creditcards end point exists"

        when: "When I call the end point get creditcards"

        ResultActions result = mockMvc.perform(get("/api/v1/creditcards")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

        String content = result.andReturn().getResponse().getContentAsString();

        println("content=" + content)

        then: "It should return all credit cards"

        assert content.contains("{\"cardHolderName\":\"Raj\",\"cardNumber\":\"1456224536997899\",\"limit\":1500,\"balance\":2500.0}")

    }

   /* @Unroll("test the post creditcards endpoint - validate the credit card number")
    def "test the post creditcards endpoint - validate the credit card number"() {

        given: "Post creditcards end point exists"

        when: "the end point POST creditcards is called with a post request"

        ResultActions result = mockMvc.perform(get("/api/v1/creditcards")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())

        String content = result.andReturn().getResponse().getContentAsString();

        println("content=" + content)

        then: "It should return all credit cards"

        assert content.contains("{\"cardHolderName\":\"Raj\",\"cardNumber\":\"1456224536997899\",\"limit\":1500,\"balance\":2500.0}")

    }*/

}
