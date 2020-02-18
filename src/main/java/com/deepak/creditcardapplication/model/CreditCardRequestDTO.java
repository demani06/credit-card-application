package com.deepak.creditcardapplication.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreditCardRequestDTO {

    @NotEmpty(message = "Please provide card holder name")
    private String cardHolderName;
    @NotEmpty(message = "Please provide card number")
    @Size(min = 16, message = "Minimum size is 16")
    private String cardNumber;
    private long limit;

    @Override
    public String toString() {
        return "CreditCardRequestDTO{" +
                "cardHolderName='" + cardHolderName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", limit=" + limit +
                '}';
    }
}
