package com.deepak.creditcardapplication.model;


import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCard {

    private String cardHolderName;
    private String cardNumber;
    private long limit;
    private double balance;

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardHolderName='" + cardHolderName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", limit=" + limit +
                ", balance=" + balance +
                '}';
    }
}
