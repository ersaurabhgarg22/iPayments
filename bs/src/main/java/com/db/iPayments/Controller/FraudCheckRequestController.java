package com.db.iPayments.Controller;

import com.db.iPayments.Service.LogService;
import com.db.iPayments.model.Payment;
import com.db.iPayments.model.PaymentStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.db.iPayments.Utils.Utils.generateUUID;
import static com.db.iPayments.Utils.Utils.getISOUTCTimestamp;

@RestController
@RequestMapping("/ff")
public class FraudCheckRequestController {

    private final LogService logService;

    public FraudCheckRequestController(LogService logService) {
        this.logService = logService;
    }

    @PostMapping("/processFraudCheck")
    public ResponseEntity<Payment> processFraudCheck(@RequestBody Payment payment) {
        try {
            logService.logInfo("Received payment for fraud check: " + payment);
            payment.setStatus(PaymentStatus.PENDING);
            payment.setCreationTimestamp(getISOUTCTimestamp());
            payment.setTransactionId(generateUUID());
            return ResponseEntity.ok(payment); // ✅ Return updated Payment object
        } catch (Exception e) {
            logService.logError(e.getMessage(), e);
            return ResponseEntity.status(500).body(null);
        }
    }
}
