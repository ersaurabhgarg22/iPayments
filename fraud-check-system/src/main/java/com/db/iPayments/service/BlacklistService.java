package com.db.iPayments.service;

import com.db.iPayments.model.Payment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class BlacklistService {

    private static final List<String> BLACKLISTED_NAMES = Arrays.asList("Mark Imaginary", "Govind Real", "Shakil Maybe", "Chang Imagine");
    private static final List<String> BLACKLISTED_COUNTRIES = Arrays.asList("CUB", "IRQ", "IRN", "PRK", "SDN", "SYR");
    private static final List<String> BLACKLISTED_BANKS = Arrays.asList("BANK OF KUNLUN", "KARAMAY CITY COMMERCIAL BANK");
    private static final List<String> BLACKLISTED_INSTRUCTIONS = Arrays.asList("Artillery Procurement", "Lethal Chemicals payment");

    public boolean isFraudulent(Payment request) {
        return BLACKLISTED_NAMES.contains(request.getPayerName()) ||
                BLACKLISTED_NAMES.contains(request.getPayeeName()) ||
                BLACKLISTED_COUNTRIES.contains(request.getPayerCountryCode()) ||
                BLACKLISTED_COUNTRIES.contains(request.getPayeeCountryCode()) ||
                BLACKLISTED_BANKS.contains(request.getPayerBank()) ||
                BLACKLISTED_BANKS.contains(request.getPayeeBank()) ||
                BLACKLISTED_INSTRUCTIONS.contains(request.getPaymentInstruction());
    }

}
