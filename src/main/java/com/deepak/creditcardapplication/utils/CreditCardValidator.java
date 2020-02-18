package com.deepak.creditcardapplication.utils;

import com.deepak.creditcardapplication.model.CreditCardRequestDTO;
import com.deepak.creditcardapplication.model.InvalidCreditCardNumberException;
import org.springframework.stereotype.Component;

@Component
public class CreditCardValidator {

    public void validate(CreditCardRequestDTO creditCardRequestDTO) throws InvalidCreditCardNumberException {
        //Simple no validation
        if (!creditCardRequestDTO.getCardNumber().matches("[0-9]+")) {
            throw new InvalidCreditCardNumberException(AppUtils.CREDIT_CARD_NUMBER_SHOULD_BE_ONLY_A_NUMBER);
        }

        //ToDo the validation for Luhn 10 check
        if (!isTheCreditCardNoValid(creditCardRequestDTO.getCardNumber())) {
            throw new InvalidCreditCardNumberException(AppUtils.CREDIT_CARD_LUHN_10_CHECK_FAILED_MSG);
        }
    }

    /*
     * Checks if the credit card number follows the Luhn 10  (Mod 10 Algo    )
     * */
    private boolean isTheCreditCardNoValid(String creditCardNo) {
        //Todo revisit the logic and unit test it
        int sum = 0;
        boolean alternate = false;
        for (int i = creditCardNo.length() - 1; i >= 0; i--) {
            int n = Integer.parseInt(creditCardNo.substring(i, i + 1));
            if (alternate) {
                n *= 2;
                if (n > 9) {
                    n = (n % 10) + 1;
                }
            }
            sum += n;
            alternate = !alternate;
        }
        return (sum % 10 == 0);
    }
}
