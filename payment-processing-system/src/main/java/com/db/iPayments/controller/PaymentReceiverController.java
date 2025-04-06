package com.db.iPayments.controller;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.store.FraudCheckConfig;
import com.db.iPayments.model.Payment;
import com.db.iPayments.model.PaymentStatus;
import com.db.iPayments.service.PaymentProcessorService;
import com.db.iPayments.service.PaymentValidatorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.db.iPayments.Utils.Utils.generateUUID;
import static com.db.iPayments.Utils.Utils.getISOUTCTimestamp;

@RestController
@RequestMapping("/payment")
public class PaymentReceiverController {

    private final PaymentProcessorService paymentProcessorService;
    private final PaymentValidatorService validatorService;
    private final LogService logService;
    private final FraudCheckConfig fraudCheckConfig;

    public PaymentReceiverController(PaymentProcessorService paymentProcessorService, PaymentValidatorService validatorService, LogService logService, FraudCheckConfig fraudCheckConfig) {
        this.paymentProcessorService = paymentProcessorService;
        this.validatorService = validatorService;
        this.logService = logService;
        this.fraudCheckConfig = fraudCheckConfig;
    }

    // test method for restAPI check
    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome to the instant payments project";
    }

    @PostMapping("/process")
    public ResponseEntity<String> processPayment(@RequestBody Payment payment) {
        try {
            logService.logInfo("Received payment request: " + payment);
            payment.setStatus(PaymentStatus.PENDING);
            payment.setCreationTimestamp(getISOUTCTimestamp());
            payment.setTransactionId(generateUUID());
            return ResponseEntity.ok(paymentProcessorService.processPayment(payment));
        } catch (Exception e) {
            logService.logError(e.getMessage(), e);
            return ResponseEntity.ok("Payment Request went into error. Please contact support team!!");
        }
    }

    @PostMapping("/api/process")
    public ResponseEntity<String> processPaymentAPI(@RequestBody Payment payment) {
        logService.logInfo("Received payment request via API: " + payment);
        payment.setStatus(PaymentStatus.PENDING);
        payment.setCreationTimestamp(getISOUTCTimestamp());
        payment.setTransactionId(generateUUID());
        if (!validatorService.validate(payment)) {
            logService.logError("Payment validation failed", null);
            return ResponseEntity.ok("Invalid payment details");
        } else{
            Payment paymentResponse = fraudCheckConfig.checkFraud(payment);
            logService.logInfo("Payment status after all checks  " + paymentResponse.getStatus());
            if (PaymentStatus.REJECTED.equals(paymentResponse.getStatus())) {
                return ResponseEntity.ok("Payment Rejected!!!");
            }
            return ResponseEntity.ok("Payment is approved with transaction id: " + paymentResponse.getTransactionId() + ". It'll be processed!");
        }
    }

}
