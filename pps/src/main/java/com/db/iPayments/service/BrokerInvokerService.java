package com.db.iPayments.service;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.messaging.ActiveMQProducer;
import com.db.iPayments.model.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.stereotype.Service;

@Service
public class BrokerInvokerService {
    private final ActiveMQProducer activeMQProducer;
    private final LogService logService;

    public BrokerInvokerService(ActiveMQProducer activeMQProducer, LogService logService) {
        this.activeMQProducer = activeMQProducer;
        this.logService = logService;
    }

    public void sendForFraudCheck(Payment payment) throws JsonProcessingException {
        logService.logInfo("Sending payment for fraud check: " + payment);
        activeMQProducer.sendMessage("brokerQueueForFraudCheck", payment);
    }
}
