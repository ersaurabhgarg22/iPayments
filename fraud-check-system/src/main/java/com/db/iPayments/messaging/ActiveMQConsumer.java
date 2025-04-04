package com.db.iPayments.messaging;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.model.Payment;
import com.db.iPayments.service.FraudCheckProcessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQConsumer {
    @Autowired
    private final FraudCheckProcessor fraudCheckProcessor;
    private final LogService logService;
    private final XmlMapper xmlMapper = new XmlMapper();

    public ActiveMQConsumer(FraudCheckProcessor fraudCheckProcessor, LogService logService) {
        this.fraudCheckProcessor = fraudCheckProcessor;
        this.logService = logService;
    }

    @JmsListener(destination = "fcsQueue")
    public void receiveMessage(String message) throws JsonProcessingException {
        logService.logInfo("Received fraud check request: " + message);
        Payment payment = xmlMapper.readValue(message, Payment.class);
        fraudCheckProcessor.processFraudCheck(payment);
    }
}
