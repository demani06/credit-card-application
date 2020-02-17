package com.deepak.creditcardapplication.utils;


import com.deepak.creditcardapplication.model.CreditCard;
import com.deepak.creditcardapplication.model.CreditCardRequestDTO;
import com.deepak.creditcardapplication.model.CreditCardResponseDTO;

public class AppUtils {

    public static CreditCard validateAndCreateCreditCardModel(CreditCardRequestDTO creditCardRequestDTO) {

        //Assuming the request is already validated
        CreditCard creditCard = CreditCard.builder()
                                        .cardHolderName(creditCardRequestDTO.getCardHolderName())
                                        .cardNumber(creditCardRequestDTO.getCardNumber())
                                        .limit(creditCardRequestDTO.getLimit())
                                        .balance(0)//default balance
                                        .build();


        return creditCard;

    }

    public static CreditCardResponseDTO getCreditCardResponseDTO(CreditCard creditCard) {

        CreditCardResponseDTO creditCardResponseDTO = CreditCardResponseDTO.builder()
                .cardHolderName(creditCard.getCardHolderName())
                .cardNumber(creditCard.getCardNumber())
                .limit(creditCard.getLimit())
                .balance(0)//default balance
                .build();

        return creditCardResponseDTO;
    }
}
