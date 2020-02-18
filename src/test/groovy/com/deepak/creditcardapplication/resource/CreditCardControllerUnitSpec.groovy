package com.deepak.creditcardapplication.resource

import com.deepak.creditcardapplication.model.CreditCard
import com.deepak.creditcardapplication.model.CreditCardRequestDTO
import com.deepak.creditcardapplication.service.CreditCardService
import com.deepak.creditcardapplication.utils.CreditCardValidator
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import spock.lang.Specification
import spock.lang.Unroll

import static com.deepak.creditcardapplication.utils.AppUtils.asJsonString
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(controllers = [CreditCardController])
class CreditCardControllerUnitSpec extends Specification {

    @Autowired
    private MockMvc mockMvc

    @MockBean
    private CreditCardValidator creditCardValidator

    @SpringBean
    private CreditCardService creditCardService = Mock()

    CreditCard creditCard1
    CreditCard creditCard2
    CreditCard creditCard3

    def setup() {
        //Set up test data
        // Create a credit card
        creditCard1 = CreditCard.builder()
                .cardHolderName("Johnny")
                .cardNumber(9090121898077890)
                .cardLimit(2400L)
                .build()

        creditCard2 = CreditCard.builder()
                .cardHolderName("Julie")
                .cardNumber(9090121898077867)
                .cardLimit(3500L)
                .build()

        creditCard3 = CreditCard.builder()
                .cardHolderName("Jennifer")
                .cardNumber(5555555555554444)
                .cardLimit(4500)
                .balance(0.0)
                .build()


    }


    @Unroll("test the get creditcards endpoint for 200 status code")
    def "test the get creditcards endpoint for 200 status code"() {

        given: "Get creditcards end point exists"
        creditCardService.getAllCreditCards() >> Arrays.asList(creditCard1, creditCard2)

        when: "When I call the end point get creditcards"

        ResultActions result = mockMvc.perform(get("/api/v1/creditcards")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())

        String content = result.andReturn().getResponse().getContentAsString()

        println("content=" + content)

        then: "It should return all credit cards from the mocked service class"
        assert content.contains("{\"cardHolderName\":\"Johnny\",\"cardNumber\":\"9090121898077890\",\"limit\":2400,\"balance\":0.0},{\"cardHolderName\":\"Julie\",\"cardNumber\":\"9090121898077867\",\"limit\":3500,\"balance\":0.0}")

    }

    @Unroll("test the get creditcards endpoint for 204 empty list")
    def "test the get creditcards endpoint for 204 empty list"() {

        given: "Get creditcards end point exists and no credit cards return from the mocked class"
        creditCardService.getAllCreditCards() >> Collections.emptyList()

        when: "When I call the end point get creditcards"

        ResultActions result = mockMvc.perform(get("/api/v1/creditcards")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())

        then: "It should return all credit cards from the mocked service class"

    }

    @Unroll("test the post creditcards endpoint - status check 201 created positive test case")
    def "test the post creditcards endpoint - status check 201 created positive test case"() {

        given: "Post creditcards end point exists"
        println("creditCard3 =" + creditCard3)
        creditCardService.saveCreditCard(creditCard3) >> creditCard3

        when: "the end point POST creditcards is called with a post request"

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/creditcards")
                .content(asJsonString(new CreditCardRequestDTO("Jennifer", "5555555555554444", 4500)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())


        then: "It should return the credit card which got saved"

        //assert content.contains("{\"cardHolderName\":\"Raj\",\"cardNumber\":\"1456224536997899\",\"limit\":1500,\"balance\":2500.0}")

    }

}
