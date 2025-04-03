package com.db.iPayments.Utils;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Currency;
import java.util.Locale;
import java.util.UUID;

public class Utils {

    public static String generateUUID() {
        return UUID.randomUUID().toString();
    }

    public static String getISOUTCTimestamp() {
        return Instant.now().atOffset(ZoneOffset.UTC)
                .format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'"));
    }

    public static String getISO8601Date() {
        return LocalDate.now().format(DateTimeFormatter.ISO_DATE);
    }

    public static boolean isNotNullOrEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isValidISOAlpha3(String code) {
        for (String country : Locale.getISOCountries()) {
            Locale locale = new Locale("", country);
            if (locale.getISO3Country().equalsIgnoreCase(code)) {
                return true;
            }
        }
        return false;
    }

    public static boolean isValidISO4217(String currencyCode) {
        try {
            Currency.getInstance(currencyCode);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    public static boolean isValidAmount(double amount) {
        BigDecimal bd = BigDecimal.valueOf(amount);
        return bd.scale() == 2;
    }
}
