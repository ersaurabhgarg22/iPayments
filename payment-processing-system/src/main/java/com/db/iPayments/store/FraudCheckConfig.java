package com.db.iPayments.store;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.model.Payment;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class FraudCheckConfig {
    private final RestTemplate restTemplate = new RestTemplate();
    private final LogService logService;

    public FraudCheckConfig(LogService logService) {
        this.logService = logService;
    }


    public Payment checkFraud(Payment payment) {
        logService.logInfo("Calling Broker Service");
        return restTemplate.postForObject("http://localhost:8081/fraud-check", payment, Payment.class);
    }
}
