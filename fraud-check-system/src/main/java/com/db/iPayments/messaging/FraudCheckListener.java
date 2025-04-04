package com.db.iPayments.messaging;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.model.Payment;
import com.db.iPayments.model.PaymentStatus;
import com.db.iPayments.service.BlacklistService;
import com.db.iPayments.service.XmlConverterService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class FraudCheckListener {
    private final JmsTemplate jmsTemplate;
    private final BlacklistService blacklistService;
    private final XmlConverterService xmlConverterService;
    private final LogService logService;

    public FraudCheckListener(JmsTemplate jmsTemplate, BlacklistService blacklistService, XmlConverterService xmlConverterService, LogService logService) {
        this.jmsTemplate = jmsTemplate;
        this.blacklistService = blacklistService;
        this.xmlConverterService = xmlConverterService;
        this.logService = logService;
    }

    @JmsListener(destination = "fcs.queue.request")
    public void onMessage(String xml) {
        Payment payment = xmlConverterService.convertFromXml(xml);
        boolean result = blacklistService.isFraudulent(payment);
        logService.logInfo("Checking if payment request is Fraudulent " + result);
        String resultXml = null;
        if (!result) {
            payment.setStatus(PaymentStatus.APPROVED);
            resultXml = xmlConverterService.convertToXml(payment);
        } else {
            payment.setStatus(PaymentStatus.REJECTED);
            resultXml = xmlConverterService.convertToXml(payment);
        }
        jmsTemplate.convertAndSend("fcs.queue.response", resultXml);
    }
}
