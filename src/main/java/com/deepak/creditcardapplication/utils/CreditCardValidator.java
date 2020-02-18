package com.deepak.creditcardapplication.utils;

import com.deepak.creditcardapplication.model.CreditCardRequestDTO;
import com.deepak.creditcardapplication.model.InvalidCreditCardNumberException;
import org.springframework.stereotype.Component;

@Component
public class CreditCardValidator {

    public void validate(CreditCardRequestDTO creditCardRequestDTO) throws InvalidCreditCardNumberException {
        //Simple no validation
        if (!creditCardRequestDTO.getCardNumber().matches(AppUtils.REGEX_FOR_NUMBER_ONLY)) {
            throw new InvalidCreditCardNumberException(AppUtils.CREDIT_CARD_NUMBER_SHOULD_BE_ONLY_A_NUMBER);
        }

        if (!isTheCreditCardNoValid(creditCardRequestDTO.getCardNumber())) {
            throw new InvalidCreditCardNumberException(AppUtils.CREDIT_CARD_LUHN_10_CHECK_FAILED_MSG);
        }
    }

    /*
     * Checks if the credit card number follows the Luhn 10  (Mod 10 Algo    )
     * Algo below
     * Step 1 - Reverse the digits to give a no n
     * Step 2 - Calculate sum of 1...n odd nos, s1
     * Step 3 - Get the even numbers (2, 4...) and multiply by 2
     * Step 4 - If any of the even nos is greater than 9 after multiplying by 2, add the individual nos
     * Step 5 - Sum all the nos in step 4 to get s2
     * Step 6 - if (s1+s2)%10 ==0, it is valid credit card no
     *
     * */
    private boolean isTheCreditCardNoValid(String creditCardNo) {
        //Todo revisit the logic and unit test it

        int sum1 = 0, sum2 = 0;

        StringBuffer originalString = new StringBuffer(creditCardNo.trim());

        //Step 1
        String reverseString = originalString.reverse().toString();

        for (int i = 0; i < reverseString.length(); i++) {

            int digit = Character.digit(reverseString.charAt(i), 10);

            //Step 2 and 3
            if (i % 2 == 0) { //since iterator is 0 based, we have odd nos at even nos
                sum1 += digit;
            } else { //even no * 2 , if no > 9 add individual nos and then to sum2
                int tempSum = 0;
                digit = 2 * digit;
                if (digit > 9) {
                    tempSum = digit / 10 + digit % 10;
                    sum2 += tempSum;
                } else {
                    sum2 += digit;
                }
            }

        }
        return (sum1 + sum2) % 10 == 0; //step 6
    }
}
