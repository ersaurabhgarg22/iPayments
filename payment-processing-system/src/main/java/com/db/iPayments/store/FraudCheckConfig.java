package com.db.iPayments.store;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.model.Payment;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FraudCheckConfig {
    private final RestTemplate restTemplate = new RestTemplate();
    private final LogService logService;
    @Value("${fraud.service.url}")
    private String fraudServiceUrl;

    public FraudCheckConfig(LogService logService) {
        this.logService = logService;
    }


    public Payment checkFraud(Payment payment) {
        logService.logInfo("Calling Broker Service - FraudCheckController");
        return restTemplate.postForObject(fraudServiceUrl, payment, Payment.class);
    }
}
