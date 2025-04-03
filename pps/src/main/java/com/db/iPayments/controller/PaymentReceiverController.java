package com.db.iPayments.controller;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.model.Payment;
import com.db.iPayments.model.PaymentStatus;
import com.db.iPayments.service.PaymentProcessorService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.db.iPayments.Utils.Utils.generateUUID;
import static com.db.iPayments.Utils.Utils.getISOUTCTimestamp;

@RestController
@RequestMapping("/payment")
public class PaymentReceiverController {

    private final PaymentProcessorService paymentProcessorService;
    private final LogService logService;

    public PaymentReceiverController(PaymentProcessorService paymentProcessorService, LogService logService) {
        this.paymentProcessorService = paymentProcessorService;
        this.logService = logService;
    }

    @GetMapping("/welcome")
    public String welcome(){
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
            return null;
        }
    }

//    @PostMapping("/processAPI")
//    public ResponseEntity<String> processPaymentAPI(@RequestBody Payment payment) {
//        try {
//            logService.logInfo("Received payment request: " + payment);
//            payment.setStatus(PaymentStatus.PENDING);
//            payment.setCreationTimestamp(getISOUTCTimestamp());
//            payment.setTransactionId(generateUUID());
//            return ResponseEntity.ok(paymentProcessorService.processPaymentAPI(payment).toString());
//        } catch (Exception e) {
//            logService.logError(e.getMessage(), e);
//            return null;
//        }
//    }
}
