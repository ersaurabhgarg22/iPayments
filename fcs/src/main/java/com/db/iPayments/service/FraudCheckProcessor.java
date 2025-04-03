package com.db.iPayments.service;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.messaging.ActiveMQProducer;
import com.db.iPayments.model.Payment;
import com.db.iPayments.model.PaymentStatus;
import org.springframework.stereotype.Service;

@Service
public class FraudCheckProcessor {

    private final BlacklistService blacklistService;
    private final ActiveMQProducer activeMQProducer;
    private final LogService logService;
    private final XmlConverterService xmlConverterService;

    public FraudCheckProcessor(BlacklistService blacklistService, ActiveMQProducer activeMQProducer, LogService logService, XmlConverterService xmlConverterService) {
        this.blacklistService = blacklistService;
        this.activeMQProducer = activeMQProducer;
        this.logService = logService;
        this.xmlConverterService = xmlConverterService;
    }

    public void processFraudCheck(Payment request) {
        logService.logInfo("Processing fraud check request: " + request);
        boolean isFraudulent = blacklistService.isFraudulent(request);
        if(!isFraudulent) {
            request.setStatus(PaymentStatus.APPROVED);
        } else {
            request.setStatus(PaymentStatus.REJECTED);
        }
        activeMQProducer.sendMessage("brokerQueueAfterFraudCheck", xmlConverterService.convertToXml(request));
    }
}
