package com.deepak.creditcardapplication.service;

import com.deepak.creditcardapplication.model.CreditCard;
import com.deepak.creditcardapplication.model.DuplicateCreditCardNumberException;

import java.util.List;

public interface CreditCardService {

    List<CreditCard> getAllCreditCards();

    CreditCard saveCreditCard(CreditCard card) throws DuplicateCreditCardNumberException;

}
