package com.deepak.creditcardapplication.utils;


import com.deepak.creditcardapplication.model.CreditCard;
import com.deepak.creditcardapplication.model.CreditCardRequestDTO;
import com.deepak.creditcardapplication.model.CreditCardResponseDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.stream.Collectors;

public class AppUtils {

    public static final String CREDIT_CARD_LUHN_10_CHECK_FAILED_MSG = "Credit Card Luhn 10 check failed";
    public static final String CREDIT_CARD_NUMBER_SHOULD_BE_ONLY_A_NUMBER = "Credit Card number should be only a number";
    public static final String CREDIT_CARD_NUMBER_UNIQUE_CHECK_FAILED = "Credit Card number unique check failed";
    public static final String INVALID_CREDIT_CARD_NUMBER = "Invalid Credit Card number";

    public static CreditCard createCreditCardModelFromRequest(CreditCardRequestDTO creditCardRequestDTO) {

        //Assuming the request is already validated
        return CreditCard.builder()
                .cardHolderName(creditCardRequestDTO.getCardHolderName())
                .cardNumber(Long.parseLong(creditCardRequestDTO.getCardNumber()))
                .cardLimit(creditCardRequestDTO.getLimit())
                .balance(0)//default balance
                .build();

    }

    public static CreditCardResponseDTO getCreditCardResponseDTO(CreditCard creditCard) {

        return CreditCardResponseDTO.builder()
                .cardHolderName(creditCard.getCardHolderName())
                .cardNumber(String.valueOf(creditCard.getCardNumber()))
                .limit(creditCard.getCardLimit())
                .balance(0)//default balance
                .build();
    }

    public static String asJsonString(final Object obj) {
        try {
            String valueAsString = new ObjectMapper().writeValueAsString(obj);
            return valueAsString;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static List<CreditCardResponseDTO> getCreditCardResponsesFromModels(List<CreditCard> allCreditCards) {
        return allCreditCards.stream().map(AppUtils::getCreditCardResponseDTO).collect(Collectors.toList());
    }
}
