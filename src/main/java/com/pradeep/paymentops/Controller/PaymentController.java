package com.pradeep.paymentops.Controller;

import com.pradeep.paymentops.dto.CreatePaymentRequest;
import com.pradeep.paymentops.dto.PaymentAuditResponse;
import com.pradeep.paymentops.dto.PaymentResponse;
import com.pradeep.paymentops.service.PaymentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("/{id}")
    public PaymentResponse getPayment(@PathVariable String id) {
        return paymentService.getById(id);
    }

    @GetMapping
    public Page<PaymentResponse> listPayments(
            @RequestParam(required = false) String merchantId,
            @RequestParam(required = false) String status,
            Pageable pageable
    ) {
        return paymentService.list(merchantId, status, pageable);
    }

    @PostMapping("/{id}/capture")
    public PaymentResponse capture(@PathVariable String id) {
        return paymentService.capture(id);
    }

    @PostMapping("/{id}/refund")
    public PaymentResponse refund(@PathVariable String id) {
        return paymentService.refund(id);
    }

    @GetMapping("/{id}/audit")
    public java.util.List<PaymentAuditResponse> audit(@PathVariable String id) {
        return paymentService.getAudit(id);
    }



}
