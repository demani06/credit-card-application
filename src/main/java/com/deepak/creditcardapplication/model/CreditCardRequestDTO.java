package com.deepak.creditcardapplication.model;

import lombok.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CreditCardRequestDTO {

    @NotEmpty(message = "Please provide card holder name")
    private String cardHolderName;
    @NotEmpty(message = "Please provide card number")
    @Size(max = 19, message = "Maximum size is 19 characters")
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
