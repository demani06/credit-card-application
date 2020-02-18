package com.deepak.creditcardapplication.model;


import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Builder
//@Getter and @Setter can be replaced by @Data but @Data was giving an exception
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = "cardNumber", name = "uniqueNameConstraint")}
)
public class CreditCard {

    @Id
    @Column(unique = true)
    private Long cardNumber;
    private String cardHolderName;
    private long cardLimit;
    private double balance;

    @Override
    public String toString() {
        return "CreditCard{" +
                "cardHolderName='" + cardHolderName + '\'' +
                ", cardNumber='" + cardNumber + '\'' +
                ", limit=" + cardLimit +
                ", balance=" + balance +
                '}';
    }

    /*
     * If two rows are equal that means that they have same credit no.
     * The system has unique key in place hence overriding equals to say if two objects are equal if they have same credit card numbers
     * */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CreditCard that = (CreditCard) o;
        return cardNumber.equals(that.cardNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cardNumber);
    }
}
