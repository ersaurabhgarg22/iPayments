package com.db.iPayments.messaging;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.model.Payment;
import com.db.iPayments.service.FraudCheckResponseHandler;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.db.iPayments.service.FraudCheckRequestHandler;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQConsumer {
    private final LogService logService;
    private FraudCheckRequestHandler fraudCheckRequestHandler;
    private FraudCheckResponseHandler  fraudCheckResponseHandler;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final XmlMapper xmlMapper = new XmlMapper();

    public ActiveMQConsumer(LogService logService, FraudCheckRequestHandler fraudCheckRequestHandler, FraudCheckResponseHandler fraudCheckResponseHandler) {
        this.logService = logService;
        this.fraudCheckRequestHandler = fraudCheckRequestHandler;
        this.fraudCheckResponseHandler = fraudCheckResponseHandler;
    }

    @JmsListener(destination = "brokerQueueForFraudCheck")
    public void receiveMessage(String message) throws JsonProcessingException {
        logService.logInfo("Received message from brokerQueueForFraudCheck queue: " + message);
        Payment payment = objectMapper.readValue(message, Payment.class);
        fraudCheckRequestHandler.handleFraudCheckRequest(payment);
    }

    @JmsListener(destination = "brokerQueueAfterFraudCheck")
    public void receiveMessageFromBSQueue(String message) throws JsonProcessingException {
        logService.logInfo("Received message from brokerQueueAfterFraudCheck queue: " + message);
        Payment payment = xmlMapper.readValue(message, Payment.class);
        logService.logInfo("Final Payment : " + payment);
        fraudCheckResponseHandler.handleFraudCheckResponse(payment);
    }
}
