package com.db.iPayments.messaging;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.model.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static com.db.iPayments.Utils.Utils.getISO8601Date;

@Component
public class ActiveMQConsumer {

    private final LogService logService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public ActiveMQConsumer(LogService logService) {
        this.logService = logService;
    }

    @JmsListener(destination = "ppsQueue", containerFactory = "defaultJmsListenerContainerFactory")
    public void receiveMessageFromPPSQueue(String message) {
        logService.logInfo("Received message from PPS queue: " + message);
        try {
            Payment payment = objectMapper.readValue(message, Payment.class);
            switch (payment.getStatus()) {
                case PENDING:
                    logService.logInfo("Payment is pending.");
                    break;
                case APPROVED:
                    payment.setExecutionDate(getISO8601Date());
                    logService.logInfo("Payment has been completed.");
                    // call for payment method
                    break;
                case REJECTED:
                    logService.logInfo("Payment failed.");
                    break;
                default:
                    logService.logInfo("Unknown payment status.");
                    break;
            }
        } catch (JsonProcessingException e) {
            logService.logError(e.getMessage(), e);
        }
    }


}
