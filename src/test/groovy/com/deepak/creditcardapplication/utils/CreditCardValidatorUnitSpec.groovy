package com.deepak.creditcardapplication.utils

import com.deepak.creditcardapplication.model.CreditCardRequestDTO
import com.deepak.creditcardapplication.model.InvalidCreditCardNumberException
import org.springframework.boot.test.context.SpringBootTest
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
class CreditCardValidatorUnitSpec extends Specification {

    @Subject
    CreditCardValidator validator

    def setup() {
        validator = new CreditCardValidator()
    }

    @Unroll("test the validate method - #scenario")
    def "test the validate method"() {

        given: "Validation method exists to validate the input request and different requests are sent to this method"

        CreditCardRequestDTO creditCardRequestDTO = CreditCardRequestDTO
                .builder()
                .cardHolderName(cardHolderName)
                .cardNumber(creditCardNumber)
                .limit(limit)
                .build()

        when: "When I call this method"

        validator.validate(creditCardRequestDTO)

        then: "It should validate the credit card number for different scenarios"

        def ex = thrown(InvalidCreditCardNumberException)
        ex.message == expectedMessage

        where:
        scenario                      || creditCardNumber   || cardHolderName || limit || expectedMessage
        'LUHN 10 check -ve case'      || '2233232'          || 'Boon'         || 2500  || AppUtils.CREDIT_CARD_LUHN_10_CHECK_FAILED_MSG
        //'LUHN 10 check +ve case'      || '1234567812345670' || 'David'        || 2500  || null
        'creditcard aphanumeric test' || '123456a812345670' || 'David'        || 2500  || AppUtils.CREDIT_CARD_NUMBER_SHOULD_BE_ONLY_A_NUMBER

    }

}
