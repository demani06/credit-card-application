package com.deepak.creditcardapplication.utils;

import com.deepak.creditcardapplication.model.CommonErrorResponse;
import com.deepak.creditcardapplication.model.DuplicateCreditCardNumberException;
import com.deepak.creditcardapplication.model.InvalidCreditCardNumberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Arrays;
import java.util.List;

/*
 * Default exception handler for the complete application
 * */

@ControllerAdvice
@Slf4j
public class CreditCardControllerAdvice {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({InvalidCreditCardNumberException.class})
    public final ResponseEntity handleOfferNotFoundException(InvalidCreditCardNumberException exc) {
        log.info("InvalidCreditCardNumberException handle method");

        List<String> errorMessageDetails = Arrays.asList(exc.getLocalizedMessage());

        CommonErrorResponse commonErrorResponse = CommonErrorResponse
                .builder()
                .errorMessage(AppUtils.INVALID_CREDIT_CARD_NUMBER)
                .errorMessageDetails(errorMessageDetails)
                .build();

        return new ResponseEntity(commonErrorResponse, HttpStatus.BAD_REQUEST);

    }


    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler({DuplicateCreditCardNumberException.class})
    protected ResponseEntity handleDuplicateCreditCardNumberException(DuplicateCreditCardNumberException ex) {

        log.info("DuplicateCreditCardNumberException handle method");

        List<String> errorMessageDetails = Arrays.asList(ex.getLocalizedMessage());

        CommonErrorResponse commonErrorResponse = CommonErrorResponse
                .builder()
                .errorMessage(AppUtils.CREDIT_CARD_NUMBER_UNIQUE_CHECK_FAILED)
                .errorMessageDetails(errorMessageDetails).build();

        return new ResponseEntity(commonErrorResponse, HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {

        log.info("MethodArgumentNotValidException handle method");

        List<String> errorMessageDetails = Arrays.asList(ex.getLocalizedMessage());

        CommonErrorResponse commonErrorResponse = CommonErrorResponse
                .builder()
                .errorMessage("Invalid Arguments")
                .errorMessageDetails(errorMessageDetails).build();

        return new ResponseEntity(commonErrorResponse, HttpStatus.BAD_REQUEST);


    }
}
