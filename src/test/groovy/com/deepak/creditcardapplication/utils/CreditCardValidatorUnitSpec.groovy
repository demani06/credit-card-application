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

    @Unroll("test the validate method for negative cases- #scenario")
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
        'creditcard aphanumeric test' || '123456a812345670' || 'David'        || 2500  || AppUtils.CREDIT_CARD_NUMBER_SHOULD_BE_ONLY_A_NUMBER
        'creditcard with space test'  || '123 6812345670'   || 'David'        || 2500  || AppUtils.CREDIT_CARD_NUMBER_SHOULD_BE_ONLY_A_NUMBER

    }

    @Unroll("test the validate method for positive scenarios- #scenario")
    def "test the validate method for positive scenarios"() {

        given: "Validation method exists to validate the input request and different requests are sent to this method"

        CreditCardRequestDTO creditCardRequestDTO = CreditCardRequestDTO
                .builder()
                .cardHolderName(cardHolderName)
                .cardNumber(creditCardNumber)
                .limit(limit)
                .build()

        when: "When I call this method"

        validator.validate(creditCardRequestDTO)

        then: "It should validate the credit card number for different scenarios and should not throw an exception"

        noExceptionThrown()

        where:
        scenario                                        || creditCardNumber   || cardHolderName || limit
        'LUHN 10 check +ve case1'                       || '1234567812345670' || 'David'        || 2500
        'LUHN 10 check +ve case2'                       || '6011111111111117' || 'Verron'       || 2500
        'LUHN 10 check +ve case3-Discover'              || '6011000990139424' || 'Henry'        || 2500
        'LUHN 10 check +ve case4-Visa'                  || '4111111111111111' || 'William'      || 2500
        'LUHN 10 check +ve case5-Paymentech'            || '6331101999990016' || 'Neesham'      || 2500
        'LUHN 10 check +ve case6-MasterCard'            || '5105105105105100' || 'Trent'        || 2500
        'LUHN 10 check +ve case7-MasterCard'            || '5555555555554444' || 'Adam'         || 2500
        'LUHN 10 check +ve case8 - Australian BankCard' || '5610591081018250' || 'Ricky'        || 2500

    }

}
