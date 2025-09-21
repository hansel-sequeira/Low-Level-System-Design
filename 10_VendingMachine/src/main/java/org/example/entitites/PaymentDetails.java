package org.example.entitites;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.example.enums.PAYMENT_MODE;

@Getter
@Setter
@AllArgsConstructor
public class PaymentDetails {
    private PAYMENT_MODE paymentMode;
    private int inputValue;
}
