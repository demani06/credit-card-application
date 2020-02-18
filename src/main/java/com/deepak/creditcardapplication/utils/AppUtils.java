package com.deepak.creditcardapplication.utils;


import com.deepak.creditcardapplication.model.CreditCard;
import com.deepak.creditcardapplication.model.CreditCardRequestDTO;
import com.deepak.creditcardapplication.model.CreditCardResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AppUtils {

    public static final String CREDIT_CARD_LUHN_10_CHECK_FAILED_MSG = "Credit Card Luhn 10 check failed";
    public static final String CREDIT_CARD_NUMBER_SHOULD_BE_ONLY_A_NUMBER = "Credit Card number should be only a number";

    public static CreditCard createCreditCardModelFromRequest(CreditCardRequestDTO creditCardRequestDTO) {

        //Assuming the request is already validated
        return CreditCard.builder()
                .cardHolderName(creditCardRequestDTO.getCardHolderName())
                .cardNumber(creditCardRequestDTO.getCardNumber())
                .limit(creditCardRequestDTO.getLimit())
                .balance(0)//default balance
                .build();

    }

    public static CreditCardResponseDTO getCreditCardResponseDTO(CreditCard creditCard) {

        return CreditCardResponseDTO.builder()
                .cardHolderName(creditCard.getCardHolderName())
                .cardNumber(creditCard.getCardNumber())
                .limit(creditCard.getLimit())
                .balance(0)//default balance
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
