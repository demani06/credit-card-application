package com.deepak.creditcardapplication.model;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreditCardResponseDTO {

    private String cardHolderName;
    private String cardNumber;
    private long limit;
    private double balance;
}
