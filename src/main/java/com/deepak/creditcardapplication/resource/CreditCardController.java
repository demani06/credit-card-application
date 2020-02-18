package com.deepak.creditcardapplication.resource;

import com.deepak.creditcardapplication.model.*;
import com.deepak.creditcardapplication.service.CreditCardService;
import com.deepak.creditcardapplication.utils.AppUtils;
import com.deepak.creditcardapplication.utils.CreditCardValidator;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
@Api(value = "Credit Card Rest API")
public class CreditCardController {

    private CreditCardValidator creditCardValidator;

    private CreditCardService creditCardService;

    public CreditCardController(CreditCardValidator creditCardValidator, CreditCardService creditCardService) {
        this.creditCardValidator = creditCardValidator;
        this.creditCardService = creditCardService;
    }

    @ApiOperation(value = "Get a list of all credit cards in the system", response = List.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of credit cards"),
            @ApiResponse(code = 204, message = "Request was sucessful, but retrieved empty credit cards from the system")
    })
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

    @ApiOperation(value = "Create a new Credit card", response = CreditCardResponseDTO.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successfully created credit card in the system"),
            @ApiResponse(code = 400, message = "Either validation failed or a bad request")
    })
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
