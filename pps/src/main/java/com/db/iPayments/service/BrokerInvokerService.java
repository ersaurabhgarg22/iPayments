package com.db.iPayments.service;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.messaging.ActiveMQProducer;
import com.db.iPayments.model.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class BrokerInvokerService {
    private final ActiveMQProducer activeMQProducer;
    private final LogService logService;
    @Autowired
    private WebClient webClient;

    public BrokerInvokerService(ActiveMQProducer activeMQProducer, LogService logService) {
        this.activeMQProducer = activeMQProducer;
        this.logService = logService;
    }

    public void sendForFraudCheck(Payment payment) throws JsonProcessingException {
        logService.logInfo("Sending payment for fraud check: " + payment);
        activeMQProducer.sendMessage("brokerQueueForFraudCheck", payment);
    }

    public Payment sendForFraudCheckAPI(Payment payment) throws JsonProcessingException {
        logService.logInfo("Sending payment for fraud check: " + payment);
        String url = "http://localhost:8081/ff/processFraudCheck";
        return webClient.post()
                .uri(url)
                .retrieve()
                .bodyToMono(Payment.class)
                .block();
    }
}
