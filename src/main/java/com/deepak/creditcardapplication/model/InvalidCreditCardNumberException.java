package com.deepak.creditcardapplication.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidCreditCardNumberException extends Exception {

    public InvalidCreditCardNumberException(String message) {
        super(message);
    }
}
