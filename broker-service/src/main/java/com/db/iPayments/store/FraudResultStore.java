package com.db.iPayments.store;

import com.db.iPayments.model.Payment;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class FraudResultStore {
    private Map<String, Payment> results = new ConcurrentHashMap<>();

    public void store(Payment payment) {
        results.put(payment.getTransactionId(), payment);
    }

    public Payment waitForResult(String transactionId) {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
        }
        System.out.println("FOUND! Returning result for " + transactionId);
        return results.remove(transactionId);
    }

}
