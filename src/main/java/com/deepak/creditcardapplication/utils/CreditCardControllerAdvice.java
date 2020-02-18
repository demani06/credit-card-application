package com.deepak.creditcardapplication.utils;

import com.deepak.creditcardapplication.model.CommonErrorResponse;
import com.deepak.creditcardapplication.model.InvalidCreditCardNumberException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;

import java.util.*;
import java.util.stream.Collectors;

/*
 * Default exception handler
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
                .errorMessage("Invalid Credit Card number")
                .errorMessageDetails(errorMessageDetails).build();

        return new ResponseEntity(commonErrorResponse, HttpStatus.BAD_REQUEST);

    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler({MethodArgumentNotValidException.class})
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatus status, WebRequest request) {

        log.info("MethodArgumentNotValidException handle method");

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", new Date());
        body.put("status", status.value());

        //Get all errors
        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(x -> x.getDefaultMessage())
                .collect(Collectors.toList());

        body.put("errors", errors);

        return new ResponseEntity<>(body, headers, status);

    }
}
