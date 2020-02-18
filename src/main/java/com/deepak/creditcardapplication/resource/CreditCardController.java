package com.deepak.creditcardapplication.resource;

import com.deepak.creditcardapplication.model.*;
import com.deepak.creditcardapplication.service.CreditCardService;
import com.deepak.creditcardapplication.utils.AppUtils;
import com.deepak.creditcardapplication.utils.CreditCardValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class CreditCardController {

    private CreditCardValidator creditCardValidator;

    private CreditCardService creditCardService;

    public CreditCardController(CreditCardValidator creditCardValidator, CreditCardService creditCardService) {
        this.creditCardValidator = creditCardValidator;
        this.creditCardService = creditCardService;
    }

    @GetMapping("/creditcards")
    public ResponseEntity<List<CreditCardResponseDTO>> getAllCreditCards() {
        log.debug("Getting all credit cards");

        List<CreditCard> allCreditCards = creditCardService.getAllCreditCards();

        //Return 204 in case of no credit card records in DB
        if (allCreditCards.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(AppUtils.getCreditCardResponsesFromModels(allCreditCards));
    }

    @PostMapping("/creditcards")
    public ResponseEntity<CreditCardResponseDTO> addNewCreditCard(@Valid @RequestBody CreditCardRequestDTO creditCardRequestDTO) throws InvalidCreditCardNumberException, DuplicateCreditCardNumberException {
        log.debug("Creating new credit card with the input = {} ", creditCardRequestDTO);

        //validate the request, any errors in validating are created as exceptions and handled by CreditCardControllerAdvice class
        creditCardValidator.validate(creditCardRequestDTO);

        //This is needed to abstract the real model to the outside world which is not needed
        CreditCard creditCard = AppUtils.createCreditCardModelFromRequest(creditCardRequestDTO);

        final CreditCard creditCardSaved = creditCardService.saveCreditCard(creditCard);

        log.debug("credit card saved ={}", creditCardSaved);

        CreditCardResponseDTO creditCardResponseDTO = AppUtils.getCreditCardResponseDTO(creditCardSaved);

        return new ResponseEntity<>(creditCardResponseDTO, HttpStatus.CREATED);
    }

}
