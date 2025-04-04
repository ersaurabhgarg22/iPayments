package com.db.iPayments.messaging;

import com.db.iPayments.store.FraudResultStore;
import com.db.iPayments.Service.LogService;
import com.db.iPayments.model.Payment;
import com.db.iPayments.service.XmlConverterService;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Component
public class FraudResponseListener {

    private final XmlConverterService xmlConverterService;
    private final LogService logService;
    private final FraudResultStore fraudResultStore;

    public FraudResponseListener(XmlConverterService xmlConverterService, LogService logService, FraudResultStore fraudResultStore) {
        this.xmlConverterService = xmlConverterService;
        this.logService = logService;
        this.fraudResultStore = fraudResultStore;
    }

    @JmsListener(destination = "fcs.queue.response")
    public void onMessage(String xml) {
        Payment payment = xmlConverterService.convertFromXml(xml);
        logService.logInfo("FCS queue Response " + payment.getStatus());
        fraudResultStore.store(payment);
    }
}
