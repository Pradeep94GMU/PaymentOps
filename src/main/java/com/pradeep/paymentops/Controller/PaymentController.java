package com.pradeep.paymentops.Controller;

import com.pradeep.paymentops.dto.CreatePaymentRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Map<String, Object> createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        // no DB yet, just simulate a created paymentId
        return Map.of(
                "paymentId", UUID.randomUUID().toString(),
                "merchantId", request.getMerchantId(),
                "amountCents", request.getAmountCents(),
                "currency", request.getCurrency(),
                "status", "CREATED"
        );
    }
}

