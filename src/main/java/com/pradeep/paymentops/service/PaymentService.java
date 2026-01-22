package com.pradeep.paymentops.service;

import com.pradeep.paymentops.dto.*;
import com.pradeep.paymentops.entity.PaymentAuditEvent;
import com.pradeep.paymentops.exception.NotFoundException;
import com.pradeep.paymentops.repository.PaymentAuditEventRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.time.OffsetDateTime;
import com.pradeep.paymentops.exception.BadRequestException;
import com.pradeep.paymentops.exception.NotFoundException;



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

    private final PaymentAuditEventRepository auditRepo;

    public PaymentService(PaymentRepository repo, PaymentAuditEventRepository auditRepo) {
        this.repo = repo;
        this.auditRepo = auditRepo;
    }

    @Transactional
    public PaymentResponse create(CreatePaymentRequest request) {

        OffsetDateTime now = OffsetDateTime.now();
        Payment payment = Payment.builder()
                .id(UUID.randomUUID().toString())
                .merchantId(request.getMerchantId())
                .amountCents(request.getAmountCents())
                .currency(request.getCurrency())
                .status("CREATED")
                .createdAt(now)
                .updatedAt(now)
                .build();

        Payment saved = repo.save(payment);
        //this will save automatically into audit table
        audit(saved.getId(), null, saved.getStatus(), "Payment created", "API");


        return PaymentResponse.builder()
                .paymentId(saved.getId())
                .merchantId(saved.getMerchantId())
                .amountCents(saved.getAmountCents())
                .currency(saved.getCurrency())
                .status(saved.getStatus())
                .build();
    }


    @Transactional(readOnly = true)
    public PaymentResponse getById(String id) {
        Payment p = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found: " + id));

        return PaymentResponse.builder()
                .paymentId(p.getId())
                .merchantId(p.getMerchantId())
                .amountCents(p.getAmountCents())
                .currency(p.getCurrency())
                .status(p.getStatus())
                .build();
    }



// ...

    @Transactional(readOnly = true)
    public Page<PaymentResponse> list(String merchantId, String status, Pageable pageable) {

        Page<Payment> page;

        boolean hasMerchant = merchantId != null && !merchantId.isBlank();
        boolean hasStatus = status != null && !status.isBlank();

        if (hasMerchant && hasStatus) {
            page = repo.findByMerchantIdAndStatus(merchantId, status.toUpperCase(), pageable);
        } else if (hasMerchant) {
            page = repo.findByMerchantId(merchantId, pageable);
        } else if (hasStatus) {
            page = repo.findByStatus(status.toUpperCase(), pageable);
        } else {
            page = repo.findAll(pageable);
        }

        return page.map(p -> PaymentResponse.builder()
                .paymentId(p.getId())
                .merchantId(p.getMerchantId())
                .amountCents(p.getAmountCents())
                .currency(p.getCurrency())
                .status(p.getStatus())
                .build());
    }



    @Transactional
    public PaymentResponse capture(String id) {
        Payment p = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found: " + id));

        if (!"CREATED".equalsIgnoreCase(p.getStatus())) {
            throw new BadRequestException("Invalid transition: " + p.getStatus() + " -> CAPTURED");
        }

        String from = p.getStatus();
        p.setStatus("CAPTURED");
        p.setUpdatedAt(OffsetDateTime.now());
        Payment saved = repo.save(p);

        audit(saved.getId(), from, saved.getStatus(), "Captured successfully", "API");
        return toResponse(saved);
    }

    private PaymentResponse toResponse(Payment p) {
        return PaymentResponse.builder()
                .paymentId(p.getId())
                .merchantId(p.getMerchantId())
                .amountCents(p.getAmountCents())
                .currency(p.getCurrency())
                .status(p.getStatus())
                .build();
    }


    @Transactional
    public PaymentResponse refund(String id) {
        Payment p = repo.findById(id)
                .orElseThrow(() -> new NotFoundException("Payment not found: " + id));

        if (!"CAPTURED".equalsIgnoreCase(p.getStatus())) {
            throw new BadRequestException("Invalid transition: " + p.getStatus() + " -> REFUNDED");
        }

        String from = p.getStatus();
        p.setStatus("REFUNDED");
        p.setUpdatedAt(OffsetDateTime.now());
        Payment saved = repo.save(p);

        audit(saved.getId(), from, saved.getStatus(), "Refunded successfully", "API");
        return toResponse(saved);

    }

    private void audit(String paymentId, String fromStatus, String toStatus, String reason, String actor) {
        auditRepo.save(PaymentAuditEvent.builder()
                .paymentId(paymentId)
                .fromStatus(fromStatus)
                .toStatus(toStatus)
                .reason(reason)
                .actor(actor)
                .createdAt(OffsetDateTime.now())
                .build());
    }


    @Transactional(readOnly = true)
    public java.util.List<PaymentAuditResponse> getAudit(String paymentId) {
        // Optional: validate payment exists
        repo.findById(paymentId).orElseThrow(() -> new NotFoundException("Payment not found: " + paymentId));

        return auditRepo.findByPaymentIdOrderByCreatedAtAsc(paymentId).stream()
                .map(a -> PaymentAuditResponse.builder()
                        .id(a.getId())
                        .fromStatus(a.getFromStatus())
                        .toStatus(a.getToStatus())
                        .reason(a.getReason())
                        .actor(a.getActor())
                        .createdAt(a.getCreatedAt())
                        .build())
                .toList();
    }




}


