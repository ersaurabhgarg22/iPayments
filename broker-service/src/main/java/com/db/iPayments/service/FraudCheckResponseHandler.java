package com.db.iPayments.service;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.messaging.ActiveMQProducer;
import com.db.iPayments.model.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class FraudCheckResponseHandler {

    private final ActiveMQProducer activeMQProducer;
    private final LogService logService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FraudCheckResponseHandler(ActiveMQProducer activeMQProducer, LogService logService) {
        this.activeMQProducer = activeMQProducer;
        this.logService = logService;
    }

    public void handleFraudCheckResponse(Payment payment) throws JsonProcessingException {
        logService.logInfo("Received fraud check result: " + payment);
        String paymentJson = objectMapper.writeValueAsString(payment);
        activeMQProducer.sendMessage("ppsQueue", paymentJson);
    }
}
