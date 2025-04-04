package com.db.iPayments.messaging;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.model.Payment;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQProducer {

    private final JmsTemplate jmsTemplate;
    private final LogService logService;

    public ActiveMQProducer(JmsTemplate jmsTemplate, LogService logService) {
        this.jmsTemplate = jmsTemplate;
        this.logService = logService;
    }

    public void sendMessage(String queue, Payment message) throws JsonProcessingException {
        logService.logInfo("Sending message to queue: " + queue);
        jmsTemplate.convertAndSend(queue, message);
    }
}
