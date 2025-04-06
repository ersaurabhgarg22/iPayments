package com.db.iPayments.service;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.model.Payment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BlacklistService {

    private final LogService logService;

    public BlacklistService(LogService logService) {
        this.logService = logService;
    }

    private static final List<String> BLACKLISTED_NAMES = Arrays.asList("Mark Imaginary", "Govind Real", "Shakil Maybe", "Chang Imagine");
    private static final List<String> BLACKLISTED_COUNTRIES = Arrays.asList("CUB", "IRQ", "IRN", "PRK", "SDN", "SYR");
    private static final List<String> BLACKLISTED_BANKS = Arrays.asList("BANK OF KUNLUN", "KARAMAY CITY COMMERCIAL BANK");
    private static final List<String> BLACKLISTED_INSTRUCTIONS = Arrays.asList("Artillery Procurement", "Lethal Chemicals payment");

    public boolean isFraudulent(Payment request) {
        if (BLACKLISTED_NAMES.contains(request.getPayerName())) {
            logService.logInfo("Fraudulent request detected: Payer name is blacklisted. Payer Name: " + request.getPayerName());
            return true;
        }

        if (BLACKLISTED_NAMES.contains(request.getPayeeName())) {
            logService.logInfo("Fraudulent request detected: Payee name is blacklisted. Payee Name: " + request.getPayeeName());
            return true;
        }

        if (BLACKLISTED_COUNTRIES.contains(request.getPayerCountryCode())) {
            logService.logInfo("Fraudulent request detected: Payer country is blacklisted. Payer Country: " + request.getPayerCountryCode());
            return true;
        }

        if (BLACKLISTED_COUNTRIES.contains(request.getPayeeCountryCode())) {
            logService.logInfo("Fraudulent request detected: Payee country is blacklisted. Payee Country: " + request.getPayeeCountryCode());
            return true;
        }

        if (BLACKLISTED_BANKS.contains(request.getPayerBank())) {
            logService.logInfo("Fraudulent request detected: Payer bank is blacklisted. Payer Bank: " + request.getPayerBank());
            return true;
        }

        if (BLACKLISTED_BANKS.contains(request.getPayeeBank())) {
            logService.logInfo("Fraudulent request detected: Payee bank is blacklisted. Payee Bank: " + request.getPayeeBank());
            return true;
        }

        if (BLACKLISTED_INSTRUCTIONS.contains(request.getPaymentInstruction())) {
            logService.logInfo("Fraudulent request detected: Payment instruction is blacklisted. Instruction: " + request.getPaymentInstruction());
            return true;
        }
        return false;
    }

}
