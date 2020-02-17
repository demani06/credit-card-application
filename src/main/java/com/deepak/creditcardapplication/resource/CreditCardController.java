package com.deepak.creditcardapplication.resource;


import com.deepak.creditcardapplication.model.CreditCard;
import com.deepak.creditcardapplication.model.CreditCardRequestDTO;
import com.deepak.creditcardapplication.model.CreditCardResponseDTO;
import com.deepak.creditcardapplication.utils.AppUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class CreditCardController {

    @GetMapping("/creditcards")
    public ResponseEntity<Set<CreditCardResponseDTO>> getAllCreditCards() {
        log.debug("Getting all credit cards");

        //Todo change the output

        CreditCardResponseDTO obj1 = CreditCardResponseDTO
                .builder()
                .cardHolderName("Raj")
                .limit(1500L)
                .cardNumber("1456224536997899")
                .balance(2500)
                .build();

        Set<CreditCardResponseDTO> cardResponseDTOSet = new HashSet<>();
        cardResponseDTOSet.add(obj1);

        return ResponseEntity.ok(cardResponseDTOSet);
    }


    @PostMapping("/creditcards")
    public ResponseEntity<CreditCardResponseDTO> addNewCreditCard(@RequestBody CreditCardRequestDTO creditCardRequestDTO) {
        log.debug("Creating new credit card with the input = {} ", creditCardRequestDTO);

        //Todo validate the request
        CreditCard creditCard = AppUtils.validateAndCreateCreditCardModel(creditCardRequestDTO);

        log.debug("Credit Card model to be saved = "+ creditCard);

        //Todo persist the Credit card model in the DB

        CreditCardResponseDTO creditCardResponseDTO = AppUtils.getCreditCardResponseDTO(creditCard);

        return ResponseEntity.ok(creditCardResponseDTO);
    }



}
