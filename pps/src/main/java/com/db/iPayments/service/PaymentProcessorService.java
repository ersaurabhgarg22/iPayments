package com.db.iPayments.service;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.model.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public class PaymentProcessorService {

    private final PaymentValidatorService validatorService;
    private final LogService logService;
    private final BrokerInvokerService brokerInvokerService;

    public PaymentProcessorService(PaymentValidatorService validatorService, LogService logService, BrokerInvokerService brokerInvokerService) {
        this.validatorService = validatorService;
        this.logService = logService;
        this.brokerInvokerService = brokerInvokerService;
    }

    public String processPayment(Payment payment) throws JsonProcessingException {
        if (!validatorService.validate(payment)) {
            logService.logError("Payment validation failed", null);
            return "Invalid payment details";
        }
        logService.logInfo("Valid payment. Sending to broker system.");
        brokerInvokerService.sendForFraudCheck(payment);
        return "Payment sent for fraud check";
    }

    public String processPaymentAPI(Payment payment) throws JsonProcessingException {
        if (!validatorService.validate(payment)) {
            logService.logError("Payment validation failed", null);
            return "Invalid payment details";
        }
        logService.logInfo("Valid payment. Sending to broker system.");
        brokerInvokerService.sendForFraudCheckAPI(payment);
        return "Payment sent for fraud check";
    }
}
