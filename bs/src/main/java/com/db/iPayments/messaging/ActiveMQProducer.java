package com.db.iPayments.messaging;

import com.db.iPayments.Service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class ActiveMQProducer {

    @Autowired
    private JmsTemplate defaultJmsTemplate; // No JSON conversion
    private final LogService logService;

    public ActiveMQProducer(LogService logService) {
        this.logService = logService;
    }

    public void sendMessage(String queue, String message) {
        logService.logInfo("Sending message to queue: " + queue);
        defaultJmsTemplate.convertAndSend(queue, message);
    }
}
