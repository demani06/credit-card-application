package com.deepak.creditcardapplication.service;

import com.deepak.creditcardapplication.model.CreditCard;
import com.deepak.creditcardapplication.model.DuplicateCreditCardNumberException;
import com.deepak.creditcardapplication.repository.CreditCardRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class CreditCardServiceImpl implements CreditCardService {

    @Autowired
    CreditCardRepository creditCardRepository;

    @Override
    public List<CreditCard> getAllCreditCards() {
        return creditCardRepository.findAll();
    }

    @Override
    public CreditCard saveCreditCard(CreditCard card) throws DuplicateCreditCardNumberException {

        log.info("Checking if there is a credit card with the same no , no = {}", card.getCardNumber());
        //Duplicate credit card check
        if (creditCardRepository.existsById(card.getCardNumber())) {
            //raise an exception
            log.error("Credit number with the number {} exists in the application", card.getCardNumber());
            throw new DuplicateCreditCardNumberException("Credit number with the number " + card.getCardNumber() + " exists in the application");
        }

        return creditCardRepository.saveAndFlush(card);
    }
}
