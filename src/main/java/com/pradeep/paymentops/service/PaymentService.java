package com.pradeep.paymentops.service;

import com.pradeep.paymentops.dto.CreatePaymentRequest;
import com.pradeep.paymentops.dto.PaymentResponse;
import org.springframework.stereotype.Service;

import com.pradeep.paymentops.dto.CreatePaymentRequest;
import com.pradeep.paymentops.dto.PaymentResponse;
import com.pradeep.paymentops.entity.Payment;
import com.pradeep.paymentops.repository.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
public class PaymentService {

    private final PaymentRepository repo;

    public PaymentService(PaymentRepository repo) {
        this.repo = repo;
    }

    @Transactional
    public PaymentResponse create(CreatePaymentRequest request) {
        Payment payment = Payment.builder()
                .id(UUID.randomUUID().toString())
                .merchantId(request.getMerchantId())
                .amountCents(request.getAmountCents())
                .currency(request.getCurrency())
                .status("CREATED")
                .createdAt(OffsetDateTime.now())
                .build();

        Payment saved = repo.save(payment);

        return PaymentResponse.builder()
                .paymentId(saved.getId())
                .merchantId(saved.getMerchantId())
                .amountCents(saved.getAmountCents())
                .currency(saved.getCurrency())
                .status(saved.getStatus())
                .build();
    }
}


