package com.deepak.creditcardapplication.service;

import com.deepak.creditcardapplication.model.CreditCard;

import java.util.Set;

public interface CreditCardService {

    Set<CreditCard> getAllCreditCards();

    CreditCard saveCreditCard();

}
