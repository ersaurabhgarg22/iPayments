package com.db.iPayments.Controller;

import com.db.iPayments.model.PaymentStatus;
import com.db.iPayments.store.FraudResultStore;
import com.db.iPayments.Service.LogService;
import com.db.iPayments.model.Payment;
import com.db.iPayments.service.XmlConverterService;
import org.springframework.http.ResponseEntity;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Component
@RestController
public class FraudCheckController {
    private final JmsTemplate jmsTemplate;
    private final XmlConverterService xmlConverterService;
    private final LogService logService;
    private final FraudResultStore fraudResultStore;

    public FraudCheckController(JmsTemplate jmsTemplate, XmlConverterService xmlConverterService, LogService logService, FraudResultStore fraudResultStore) {
        this.jmsTemplate = jmsTemplate;
        this.xmlConverterService = xmlConverterService;
        this.logService = logService;
        this.fraudResultStore = fraudResultStore;
    }

    @PostMapping("/fraud-check")
    public ResponseEntity<Payment> check(@RequestBody Payment payment) {
        String xml = xmlConverterService.convertToXml(payment);
        logService.logInfo("XML form of payment request " + xml);
        jmsTemplate.convertAndSend("fcs.queue.request", xml);
        Payment paymentResult = fraudResultStore.waitForResult(payment.getTransactionId());
        if(null != paymentResult) {
            logService.logInfo("Payment status after fraud check request " + paymentResult.getStatus());
            return ResponseEntity.ok(paymentResult);
        }else {
            logService.logInfo("Payment is Rejected!!");
            payment.setStatus(PaymentStatus.REJECTED);
            return ResponseEntity.ok(payment);
        }
    }
}
