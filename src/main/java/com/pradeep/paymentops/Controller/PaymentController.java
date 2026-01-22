package com.pradeep.paymentops.Controller;

import com.pradeep.paymentops.dto.CreatePaymentRequest;
import com.pradeep.paymentops.dto.PaymentResponse;
import com.pradeep.paymentops.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PaymentResponse createPayment(@Valid @RequestBody CreatePaymentRequest request) {
        return paymentService.create(request);
    }
}
