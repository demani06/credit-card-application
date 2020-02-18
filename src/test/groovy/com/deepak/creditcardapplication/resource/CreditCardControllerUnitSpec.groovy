package com.deepak.creditcardapplication.resource

import com.deepak.creditcardapplication.model.CreditCard
import com.deepak.creditcardapplication.model.CreditCardRequestDTO
import com.deepak.creditcardapplication.utils.CreditCardValidator
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

    def setup() {
        //Set up test data
        // Create a credit card
        CreditCard creditCard = CreditCard.builder()
                .cardHolderName("Johnny")
                .cardNumber("9090121898077890")
                .limit(2400L)
                .build()

        //TODO add these to the repository or to return

    }


    @Unroll("test the get creditcards endpoint")
    def "test the get creditcards endpoint"() {

        given: "Get creditcards end point exists"

        when: "When I call the end point get creditcards"

        ResultActions result = mockMvc.perform(get("/api/v1/creditcards")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())

        String content = result.andReturn().getResponse().getContentAsString()

        println("content=" + content)

        then: "It should return all credit cards"
        //TODO the assert is going to change after real implementation completed in the Controller class
        assert content.contains("{\"cardHolderName\":\"Raj\",\"cardNumber\":\"1456224536997899\",\"limit\":1500,\"balance\":2500.0}")

    }

    @Unroll("test the post creditcards endpoint - status check 201 created positive test case")
    def "test the post creditcards endpoint - status check 201 created positive test case"() {

        given: "Post creditcards end point exists"

        when: "the end point POST creditcards is called with a post request"

        /* ResultActions result = mockMvc.perform(get("/api/v1/creditcards")
                 .contentType(MediaType.APPLICATION_JSON))
                 .andExpect(status().isOk())*/

        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/creditcards")
                .content(asJsonString(new CreditCardRequestDTO("Julie", "1456224536997899", 2500)))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
        //.andExpect(MockMvcResultMatchers.jsonPath("$.employeeId").exists());


        then: "It should return all credit cards"

        //assert content.contains("{\"cardHolderName\":\"Raj\",\"cardNumber\":\"1456224536997899\",\"limit\":1500,\"balance\":2500.0}")

    }

}
