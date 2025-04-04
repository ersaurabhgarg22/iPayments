package com.db.iPayments.service;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.messaging.ActiveMQProducer;
import com.db.iPayments.model.Payment;
import org.springframework.stereotype.Service;

@Service
public class FraudCheckRequestHandler {

    private final XmlConverterService xmlConverterService;
    private final ActiveMQProducer activeMQProducer;
    private final LogService logService;

    public FraudCheckRequestHandler(XmlConverterService xmlConverterService, ActiveMQProducer activeMQProducer, LogService logService) {
        this.xmlConverterService = xmlConverterService;
        this.activeMQProducer = activeMQProducer;
        this.logService = logService;
    }

    public void handleFraudCheckRequest(Payment payment) {
        logService.logInfo("Received fraud check request: " + payment);
        String xmlRequest = xmlConverterService.convertToXml(payment);
        activeMQProducer.sendMessage("fcsQueue", xmlRequest);
    }
}
