package com.db.iPayments.service;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.model.Payment;
import org.springframework.stereotype.Service;

import static com.db.iPayments.Utils.Utils.*;

@Service
public class PaymentValidatorService {

    private final LogService logService;

    public PaymentValidatorService(LogService logService) {
        this.logService = logService;
    }

    public boolean validate(Payment payment) {

        if (!isNotNullOrEmpty(payment.getPayerName())) {
            logService.logInfo("Validation failed: Payer Name is empty or null.");
            return false;
        }
        if (!isNotNullOrEmpty(payment.getPayerBank())) {
            logService.logInfo("Validation failed: Payer Bank is empty or null.");
            return false;
        }
        if (!isNotNullOrEmpty(payment.getPayerCountryCode())) {
            logService.logInfo("Validation failed: Payer Country Code is empty or null.");
            return false;
        }
        if (!isValidISOAlpha3(payment.getPayerCountryCode())) {
            logService.logInfo("Validation failed: Payer Country Code " + payment.getPayerCountryCode() + " is not a valid ISO Alpha-3 code.");
            return false;
        }
        if (!isNotNullOrEmpty(payment.getPayerAccount())) {
            logService.logInfo("Validation failed: Payer Account is empty or null.");
            return false;
        }

        if (!isNotNullOrEmpty(payment.getPayeeName())) {
            logService.logInfo("Validation failed: Payee Name is empty or null.");
            return false;
        }
        if (!isNotNullOrEmpty(payment.getPayeeBank())) {
            logService.logInfo("Validation failed: Payee Bank is empty or null.");
            return false;
        }
        if (!isNotNullOrEmpty(payment.getPayeeCountryCode())) {
            logService.logInfo("Validation failed: Payee Country Code is empty or null.");
            return false;
        }
        if (!isValidISOAlpha3(payment.getPayeeCountryCode())) {
            logService.logInfo("Validation failed: Payee Country Code " + payment.getPayeeCountryCode() + " is not a valid ISO Alpha-3 code.");
            return false;
        }
        if (!isNotNullOrEmpty(payment.getPayeeAccount())) {
            logService.logInfo("Validation failed: Payee Account is empty or null.");
            return false;
        }
        if (!isNotNullOrEmpty(payment.getCurrency())) {
            logService.logInfo("Validation failed: Currency is empty or null.");
            return false;
        }
        if (!isValidISO4217(payment.getCurrency())) {
            logService.logInfo("Validation failed: Currency " + payment.getCurrency() + " is not a valid ISO 4217 code.");
            return false;
        }
        if (!isValidAmount(payment.getAmount())) {
            logService.logInfo("Validation failed: Amount " + payment.getAmount() + " is not valid.");
            return false;
        }

        return true;

    }
}
