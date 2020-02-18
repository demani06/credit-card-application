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

        //Todo change the output

        //todo return 204 no content in case there are no reocrds

        List<CreditCard> allCreditCards = creditCardService.getAllCreditCards();

        log.debug("allCreditCards={}", allCreditCards);
        //Return 204
        if (allCreditCards.isEmpty()) {
            return ResponseEntity.noContent().build();
        }


        return ResponseEntity.ok(AppUtils.getCreditCardResponsesFromModels(allCreditCards));
    }


    @PostMapping("/creditcards")
    public ResponseEntity<CreditCardResponseDTO> addNewCreditCard(@Valid @RequestBody CreditCardRequestDTO creditCardRequestDTO) throws InvalidCreditCardNumberException, DuplicateCreditCardNumberException {
        log.debug("Creating new credit card with the input = {} ", creditCardRequestDTO);

        //Todo validate the request
        creditCardValidator.validate(creditCardRequestDTO);

        //This is needed to abstract the real model to the outside world which is not needed
        CreditCard creditCard = AppUtils.createCreditCardModelFromRequest(creditCardRequestDTO);

        log.debug("Credit Card model to be saved =  {}", creditCard);

        //Todo persist the Credit card model in the DB
        final CreditCard creditCardSaved = creditCardService.saveCreditCard(creditCard);

        log.debug("Credit Card model saved in the DB = {}", creditCardSaved);

        CreditCardResponseDTO creditCardResponseDTO = AppUtils.getCreditCardResponseDTO(creditCardSaved);

        return new ResponseEntity<>(creditCardResponseDTO, HttpStatus.CREATED);
    }



}
