package com.db.iPayments.service;

import com.db.iPayments.model.Payment;
import org.springframework.stereotype.Service;

import static com.db.iPayments.Utils.Utils.*;

@Service
public class PaymentValidatorService {
    public boolean validate(Payment payment) {
        return isNotNullOrEmpty(payment.getPayerName()) && isNotNullOrEmpty(payment.getPayerBank()) &&
                isNotNullOrEmpty(payment.getPayerCountryCode()) && isNotNullOrEmpty(payment.getPayerAccount()) &&
                isNotNullOrEmpty(payment.getPayeeName()) && isNotNullOrEmpty(payment.getPayeeBank()) &&
                isNotNullOrEmpty(payment.getPayeeCountryCode()) && isNotNullOrEmpty(payment.getPayeeAccount()) &&
                isNotNullOrEmpty(payment.getCurrency()) &&
                isValidISOAlpha3(payment.getPayerCountryCode()) && isValidISOAlpha3(payment.getPayeeCountryCode())
                && isValidISO4217(payment.getCurrency()) && isValidAmount(payment.getAmount());
    }
}
