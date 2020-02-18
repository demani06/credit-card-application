package com.deepak.creditcardapplication.repository;

import com.deepak.creditcardapplication.model.CreditCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditCardRepository extends JpaRepository<CreditCard, Long> {
}
